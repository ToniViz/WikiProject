/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio.controlador;

import dominio.dao.acceso.UsuarioAccesoImpl;
import dominio.dao.acceso.ArticuloAcceso;
import dominio.dao.acceso.WikiAccesoImpl;
import dominio.dao.acceso.WikiAcceso;
import dominio.dao.acceso.ArticuloAccesoImpl;
import dominio.dao.acceso.UsuarioAcceso;
import dominio.articulo.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import servicio.utilidades.EstadoCreator;
import servicio.utilidades.FactoryColaborador;

/**
 * The class processes and saves the modification file of the article sent by the user. Also saves changes accepted by the user supervisor in the context article. 
 * @author Toni
 */
@MultipartConfig
public class ManejadorArchivo {

    //Wiki access field
    private WikiAcceso wikiA;
    //Article access field
    private ArticuloAcceso articuloA;
    //User access field
    private UsuarioAcceso usuarioA;
    //Article used in the context
    private Articulo articulo;
    //It is the article resulting from the modification made by the user
    private Articulo articuloRespaldo;
    //Estado creator factory
    private EstadoCreator estadoC;

    /**
     * ManejadorArchivo with the article used in the context
     * @param articulo 
     */
    public ManejadorArchivo(Articulo articulo) {
        this.wikiA = new WikiAccesoImpl();
        this.articuloA = new ArticuloAccesoImpl();
        this.usuarioA = new UsuarioAccesoImpl();
        this.articulo = articulo;
        this.articuloRespaldo = null;
        this.estadoC = new FactoryColaborador();

    }

    /**
     * Returns the article template in HTML format that the user wants to modify
     * @param request
     * @param response
     */
    public void devolverArchivo(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment;filename=articulo.html");

        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", -1);

        int idArticulo = Integer.parseInt(request.getParameter("idArticulo"));
        Articulo articulo = this.articuloA.findArticleID(idArticulo);
        List<Contenido> contenidos = this.articuloA.findContentID(articulo);

        PrintWriter out;
        try {
            out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Articulo</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>" + articulo.getTituloArticulo() + "</h2>");
            out.println("<b>" + articulo.getDescripcion() + "</b>");
            out.println("<main>");
            for (Contenido contenido : contenidos) {
                out.println("<div>");
                out.println("<h4>" + contenido.getSubtitulo() + "</h4>");
                out.println("<p>" + contenido.getFrases() + "</p>");
                out.println("</div>");
            }
            out.println("</main>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException ex) {
            Logger.getLogger(ManejadorArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Processes the file sent by the user colaborador and saves the content into the (borrador) field in the context article. Supervisor changes directly modify article
     *
     * @param request
     * @param response
     * @param id
     */
    public void guardarContenido(HttpServletRequest request, HttpServletResponse response, int id, boolean testigo) {

        try {

            String texto = null;
            String resultado = null;

            try {

                Part archivo = request.getPart("archivo");
                InputStream stream = archivo.getInputStream();
                InputStreamReader isReader = new InputStreamReader(stream);
                BufferedReader reader = new BufferedReader(isReader);
                StringBuffer sb = new StringBuffer();
                String cadena;
                while ((cadena = reader.readLine()) != null) {
                    sb.append(cadena);
                }
                resultado = sb.toString();
            } catch (IOException ex) {
                Logger.getLogger(ManejadorArchivo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServletException ex) {
                Logger.getLogger(ManejadorArchivo.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (!testigo) {
                //Guarda los cambios producidos por el colaborador
                this.articulo.setArchivo(resultado);
                this.articulo.setBorrador(id);
                this.estadoC.addEstado(this.usuarioA.findUserID(id), articulo, "Pendiente");
            } else {
                //Guards los cambios realizados por el supervisor
                this.articulo.setArchivo(resultado);
                Articulo articulo = this.getContenido();
                this.articulo.setArchivo(null);
                if (articulo.getContenido().size() > this.articulo.getContenido().size()) {
                    int auxiliar = articulo.getContenido().size() - this.articulo.getContenido().size();
                    for (int i = 0; i < auxiliar; i++) {
                        Contenido contenido = new Contenido("null", "null");
                        this.articuloA.insertContent(this.articulo, contenido);
                        this.articulo.getContenido().add(new Contenido("null", "null"));
                    }
                }
                this.articulo.setTituloArticulo(articulo.getTituloArticulo());
                this.articulo.setDescripcion(articulo.getDescripcion());
                for (int i = 0; i < this.articulo.getContenido().size(); i++) {
                    Contenido contenido = this.articulo.getContenido().get(i);
                    contenido.setSubtitulo(articulo.getContenido().get(i).getSubtitulo());
                    contenido.setFrases(articulo.getContenido().get(i).getFrases());
                }
                for (Contenido content : this.articulo.getContenido()) {
                    this.articuloA.updateContent(this.articulo, content);
                }
            }

            this.articuloA.updateArticle(articulo);

            List<Contenido> contenido = this.articuloA.findContentID(articulo);
            request.setAttribute("articulo", this.articulo);
            request.setAttribute("contenidos", contenido);
            request.getRequestDispatcher("/WEB-INF/paginas/colaborador/colaboradorArticulo.jsp").forward(request, response);

        } catch (ServletException ex) {
            Logger.getLogger(ManejadorArchivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Find the differences found within the structure of the original article and the modified article and sends them. 
     * @return: list of differences
     */
    public LinkedHashMap<String, String> getDiferencias() {
        this.articuloRespaldo = this.getContenido();
        LinkedHashMap<String, String> diferencias = new LinkedHashMap<>();
        if (!this.articulo.getTituloArticulo().equals(this.articuloRespaldo.getTituloArticulo())) {
            diferencias.put(this.articulo.getTituloArticulo(), this.articuloRespaldo.getTituloArticulo());
        }
        if (!this.articulo.getDescripcion().equals(this.articuloRespaldo.getDescripcion())) {
            diferencias.put(this.articulo.getDescripcion(), this.articuloRespaldo.getDescripcion());
        }
        List<Contenido> contenido = this.articulo.getContenido();
        List<Contenido> contenidoAux = this.articuloRespaldo.getContenido();

        if (contenidoAux.size() > contenido.size()) {
            for (int i = 0; i < contenidoAux.size(); i++) {
                String subtituloAux = null;
                String subtitulo = null;
                String frasesAux = null;
                String frases = null;
                subtituloAux = contenidoAux.get(i).getSubtitulo();
                if (contenido.size() > i) {
                    subtitulo = contenido.get(i).getSubtitulo();
                } else {
                    subtitulo = " ";
                }
                frasesAux = contenidoAux.get(i).getFrases();
                if (contenido.size() > i) {
                    frases = contenido.get(i).getFrases();
                } else {
                    frases = " ";
                }

                if (!subtituloAux.equals(subtitulo)) {
                    diferencias.put(subtitulo, subtituloAux);
                }
                if (!frases.equals(frasesAux)) {
                    diferencias.put(frases, frasesAux);
                }
            }
        } else {
            for (int i = 0; i < contenido.size(); i++) {
                String subtituloAux = null;
                String subtitulo = null;
                String frasesAux = null;
                String frases = null;
                subtitulo = contenido.get(i).getSubtitulo();
                if (contenidoAux.size() > i) {
                    subtituloAux = contenidoAux.get(i).getSubtitulo();
                } else {
                    subtituloAux = " ";
                }
                frases = contenido.get(i).getFrases();
                if (contenidoAux.size() > i) {
                    frasesAux = contenidoAux.get(i).getFrases();
                } else {
                    frasesAux = " ";
                }

                if (!subtituloAux.equals(subtitulo)) {
                    diferencias.put(subtitulo, subtituloAux);
                }
                if (!frases.equals(frasesAux)) {
                    diferencias.put(frases, frasesAux);
                }
            }
        }

        return diferencias;
    }

    /**
     * Processes items selected by supervisor and saves them in context article
     *
     * @param request
     * @param response
     */
    public void cambioArticulo(HttpServletRequest request, HttpServletResponse response, int id) {
        String[] diferencias = request.getParameterValues("checkbox");

        List<Contenido> contenidos = this.articulo.getContenido();
        this.articuloRespaldo = this.getContenido();

        int sizeListAux = this.articuloRespaldo.getContenido().size();
        int sizeList = this.articulo.getContenido().size();
        int auxiliar = sizeListAux - sizeList;
        if (sizeListAux > sizeList) {
            for (int i = 0; i < auxiliar; i++) {
                Contenido contenido = new Contenido("null", "null");
                this.articuloA.insertContent(this.articulo, contenido);
                contenidos.add(new Contenido("null", "null"));

            }
        }

        for (int i = 0; i < diferencias.length; i++) {
            String diferencia = diferencias[i];
            String analisis = this.analizeString(diferencia);

            switch (analisis) {
                case "tituloArticulo":
                    this.articulo.setTituloArticulo(diferencia);
                    break;
                case "descripcionArticulo":
                    this.articulo.setDescripcion(diferencia);
            }

            for (int j = 0; j < sizeListAux; j++) {
                Contenido content = null;

                content = contenidos.get(j);
                if (analisis.equals("subtitulo" + j)) {
                    content.setSubtitulo(diferencia);
                }
                if (analisis.equals("frase" + j)) {
                    content.setFrases(diferencia);
                }

            }

        }
        this.articulo.setArchivo(null);
        this.articulo.setBorrador(0);
        this.estadoC.updateEstado(this.usuarioA.findUserID(id), this.articulo, "Aceptado");
        //Guardando datos 
        this.articuloA.updateArticle(this.articulo);
        for (Contenido content : contenidos) {
            this.articuloA.updateContent(this.articulo, content);
        }

        //
        request.setAttribute("articulo", this.articulo);
        request.setAttribute("contenidos", contenidos);
        try {
            request.getRequestDispatcher("/WEB-INF/paginas/supervisor/supervisorArticulo.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ManejadorArchivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManejadorArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * 
     * Analyzes the received string and returns the type to which it belongs within the article
     * @param palabra
     * @return: type of field
     */
    private String analizeString(String palabra) {

        List<Contenido> content = this.articuloRespaldo.getContenido();

        if (palabra.equals(this.articuloRespaldo.getTituloArticulo())) {
            return "tituloArticulo";
        } else if (palabra.equals(this.articuloRespaldo.getDescripcion())) {
            return "descripcionArticulo";
        }

        for (int i = 0; i < content.size(); i++) {
            Contenido contenidos = content.get(i);
            if (contenidos.getSubtitulo().equals(palabra)) {
                return "subtitulo" + i;
            } else if (contenidos.getFrases().equals(palabra)) {
                return "frase" + i;
            }
        }

        return null;
    }

    /**
     * Reads the file that saves the changes registered by the user colaborador, to transform them into useful information and introduce it in an article.
     *
     * @return Artículo
     */
    private Articulo getContenido() {
        String cadena = this.articulo.getArchivo();
        String frase = new String();
        String contenidos = new String();
        Contenido contenidoAux = new Contenido();
        ArrayList<String> titulo = new ArrayList();
        ArrayList<String> subtitulo = new ArrayList();

        Character caracterAux;

        int indice = 0;
        Articulo art = new Articulo();

        ArrayList<Contenido> contenido = new ArrayList();

        for (int i = 0; i < cadena.length(); i++) {
            char caracter = cadena.charAt(i);
            if (caracter == '<') {
                String fraseAux = new String();
                String contenidosAux = new String();
                indice = i;
                do {

                    caracterAux = cadena.charAt(indice);
                    frase = Character.toString(caracterAux);
                    fraseAux = fraseAux.concat(frase);
                    indice++;

                    System.out.println(fraseAux);

                } while (caracterAux != '>');

                switch (fraseAux) {

                    case "<h2>":
                        do {

                            caracterAux = cadena.charAt(indice);
                            contenidos = Character.toString(caracterAux);
                            if (!contenidos.equals("<")) {
                                contenidosAux = contenidosAux.concat(contenidos);
                            }
                            indice++;
                        } while (caracterAux != '<');
                        art.setTituloArticulo(contenidosAux);
                        break;
                    case "<b>":
                        do {
                            caracterAux = cadena.charAt(indice);
                            contenidos = Character.toString(caracterAux);
                            if (!contenidos.equals("<")) {
                                contenidosAux = contenidosAux.concat(contenidos);
                            }
                            indice++;
                        } while (caracterAux != '<');
                        art.setDescripcion(contenidosAux);
                        break;
                    case "<h4>":

                        do {
                            caracterAux = cadena.charAt(indice);
                            contenidos = Character.toString(caracterAux);
                            if (!contenidos.equals("<")) {
                                contenidosAux = contenidosAux.concat(contenidos);
                            }
                            indice++;
                        } while (caracterAux != '<');
                        titulo.add(contenidosAux);
                        break;
                    case "<p>":
                        do {
                            caracterAux = cadena.charAt(indice);
                            contenidos = Character.toString(caracterAux);
                            if (!contenidos.equals("<")) {
                                contenidosAux = contenidosAux.concat(contenidos);
                            }
                            indice++;
                        } while (caracterAux != '<');
                        subtitulo.add(contenidosAux);
                        break;
                }

            }

        }
        for (int i = 0; i < titulo.size(); i++) {
            Contenido content = new Contenido();
            content.setSubtitulo(titulo.get(i));
            content.setFrases(subtitulo.get(i));
            contenido.add(content);
        }

        art.setContenido(contenido);
        return art;

    }

    /**
     * Return the context article
     * @return: context article
     */
    public Articulo getArticulo() {
        return articulo;
    }

    /**
     * Set the context article
     * @param articulo 
     */
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

}

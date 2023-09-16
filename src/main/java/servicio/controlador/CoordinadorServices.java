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
import dominio.articulo.Articulo;
import dominio.articulo.Contenido;
import dominio.usuario.Supervisor;
import dominio.usuario.Usuario;
import dominio.wiki.Wiki;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servicio.utilidades.FactoryArtWikiImpl;
import servicio.utilidades.FactoryArtWiki;

/**
 * This class controls the user coordinador access to the system, his requests and changes. Controls supervisor user access to articles. 
 * @author Toni
 */
public class CoordinadorServices implements Servicio {

    //The coordinador user is responsible for controlling the system at a particular time.
    private Usuario usuario;
    //Wiki access field
    private WikiAcceso wikiA;
    //Article access field
    private ArticuloAcceso articuloA;
    //User access field
    private UsuarioAcceso usuarioA;
    //Article used in the context 
    private Articulo articulo;
    //Wiki used in the context
    private Wiki wiki;
    //Wiki and Article factory to create them
    private FactoryArtWiki factoryA;

    /**
     * CoordinadorServices constructor with the user who will control the system
     * @param usuario 
     */
    public CoordinadorServices(Usuario usuario) {
        this.usuario = usuario;
        this.wikiA = new WikiAccesoImpl();
        this.articuloA = new ArticuloAccesoImpl();
        this.usuarioA = new UsuarioAccesoImpl();
        this.articulo = null;
        this.wiki = null;
        this.factoryA = new FactoryArtWikiImpl();
    }

    /**
     * Receives doGet requests and redirects them to their corresponding methods
     * @param request
     * @param response 
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "agregar":
                    this.agregarArticulo(request, response);
                    break;
                case "deleteWikiUsuario":
                    this.eliminarUsuarioWiki(request, response);
                    break;
                case "listarArticulos":
                    this.listarArticulos(request, response);
                    break;
                case "modificar":
                    this.formularioArticulo(request, response);
                    break;
                case "deleteArticle":
                    this.deleteArticle(request, response);
                    break;
                case "gestionar":
                    this.gestionarWiki(request, response);
                    break;
                case "gestionarArticulo":
                    this.gestionarArticulo(request, response);
                    break;
                case "deleteUsuario":
                    this.deleteArticleUser(request, response);
                    break;
                case "peticionRol":
                    this.registroRol(request, response);
                    break;
                case "mostrarArticulo":
                    this.mostrarArticulo(request, response);
                    break;
                case "addContent":
                    this.accessAddContent(request, response);
                    break;
                case "sRol":
                    this.solicitudRol(request, response);
                    break;    
                case "return":
                    this.accionPorDefecto(request, response);
                    break;
            }
        } else {
            this.accionPorDefecto(request, response);
        }
    }

    /**
     * Select the article and show the options to add content
     *
     * @param request
     * @param response
     */
    private void accessAddContent(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idArticulo = Integer.parseInt(request.getParameter("idArticulo"));
            this.articulo = this.articuloA.findArticleID(idArticulo);
            request.setAttribute("articulo", this.articulo);
            request.getRequestDispatcher("/WEB-INF/paginas/coordinador/coordinadorAgregarContenido.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Displays the article and its content
     *
     * @param request
     * @param response
     */
    private void mostrarArticulo(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idArticulo = Integer.parseInt(request.getParameter("idArticulo"));
            Articulo articulo = this.articuloA.findArticleID(idArticulo);
            List<Contenido> contenido = this.articuloA.findContentID(articulo);
            request.setAttribute("articulo", articulo);
            request.setAttribute("contenidos", contenido);
            request.getRequestDispatcher("/WEB-INF/paginas/coordinador/coordinadorDesplegarArticulo.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Receives the role request from the coordinador user to register it in the system
     *
     * @param request
     * @param response
     */
    private void registroRol(HttpServletRequest request, HttpServletResponse response) {
        String peticion = request.getParameter("rol");
        this.usuario.setSolicitud(peticion);
        this.usuarioA.updateUser(usuario);
        try {
            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher("/WEB-INF/paginas/coordinador/coordinadorCambioRol.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Removes the user from the selected article in context
     *
     * @param request
     * @param response
     */
    private void deleteArticleUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idUsuario = Integer.parseInt(request.getParameter("usuarioId"));
            Usuario usuario = this.usuarioA.findUserID(idUsuario);
            this.articuloA.deleteUser(this.articulo, usuario);

            List<Usuario> usuarios = this.getSupervisores(this.articuloA.findUsersID(this.articulo));

            List<Usuario> candidatos = this.getCandidatos(this.usuarioA.findAllUsers(), usuarios);

            request.setAttribute("wiki", wiki);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("candidatos", candidatos);
            request.getRequestDispatcher("/WEB-INF/paginas/coordinador/coordinadorUsuariosWiki.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Shows the user coordinador the management options on the selected article
     *
     * @param request
     * @param response
     */
    private void gestionarArticulo(HttpServletRequest request, HttpServletResponse response) {

        try {

            int idArticulo = Integer.parseInt(request.getParameter("idArticulo"));
            Articulo articulo = this.articuloA.findArticleID(idArticulo);
            //articulo.setContenido((ArrayList<Contenido>) this.articuloA.findContentID(articulo));
            this.articulo = articulo;

            List<Usuario> usuarios = this.getSupervisores(this.articuloA.findUsersID(articulo));

            List<Usuario> candidatos = this.getCandidatos(this.usuarioA.findAllUsers(), usuarios);

            request.setAttribute("articulo", articulo);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("candidatos", candidatos);
            request.getRequestDispatcher("/WEB-INF/paginas/coordinador/coordinadorUsuariosArticulo.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Delete the article selected by the user coordinador
     *
     * @param request
     * @param response
     */
    private void deleteArticle(HttpServletRequest request, HttpServletResponse response) {
        int idArticulo = Integer.parseInt(request.getParameter("idArticulo"));
        Articulo articuloD = this.articuloA.findArticleID(idArticulo);
        List<Contenido> contenidos = this.articuloA.findContentID(articuloD);
        articuloD.setContenido((ArrayList<Contenido>) contenidos);
        this.articuloA.deleteArticle(articuloD);

        List<Articulo> articulos = new ArrayList();
        this.wiki.setArticulos((ArrayList<Articulo>) this.wikiA.findArticlesID(wiki));
        for (Articulo articulo : this.wiki.getArticulos()) {
            for (Usuario usuario : this.articuloA.findUsersID(articulo)) {
                if (this.usuario.getId() == usuario.getId()) {
                    articulos.add(articulo);
                }
            }
        }
        try {
            request.setAttribute("articulos", articulos);
            request.getRequestDispatcher("/WEB-INF/paginas/coordinador/coordinadorListarArticulos.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Shows the form to modify the data of the selected article
     *
     * @param request
     * @param response
     */
    private void formularioArticulo(HttpServletRequest request, HttpServletResponse response) {
        int idArticulo = Integer.parseInt(request.getParameter("idArticulo"));
        this.articulo = this.articuloA.findArticleID(idArticulo);
        try {
            request.setAttribute("articulo", this.articulo);
            request.getRequestDispatcher("/WEB-INF/paginas/coordinador/coordinadorModificarArticulo.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Shows the list of wiki articles where the users supervisor are assigned
     *
     * @param request
     * @param response
     */
    private void listarArticulos(HttpServletRequest request, HttpServletResponse response) {
        int idWiki = Integer.parseInt(request.getParameter("idWiki"));
        Wiki wiki = this.wikiA.finWikiID(idWiki);
        this.wiki = wiki;

        List<Articulo> articulos = new ArrayList();
        for (Articulo articulo : this.wikiA.findArticlesID(wiki)) {
            for (Usuario usuario : this.articuloA.findUsersID(articulo)) {
                if (this.usuario.getId() == usuario.getId()) {
                    articulos.add(articulo);
                }
            }
        }
        try {
            request.setAttribute("articulos", articulos);
            request.getRequestDispatcher("/WEB-INF/paginas/coordinador/coordinadorListarArticulos.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Removes supervisors from the selected Wiki
     *
     * @param request
     * @param response
     */
    private void eliminarUsuarioWiki(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idUsuario = Integer.parseInt(request.getParameter("usuarioId"));
            Usuario usuario = this.usuarioA.findUserID(idUsuario);
            this.wikiA.deleteUser(this.wiki, usuario);

            List<Usuario> usuarios = this.getSupervisores(this.wikiA.findUsersID(wiki));

            List<Usuario> candidatos = this.getCandidatos(this.usuarioA.findAllUsers(), usuarios);

            request.setAttribute("wiki", wiki);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("candidatos", candidatos);
            request.getRequestDispatcher("/WEB-INF/paginas/coordinador/coordinadorUsuariosWiki.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a list of supervisors from the user list
     *
     * @param usuariosAux
     * @return
     */
    private List<Usuario> getSupervisores(List<Usuario> usuariosAux) {
        List<Usuario> usuarios = new ArrayList();
        if (!usuariosAux.isEmpty()) {
            for (Usuario user : usuariosAux) {
                if (user instanceof Supervisor) {
                    usuarios.add(user);
                }
            }
        }

        return usuarios;
    }

    /**
     * Returns a list of supervisors not included in the article/wiki
     *
     * @param usuariosSistema
     * @param usuariosAgregados
     * @return
     */
    private List<Usuario> getCandidatos(List<Usuario> usuariosSistema, List<Usuario> usuariosAgregados) {
        List<Usuario> usuarios = new ArrayList();
        for (int i = 0; i < usuariosSistema.size(); i++) {
            Usuario usuario = usuariosSistema.get(i);
            if(usuario instanceof Supervisor){
                usuarios.add(usuario);
            }
        }
        
        for(Usuario agregados: usuariosAgregados){
            for(int i =0; i<usuarios.size();i++){
                Usuario usuario = usuarios.get(i);
                if(agregados.getId()==usuario.getId()){
                    usuarios.remove(i);
                }
            }
        }
       

        return usuarios;
    }

    /**
     * Processes the request to create an article
     *
     * @param request
     * @param response
     */
    private void agregarArticulo(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idWiki = Integer.parseInt(request.getParameter("idWiki"));
            Wiki wiki = this.wikiA.finWikiID(idWiki);
            this.wiki = wiki;
            request.getRequestDispatcher("/WEB-INF/paginas/coordinador/coordinadorCrearArticulo.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Receives doPost requests and redirects them to their corresponding methods
     * @param request
     * @param response 
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "crearArticulo":
                    this.crearArticulo(request, response);
                    break;
                case "gestionar":
                    this.gestionarWiki(request, response);
                    break;
                case "addUsuarioWiki":
                    this.addUsuarioWiki(request, response);
                    break;
                case "modificarArticulo":
                    this.modificarArticulo(request, response);
                    break;
                case "addUsuarioArticulo":
                    this.addUserArticle(request, response);
                    break;
                case "addContent":
                    this.addContent(request, response);
                    break;
                case "inicios":
                    this.accionPorDefecto(request, response);
                    break;
            }
        } else {
            this.accionPorDefecto(request, response);
        }
    }

    /**
     * Add content with the information of the fields sent in the form
     *
     * @param request
     * @param response
     */
    private void addContent(HttpServletRequest request, HttpServletResponse response) {
        String subtitulo = request.getParameter("Subtitulo");
        String contenido = request.getParameter("Contenido");

        this.articuloA.insertContent(this.articulo, new Contenido(subtitulo, contenido));
        this.accionPorDefecto(request, response);
    }

    /**
     * Processes the role change request, if it exists. Otherwise it shows some options to request a role change
     *
     * @param request
     * @param response
     */
    public void solicitudRol(HttpServletRequest request, HttpServletResponse response) {

        if (this.usuario.getSolicitud() == null) {
            List<String> roles = new LinkedList<>();
            roles.add("Colaborador");
            roles.add("Gestor");
            roles.add("Supervisor");

            request.setAttribute("tipos", roles);
            request.setAttribute("usuario", this.usuario);
            try {
                request.getRequestDispatcher("/WEB-INF/paginas/coordinador/coordinadorCambioRol.jsp").forward(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            request.setAttribute("usuario", this.usuario);
            try {
                request.getRequestDispatcher("/WEB-INF/paginas/coordinador/coordinadorCambioRol.jsp").forward(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Add selected supervisors in the context article
     *
     * @param request
     * @param response
     */
    private void addUserArticle(HttpServletRequest request, HttpServletResponse response) {
        String[] ids = request.getParameterValues("checkbox");

        for (String indice : ids) {
            this.articuloA.insertUser(this.articulo, this.usuarioA.findUserID(Integer.parseInt(indice)));
        }

        this.accionPorDefecto(request, response);
    }

    /**
     * Receive the data from the form sent by the user coordinador and update the article of the context
     *
     * @param request
     * @param response
     */
    private void modificarArticulo(HttpServletRequest request, HttpServletResponse response) {
        String titulo = request.getParameter("Titulo");
        String descripcion = request.getParameter("Descripcion");
        this.articulo.setTituloArticulo(titulo);
        this.articulo.setDescripcion(descripcion);
        this.articuloA.updateArticle(articulo);
        this.accionPorDefecto(request, response);
    }

    /**
     * Processes user-selected IDs to add them as supervisors assigned to the Wiki
     *
     * @param request
     * @param response
     */
    private void addUsuarioWiki(HttpServletRequest request, HttpServletResponse response) {
        String[] ids = request.getParameterValues("checkbox");

        for (String indice : ids) {
            this.wikiA.insertUser(this.wiki, this.usuarioA.findUserID(Integer.parseInt(indice)));
        }

        List<Usuario> usuarios = this.getSupervisores(this.wikiA.findUsersID(this.wiki));

        List<Usuario> candidatos = this.getCandidatos(this.usuarioA.findAllUsers(), usuarios);

        this.accionPorDefecto(request, response);

    }

    /**
     * Shows the Wiki options selected. Also it supervisors and supervisors candidates. 
     * @param request
     * @param response 
     */
    private void gestionarWiki(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idWiki = Integer.parseInt(request.getParameter("idWiki"));
            Wiki wiki = this.wikiA.finWikiID(idWiki);
            this.wiki = wiki;

            List<Usuario> usuarios = this.getSupervisores(this.wikiA.findUsersID(wiki));

            List<Usuario> candidatos = this.getCandidatos(this.usuarioA.findAllUsers(), usuarios);

            request.setAttribute("wiki", wiki);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("candidatos", candidatos);
            request.getRequestDispatcher("/WEB-INF/paginas/coordinador/coordinadorUsuariosWiki.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CoordinadorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Create an article according to the parameters provided. Created article is automatically added to be managed by the user who creates it
     *
     * @param request
     * @param response
     */
    public void crearArticulo(HttpServletRequest request, HttpServletResponse response) {
        String titulo = request.getParameter("Titulo");
        String descripcion = request.getParameter("Descripcion");
        Articulo articulo = this.factoryA.crearArticulo(titulo, descripcion);
        this.articuloA.insertArticle(articulo);
        this.articuloA.insertUser(articulo, this.usuario);
        this.wikiA.insertContent(this.wiki, articulo);

        this.accionPorDefecto(request, response);
    }

    /**
     * Processes a default action that returns the user to the main menu
     *
     * @param request
     * @param response
     */
    public void accionPorDefecto(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Wiki> wikisTotal = this.wikiA.findAllWiki();
            List<Wiki> wikis = new ArrayList();
            HttpSession sesion = request.getSession();
            for (Wiki wiki : wikisTotal) {
                List<Usuario> usuario = this.wikiA.findUsersID(wiki);
                for (Usuario user : usuario) {
                    if (user.getId() == this.usuario.getId()) {
                        wikis.add(wiki);
                    }
                }
            }

            sesion.setAttribute("wikis", wikis);
            response.sendRedirect("coordinadorPrincipal.jsp");
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

}

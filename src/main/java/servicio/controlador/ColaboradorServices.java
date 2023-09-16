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
import dominio.usuario.Colaborador;
import dominio.usuario.Estado;
import dominio.usuario.Usuario;
import dominio.wiki.Wiki;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This class controls the user colaborador access to the system, his requests and changes. 
 * @author Toni
 */
public class ColaboradorServices implements Servicio {

    //The colaborador user is responsible for controlling the system at a particular time.
    private Usuario usuario;
    //Usuario acceso field
    private final UsuarioAcceso usuarioA;
    //Wiki access field
    private final WikiAcceso wikiA;
    //Articulo access field
    private final ArticuloAcceso articuloA;
    //File manager field
    private ManejadorArchivo manejador;

    /**
     * ColaboradorServices constructor with the user who will control the system
     * @param usuario 
     */
    public ColaboradorServices(Usuario usuario) {
        this.usuario = usuario;
        this.articuloA = new ArticuloAccesoImpl();
        this.usuarioA = new UsuarioAccesoImpl();
        this.wikiA = new WikiAccesoImpl();
        this.manejador = null;
    }

    /**
     * Set the user
     * @param usuario 
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
                case "hModificacion":
                    this.historialModificacion(request, response);
                    break;
                case "sRol":
                    this.solicitudRol(request, response);
                    break;
                case "peticionRol":
                    this.registroRol(request, response);
                    break;
                case "listarArticulo":
                    this.listarArticulo(request, response);
                    break;
                case "mostrarArticulo":
                    this.mostrarArticulo(request, response);
                    break;
                case "descargar":
                    this.manejador.devolverArchivo(request, response);
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
     * Displays the selected article with its content
     *
     * @param request
     * @param response
     */
    private void mostrarArticulo(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idArticulo = Integer.parseInt(request.getParameter("idArticulo"));
            Articulo articulo = this.articuloA.findArticleID(idArticulo);
            this.manejador = new ManejadorArchivo(articulo);
            List<Contenido> contenido = this.articuloA.findContentID(articulo);
            request.setAttribute("articulo", articulo);
            request.setAttribute("contenidos", contenido);
            request.getRequestDispatcher("/WEB-INF/paginas/colaborador/colaboradorArticulo.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a list of articles assigned to the user colaborador
     *
     * @param request
     * @param response
     */
    private void listarArticulo(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idWiki = Integer.parseInt(request.getParameter("idWiki"));
            Wiki wiki = this.wikiA.finWikiID(idWiki);
            List<Articulo> articulos = this.wikiA.findArticlesID(wiki);
            
            for(int i=0; i<articulos.size(); i++){
                Articulo art = articulos.get(i);
                for(Usuario usuario: this.articuloA.findUsersID(art)){
                    if(usuario instanceof Colaborador){
                        if(this.usuario.getId() == usuario.getId()){
                            articulos.remove(i);
                        }
                    }
                }
            }
     

            request.setAttribute("articulos", articulos);
            request.getRequestDispatcher("/WEB-INF/paginas/colaborador/colaboradorListarArticulos.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Receives the role request from the colaborador user to register it in the system
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
            request.getRequestDispatcher("/WEB-INF/paginas/colaborador/colaboradorCambioRol.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Process and return the list of article modification requests that the user colaborador has.
     *
     * @param request
     * @param response
     */
    public void historialModificacion(HttpServletRequest request, HttpServletResponse response) {
        List<Articulo> articulos = this.articuloA.findAllArticles();
        List<Estado> estados = this.usuarioA.findUserStates(this.usuario);
        HashMap<String, String> respuesta = new HashMap();
        for (Articulo art : articulos) {
            for (Estado estado : estados) {
                if (art.getId() == estado.getIdArticulo()) {
                    respuesta.put(art.getTituloArticulo(), estado.getEstado());
                }
            }
        }
        request.setAttribute("solicitudes", respuesta);
        try {
            request.getRequestDispatcher("/WEB-INF/paginas/colaborador/colaboradorListaModificaciones.jsp").forward(request, response);
        } catch (ServletException ex) {
            ex.getMessage();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    /**
     * Processes the role change request, if it exists. Otherwise, it displays the options for requesting a role change
     *
     * @param request
     * @param response
     */
    public void solicitudRol(HttpServletRequest request, HttpServletResponse response) {

        if (this.usuario.getSolicitud() == null) {
            List<String> roles = new LinkedList<>();
            roles.add("Coordinador");
            roles.add("Gestor");
            roles.add("Supervisor");

            request.setAttribute("tipos", roles);
            request.setAttribute("usuario", this.usuario);
            try {
                request.getRequestDispatcher("/WEB-INF/paginas/colaborador/colaboradorCambioRol.jsp").forward(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            request.setAttribute("usuario", this.usuario);
            try {
                request.getRequestDispatcher("/WEB-INF/paginas/colaborador/colaboradorCambioRol.jsp").forward(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Processes a default action that returns the user to the main menu
     *
     * @param request
     * @param response
     */
    public void accionPorDefecto(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Wiki> wikis = this.wikiA.findAllWiki();
            HttpSession sesion = request.getSession();
            sesion.setAttribute("wikis", wikis);
            response.sendRedirect("colaboradorPrincipal.jsp");
        } catch (IOException ex) {
            ex.getMessage();
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
                case "inicios":
                    this.accionPorDefecto(request, response);
                    break;
                case "cargar":
                    this.manejador.guardarContenido(request, response, this.usuario.getId(), false);
                    break;
            }
        } else {
            this.accionPorDefecto(request, response);
        }
    }

}

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
import dominio.wiki.Wiki;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servicio.utilidades.FactoryColaborador;
import servicio.utilidades.FactoryUsuario;
import servicio.web.Controlador;

/**
 * This class controls the guest user access to the system. This user has no account in the system
 * @author Toni
 */
public class UsuarioServices implements Servicio {

    //Article access field
    private ArticuloAcceso articulo;
    //User access field
    private UsuarioAcceso usuario;
    //Wiki access field
    private WikiAcceso wiki;
    //User factory field
    private FactoryUsuario fusuarios;
    
    /**
     * UsuarioServices constructor. It doesn't need a context user
     */
    public UsuarioServices() {
        this.articulo = new ArticuloAccesoImpl();
        this.usuario = new UsuarioAccesoImpl();
        this.wiki = new WikiAccesoImpl();
        this.fusuarios = null;
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
                case "listarArticulo":
                    this.listarArticulo(request, response);
                    break;

                case "mostrarArticulo":
                    this.mostrarArticulo(request, response);
                    break;
                    
                case "return":
                    this.defaultOpcion(request, response);
                    break;

            }
        } else {
            this.defaultOpcion(request, response);
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
        if(accion != null){
            switch(accion){
                case "registro":
                    this.registroUsuario(request, response);
                    break;
            }
        }
        
    }
    
    /**
     * Registers a user as a colaborador according to the data received from the form
     * @param request
     * @param response 
     */
    private void registroUsuario(HttpServletRequest request, HttpServletResponse response){
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String apellidoDos = request.getParameter("apellidoDos");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        
        this.fusuarios = new FactoryColaborador();
        this.fusuarios.addUsuario(nombre, apellido, apellidoDos, direccion, telefono);
        
        //Redirigimos a la pantalla por defecto 
        this.defaultOpcion(request, response);
        
    }
    
    /**
     * Shows the article selected by the client
     * @param request
     * @param response 
     */
    private void mostrarArticulo(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idArticulo = Integer.parseInt(request.getParameter("idArticulo"));
            Articulo articulo = this.articulo.findArticleID(idArticulo);
            List<Contenido> contenido = this.articulo.findContentID(articulo);
            request.setAttribute("articulo", articulo);
            request.setAttribute("contenidos", contenido);
            
            request.getRequestDispatcher("/WEB-INF/paginas/usuario/desplegarArticulo.jsp").forward(request, response);
        } catch (ServletException ex) {
            ex.getMessage();
        } catch (IOException ex) {
            ex.getMessage();
        }
        
    }
    
    /**
     * Processes a default action that returns the user to the main menu
     * @param request
     * @param response 
     */
    private void defaultOpcion(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Wiki> wikis = this.wiki.findAllWiki();
            HttpSession sesion = request.getSession();
            sesion.setAttribute("wikis", wikis);
            response.sendRedirect("usuarios.jsp");
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
    
    /**
     * Shows a list of articles that belong to the selected wiki
     * @param request
     * @param response 
     */
    private void listarArticulo(HttpServletRequest request, HttpServletResponse response) {
        int idWiki = Integer.parseInt(request.getParameter("idWiki"));
        Wiki wiki = this.wiki.finWikiID(idWiki);
        List<Articulo> articulos = this.wiki.findArticlesID(wiki);
        request.setAttribute("articulos", articulos);
        try {
            request.getRequestDispatcher("/WEB-INF/paginas/usuario/listadoArticulos.jsp").forward(request, response);
        } catch (ServletException ex) {
            ex.getMessage();
        } catch (IOException ex) {
            ex.getMessage();
        }

    }
}

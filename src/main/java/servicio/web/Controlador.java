/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio.web;

import servicio.controlador.CoordinadorServices;
import servicio.controlador.GestorServices;
import servicio.controlador.SupervisorServices;
import servicio.controlador.ColaboradorServices;
import servicio.controlador.UsuarioServices;
import servicio.controlador.Servicio;
import dominio.dao.acceso.UsuarioAccesoImpl;
import dominio.dao.acceso.WikiAccesoImpl;
import dominio.dao.acceso.WikiAcceso;
import dominio.dao.acceso.UsuarioAcceso;
import dominio.bbdd.BBDD;
import dominio.usuario.*;
import dominio.wiki.Wiki;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * This class is the central controller that processes all view requests and redirects them to the appropriate controllers. 
 * @author Toni
 */
@MultipartConfig
@WebServlet("/Controlador")
public class Controlador extends HttpServlet {
    //User access field
    private UsuarioAcceso usuario = new UsuarioAccesoImpl();
    //Interface used to apply the strategy pattern
    private Servicio servicio = null;
    //BBDD access field
    private BBDD bbdd = null;

    /**
     * Receives doGet requests and redirects them to their corresponding methods.
     * If the database has not been created, it is created. 
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (bbdd == null) {
            try {
                bbdd = new BBDD();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        String accion = request.getParameter("accion");
        if (accion != null) {
            if (accion.equals("cSesion")) {
                servicio = new UsuarioServices();
                this.defaultOpcion(request, response);
            }
            else {
                this.servicio.doGet(request, response);
            }

        } else {
            servicio = new UsuarioServices();
            this.servicio.doGet(request, response);
        }
    }
    
    
   

    /**
     * The method redirects requests to a controller based on the user logging in
     * @param usuario 
     */
    private void setStrategy(Usuario usuario) {
        if (usuario instanceof Gestor) {
            this.servicio = new GestorServices(usuario);
        } else if (usuario instanceof Colaborador) {
            this.servicio = new ColaboradorServices(usuario);
        } else if (usuario instanceof Coordinador) {
            this.servicio = new CoordinadorServices(usuario);
        } else if (usuario instanceof Supervisor) {
            this.servicio = new SupervisorServices(usuario);
        } else if (usuario == null) {
            this.servicio = new UsuarioServices();
        }
    }

   
    /**
     * This method allows access to the system according to the data provided. If the data is not valid, it sends an alert. 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario usuario = null;
        String accion = request.getParameter("accion");
        if (accion != null) {
            if (accion.equals("inicios")) {
                usuario = this.inicioSesion(request.getParameter("nombre"), request.getParameter("pass"));
                if (usuario != null) {           
                    this.setStrategy(usuario);
                    this.servicio.doPost(request, response);
                } else {

                    PrintWriter out = response.getWriter();
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Debe ingresar los datos de un usuario válido')");
                    out.println("location='inicioSesion.jsp';");
                    out.println("</script>");
                    this.setStrategy(usuario);
                }
            } else {
                this.servicio.doPost(request, response);
            }
        }

    }

    /**
     * Returns a user if the data is valid
     *
     * @param nombre
     * @param pass
     * @return: If the user exists returns the user, if not exist returns null
     */
    private Usuario inicioSesion(String nombre, String pass) {

        List<Usuario> usuarios = this.usuario.findAllUsers();
        Iterator<Usuario> users = usuarios.iterator();
        while (users.hasNext()) {
            Usuario usuario = users.next();

            if (usuario.getAcceso().getUsuario().equals(nombre) && usuario.getAcceso().getPassword().equals(pass)) {
                return usuario;
            }
        }
        return null;
    }
    
    /**
     * Processes a default action that returns the user to the main menu
     *
     * @param request
     * @param response
     */
    private void defaultOpcion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WikiAcceso wikiA = new WikiAccesoImpl();
        List<Wiki> wiki = wikiA.findAllWiki();
        request.setAttribute("wikis", wiki);
        request.getRequestDispatcher("usuarios.jsp").forward(request, response);
    }
}

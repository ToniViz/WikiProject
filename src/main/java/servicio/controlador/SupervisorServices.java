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
import dominio.usuario.*;
import dominio.wiki.Wiki;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servicio.utilidades.EstadoCreator;
import servicio.utilidades.FactoryColaborador;

/**
 * This class controls the user supervisor access to the system, his requests and changes. Controls users colaborador access to articles and wikis. 
 * @author Toni
 */
public class SupervisorServices implements Servicio {

    //Context user
    private Usuario usuario;
    //Article access field
    private ArticuloAcceso articuloA;
    //Wiki access field
    private WikiAcceso wikiA;
    //User access field
    private UsuarioAcceso usuarioA;
    //File manager field
    private ManejadorArchivo manejador;
    //State creator factory
    private EstadoCreator estado;

    /**
     * GestorServices constructor with the user who will control the system
     * @param usuario 
     */
    public SupervisorServices(Usuario usuario) {
        this.usuario = usuario;
        this.articuloA = new ArticuloAccesoImpl();
        this.usuarioA = new UsuarioAccesoImpl();
        this.wikiA = new WikiAccesoImpl();
        this.manejador = null;
        this.estado = new FactoryColaborador();
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
                case "mostrarModificado":
                    this.mostrarModificado(request, response);
                    break;
                case "descargar":
                    this.manejador.devolverArchivo(request, response);
                    break;
                case "descartar":
                    this.descartarCambios(request, response);
                    break;
                case "vetarUsuario":
                    this.vetarUsuario(request, response);
                    break;
                case "return":
                    this.accionPorDefecto(request, response);
                    break;
                case "deleteVeto":
                    this.deleteVeto(request, response);
                    break;

            }
        } else {
            this.accionPorDefecto(request, response);
        }

    }
    
    /**
     * Removes a user colaborador veto on the article from context
     * @param request
     * @param response 
     */
    private void deleteVeto(HttpServletRequest request, HttpServletResponse response){
        try {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            Usuario usuarioA = this.usuarioA.findUserID(idUsuario);
            Articulo articulo = this.manejador.getArticulo();
            this.articuloA.deleteUser(articulo, usuarioA);
            
            List<Contenido> contenidos = this.articuloA.findContentID(articulo);
            List<Usuario> colaboradores = this.getColaboradores(articulo);
            List<Usuario> usuariosV = this.getColaboradoresVeto(articulo);
            
            
            
            
            request.setAttribute("usuariosV", usuariosV);
            request.setAttribute("usuarios", colaboradores);
            request.setAttribute("articulo", articulo);
            request.setAttribute("contenidos", contenidos);
            request.getRequestDispatcher("/WEB-INF/paginas/supervisor/supervisorArticuloSupervision.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(SupervisorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SupervisorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Returns a list of colaboradores
     * @return: colaboradores list
     */
    private List<Usuario> getColaboradores(Articulo articulo){
        List<Usuario> colaboradores = new ArrayList();
         List<Usuario> usuarios = this.usuarioA.findAllUsers();
            for (int i = 0; i < usuarios.size(); i++) {
                Usuario usuario = usuarios.get(i);
                if (usuario instanceof Colaborador) {
                    
                    colaboradores.add(usuario);
                    
                }
            }
            for (int i = 0; i < colaboradores.size(); i++) {
            Usuario usuario = colaboradores.get(i);

            if (articulo.isBorrador() == usuario.getId()) {
                colaboradores.remove(i);
            }

            for (Usuario user : this.articuloA.findUsersID(articulo)) {
                if (user.getId() == usuario.getId()) {              
                    colaboradores.remove(i);
                }
            }
        }
            return colaboradores;
    }
    
    /**
     * Returns a list of banned colaboradores to the context article
     * @param articulo
     * @return: list of colaboradores with a veto
     */
    private List<Usuario> getColaboradoresVeto(Articulo articulo){
        List<Usuario> usuariosV = new ArrayList();
        for(Usuario user: this.articuloA.findUsersID(articulo)){
                if(user instanceof Colaborador){
                    usuariosV.add(user);
                }
            }
        return usuariosV;
    }
    
    
    /**
     * Processes the request to veto a user and saves it on the database
     *
     * @param request
     * @param response
     */
    private void vetarUsuario(HttpServletRequest request, HttpServletResponse response) {
        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        Usuario usuarioA = this.usuarioA.findUserID(idUsuario);
        Articulo articulo = this.manejador.getArticulo();

        this.articuloA.insertUser(articulo, usuarioA);
        
        List<Contenido> contenidos = this.articuloA.findContentID(articulo);
        List<Usuario> colaboradores = this.getColaboradores(articulo);
        List<Usuario> usuariosV = this.getColaboradoresVeto(articulo);
        

        
        request.setAttribute("usuariosV", usuariosV);
        request.setAttribute("usuarios", colaboradores);
        request.setAttribute("articulo", articulo);
        request.setAttribute("contenidos", contenidos);
        try {
            request.getRequestDispatcher("/WEB-INF/paginas/supervisor/supervisorArticuloSupervision.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(SupervisorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SupervisorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Processes the rejection of the request for change in the article by a colaborador user
     *
     * @param request
     * @param response
     */
    private void descartarCambios(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idArticulo = Integer.parseInt(request.getParameter("idArticulo"));
            Articulo articulo = this.articuloA.findArticleID(idArticulo);
            Usuario usuarioA = this.usuarioA.findUserID(articulo.isBorrador());
            articulo.setArchivo(null);
            articulo.setBorrador(0);
            this.estado.updateEstado(usuarioA, articulo, "Rechazado");
            List<Contenido> contenidos = this.articuloA.findContentID(articulo);

            List<Usuario> colaboradores = this.getColaboradores(articulo);
            List<Usuario> usuariosV = this.getColaboradoresVeto(articulo);
       

            request.setAttribute("usuariosV", usuariosV);
            request.setAttribute("usuarios", colaboradores);
            request.setAttribute("articulo", articulo);
            request.setAttribute("contenidos", contenidos);
            request.getRequestDispatcher("/WEB-INF/paginas/supervisor/supervisorArticuloSupervision.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(SupervisorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SupervisorServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Shows the article and its content
     *
     * @param request
     * @param response
     */
    private void mostrarArticulo(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idArticulo = Integer.parseInt(request.getParameter("idArticulo"));
            Articulo articulo = this.articuloA.findArticleID(idArticulo);
            articulo.setContenido((ArrayList<Contenido>) this.articuloA.findContentID(articulo));
            this.manejador = new ManejadorArchivo(articulo);
            List<Contenido> contenido = this.articuloA.findContentID(articulo);
            request.setAttribute("articulo", articulo);
            request.setAttribute("contenidos", contenido);
            request.getRequestDispatcher("/WEB-INF/paginas/supervisor/supervisorArticulo.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Shows the article and requests changes made to it for review
     *
     * @param request
     * @param response
     */
    private void mostrarModificado(HttpServletRequest request, HttpServletResponse response) {
        try {
            LinkedHashMap<String, String> cambios = null;
            int idArticulo = Integer.parseInt(request.getParameter("idArticulo"));
            Articulo articulo = this.articuloA.findArticleID(idArticulo);
            articulo.setContenido((ArrayList<Contenido>) this.articuloA.findContentID(articulo));
            this.manejador = new ManejadorArchivo(articulo);
            List<Contenido> contenido = this.articuloA.findContentID(articulo);

            if (!(articulo.getArchivo() == null)) {
                cambios = this.manejador.getDiferencias();
            }

            //Envía los colaboradores que no tienen un veto en el artículo
            List<Usuario> colaboradores = this.getColaboradores(articulo);
            List<Usuario> usuariosV = this.getColaboradoresVeto(articulo);
       

            request.setAttribute("usuariosV", usuariosV);
            request.setAttribute("usuarios", colaboradores);
            request.setAttribute("cambios", cambios);
            request.setAttribute("articulo", articulo);
            request.setAttribute("contenidos", contenido);
            request.getRequestDispatcher("/WEB-INF/paginas/supervisor/supervisorArticuloSupervision.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *  Returns a list of articles assigned to the user colaborador
     *
     * @param request
     * @param response
     */
    private void listarArticulo(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idWiki = Integer.parseInt(request.getParameter("idWiki"));
            Wiki wiki = this.wikiA.finWikiID(idWiki);
            List<Articulo> articulosLista = this.wikiA.findArticlesID(wiki);

            List<Articulo> revisiones = new ArrayList();
            for (Articulo articulo : articulosLista) {
                for (Usuario usuario : this.articuloA.findUsersID(articulo)) {
                    if (usuario.getId() == this.usuario.getId()) {
                        revisiones.add(articulo);
                    }
                }
            }
            for (int i = 0; i < articulosLista.size(); i++) {
                Articulo articulo = articulosLista.get(i);
                for (Usuario usuario : this.articuloA.findUsersID(articulo)) {
                    if (usuario.getId() == this.usuario.getId()) {
                        articulosLista.remove(i);
                    }
                }
            }
            request.setAttribute("articulos", articulosLista);
            request.setAttribute("revisiones", revisiones);
            request.getRequestDispatcher("/WEB-INF/paginas/supervisor/supervisorListarArticulos.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                request.getRequestDispatcher("/WEB-INF/paginas/supervisor/supervisorCambioRol.jsp").forward(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            request.setAttribute("usuario", this.usuario);
            try {
                request.getRequestDispatcher("/WEB-INF/paginas/supervisor/supervisorCambioRol.jsp").forward(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Saves the user role request into the system
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
            request.getRequestDispatcher("/WEB-INF/paginas/supervisor/supervisorCambioRol.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ColaboradorServices.class.getName()).log(Level.SEVERE, null, ex);
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
                case "cambiosArticulo":
                    this.manejador.cambioArticulo(request, response, this.usuario.getId());
                    break;
                case "cargar":
                    this.manejador.guardarContenido(request, response, 0, true);
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
            response.sendRedirect("supervisorPrincipal.jsp");
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

}


package servicio.controlador;

import dominio.dao.acceso.ArticuloAcceso;
import dominio.dao.acceso.ArticuloAccesoImpl;
import dominio.dao.acceso.UsuarioAcceso;
import dominio.dao.acceso.UsuarioAccesoImpl;
import dominio.dao.acceso.WikiAcceso;
import dominio.dao.acceso.WikiAccesoImpl;
import dominio.articulo.Articulo;
import dominio.articulo.Contenido;
import dominio.usuario.Coordinador;
import dominio.usuario.Gestor;
import dominio.usuario.Supervisor;
import dominio.usuario.Usuario;
import dominio.wiki.Wiki;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servicio.utilidades.*;

/**
 * This class controls the user gestor access to the system, his requests and changes. Controls all users access to articles and wikis. 
 * @author Toni
 */
public class GestorServices implements Servicio {

    //Article access field
    private ArticuloAcceso articuloA;
    private WikiAcceso wikiA;
    //Wiki access field
    private UsuarioAcceso usuarioA;
    //The gestor user is responsible for controlling the system at a particular time
    private Usuario usuario;
    //Wiki used in the context
    private Wiki wiki;
    //Article used in the context
    private Articulo articulo;
    //Wiki and Article factory to create them
    private FactoryArtWiki factoriAW;
    //Users factory to create them
    private FactoryUsuario factoryU;

    /**
     * GestorServices constructor with the user who will control the system
     * @param usuario 
     */
    public GestorServices(Usuario usuario) {
        this.usuario = usuario;
        this.articuloA = new ArticuloAccesoImpl();
        this.wikiA = new WikiAccesoImpl();
        this.usuarioA = new UsuarioAccesoImpl();
        this.wiki = null;
        this.articulo = null;
        this.factoriAW = new FactoryArtWikiImpl();
        this.factoryU = null;
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
                case "modificar":
                    this.formularioWiki(request, response);
                    break;
                case "delete":
                    this.deleteWiki(request, response);
                    break;
                case "gestionarWiki":
                    this.usuariosWiki(request, response);
                    break;
                case "deleteUserWiki":
                    this.deleteUserWiki(request, response);
                    break;
                case "listarArticulos":
                    this.listarArticulos(request, response);
                    break;
                case "mostrarArticulo":
                    this.mostrarArticulo(request, response);
                    break;
                case "eliminarRolArticulo":
                    this.eliminarRolArticulo(request, response);
                    break;
                case "addWiki":
                    this.addWiki(request, response);
                    break;
                case "usuariosPeticiones":
                    this.usuariosPeticiones(request, response);
                    break;
                case "aceptarSol":
                    this.aceptarSolicitud(request, response);
                    break;
                case "rechazarSol":
                    break;
                case "return":
                    this.accionPorDefecto(request, response);
                    break;
                case "eliminar":
                    this.deleteUser(request, response);
                    break;
            }
        } else {
            this.accionPorDefecto(request, response);
        }

    }

    /**
     * Delete the user from the system
     *
     * @param request
     * @param response
     */
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) {

        try {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            Usuario usuario = this.usuarioA.findUserID(idUsuario);
            this.usuarioA.deleteUser(usuario);

            List<Usuario> usuariosS = new ArrayList();
            List<Usuario> usuariosI = new ArrayList();
            List<Usuario> usuarios = this.usuarioA.findAllUsers();

            for (Usuario user : usuarios) {
                if (user.getSolicitud() != null) {
                    usuariosS.add(user);
                }
                if (!(user instanceof Gestor)) {
                    usuariosI.add(user);
                }
            }

            request.setAttribute("usuariosI", usuariosI);
            request.setAttribute("usuariosS", usuariosS);
            request.getRequestDispatcher("/WEB-INF/paginas/gestor/gestorGestUsuarios.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Rejects a user’s role change request
     *
     * @param request
     * @param response
     */
    private void rechazarSolicitud(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            Usuario usuario = this.usuarioA.findUserID(idUsuario);
            usuario.setSolicitud(null);
            this.usuarioA.updateUser(usuario);

            List<Usuario> usuariosS = new ArrayList();
            List<Usuario> usuariosI = new ArrayList();
            List<Usuario> usuarios = this.usuarioA.findAllUsers();

            for (Usuario user : usuarios) {
                if (user.getSolicitud() != null) {
                    usuariosS.add(user);
                }
                if (!(user instanceof Gestor)) {
                    usuariosI.add(user);
                }
            }

            request.setAttribute("usuariosI", usuariosI);
            request.setAttribute("usuariosS", usuariosS);
            request.getRequestDispatcher("/WEB-INF/paginas/gestor/gestorGestUsuarios.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Processes a user’s role change request. Creates a new user without any associated articles or wiki and with the same user data. 
     *
     * @param request
     * @param response
     */
    private void aceptarSolicitud(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            Usuario usuarioN = null;
            Usuario usuario = this.usuarioA.findUserID(idUsuario);
            String solicitud = usuario.getSolicitud();
            switch (solicitud) {
                case "Gestor":
                    this.factoryU = new FactoryGestor();
                    usuarioN = this.factoryU.crearUsuario(usuario.getNombre(), usuario.getApellido(), usuario.getApellidoDos(), usuario.getDireccion(), usuario.getTelefono());
                    break;
                case "Supervisor":
                    this.factoryU = new FactorySupervisor();
                    usuarioN = this.factoryU.crearUsuario(usuario.getNombre(), usuario.getApellido(), usuario.getApellidoDos(), usuario.getDireccion(), usuario.getTelefono());
                    break;
                case "Colaborador":
                    this.factoryU = new FactoryColaborador();
                    usuarioN = this.factoryU.crearUsuario(usuario.getNombre(), usuario.getApellido(), usuario.getApellidoDos(), usuario.getDireccion(), usuario.getTelefono());
                    break;
                case "Coordinador":
                    this.factoryU = new FactoryCoordinador();
                    usuarioN = this.factoryU.crearUsuario(usuario.getNombre(), usuario.getApellido(), usuario.getApellidoDos(), usuario.getDireccion(), usuario.getTelefono());
                    break;
            }

            this.usuarioA.deleteUser(usuario);
            this.usuarioA.insertUser(usuarioN);
            List<Usuario> usuariosS = new ArrayList();
            List<Usuario> usuariosI = new ArrayList();
            List<Usuario> usuarios = this.usuarioA.findAllUsers();

            for (Usuario user : usuarios) {
                if (user.getSolicitud() != null) {
                    usuariosS.add(user);
                }
                if (!(user instanceof Gestor)) {
                    usuariosI.add(user);
                }
            }

            request.setAttribute("usuariosI", usuariosI);
            request.setAttribute("usuariosS", usuariosS);
            request.getRequestDispatcher("/WEB-INF/paginas/gestor/gestorGestUsuarios.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Shows the user role requests section and users management
     *
     * @param request
     * @param response
     */
    private void usuariosPeticiones(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Usuario> usuariosS = new ArrayList();
            List<Usuario> usuariosI = new ArrayList();
            List<Usuario> usuarios = this.usuarioA.findAllUsers();

            for (Usuario user : usuarios) {
                if (user.getSolicitud() != null) {
                    usuariosS.add(user);
                }
                if (!(user instanceof Gestor)) {
                    usuariosI.add(user);
                }
            }

            request.setAttribute("usuariosI", usuariosI);
            request.setAttribute("usuariosS", usuariosS);
            request.getRequestDispatcher("/WEB-INF/paginas/gestor/gestorGestUsuarios.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Show the form to create a new wiki
     *
     * @param request
     * @param response
     */
    private void addWiki(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/WEB-INF/paginas/gestor/gestorCrearWiki.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Removes the user from the role assigned in the context article
     *
     * @param request
     * @param response
     */
    private void eliminarRolArticulo(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            Usuario usuario = this.usuarioA.findUserID(idUsuario);
            this.articuloA.deleteUser(this.articulo, usuario);
            List<Usuario> usuarios = this.getUsuariosWikiArticulo(this.articuloA.findUsersID(articulo));
            List<Usuario> candidatos = this.getUsuariosNoVinculados(this.usuarioA.findAllUsers(), usuarios);
            request.setAttribute("articulo", this.articulo);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("candidatos", candidatos);
            request.getRequestDispatcher("/WEB-INF/paginas/gestor/gestorUsuariosArticulo.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Shows the selected article and its options
     *
     * @param request
     * @param response
     */
    private void mostrarArticulo(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idArticulo = Integer.parseInt(request.getParameter("idArticulo"));
            this.articulo = this.articuloA.findArticleID(idArticulo);
            List<Contenido> contenidos = this.articuloA.findContentID(articulo);
            List<Usuario> usuarios = this.getUsuariosWikiArticulo(this.articuloA.findUsersID(articulo));
            List<Usuario> candidatos = this.getUsuariosNoVinculados(this.usuarioA.findAllUsers(), usuarios);
            request.setAttribute("contenidos", contenidos);
            request.setAttribute("articulo", this.articulo);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("candidatos", candidatos);
            request.getRequestDispatcher("/WEB-INF/paginas/gestor/gestorUsuariosArticulo.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Shows articles linked to the wiki
     *
     * @param request
     * @param response
     */
    private void listarArticulos(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idWiki = Integer.parseInt(request.getParameter("idWiki"));
            this.wiki = this.wikiA.finWikiID(idWiki);
            List<Articulo> articulos = this.wikiA.findArticlesID(this.wiki);
            request.setAttribute("articulos", articulos);
            request.getRequestDispatcher("/WEB-INF/paginas/gestor/gestorListaArticulo.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Removes the user from the selected wiki
     *
     * @param request
     * @param response
     */
    private void deleteUserWiki(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            Usuario usuario = this.usuarioA.findUserID(idUsuario);
            this.wikiA.deleteUser(this.wiki, usuario);
            List<Usuario> usuarios = this.getUsuariosWikiArticulo(this.wikiA.findUsersID(this.wiki));
            List<Usuario> candidatos = this.getUsuariosNoVinculados(this.usuarioA.findAllUsers(), usuarios);
            request.setAttribute("wiki", this.wiki);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("candidatos", candidatos);
            request.getRequestDispatcher("/WEB-INF/paginas/gestor/gestorUsuariosWiki.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Shows the user section to assign or delete from a Wiki
     *
     * @param request
     * @param response
     */
    private void usuariosWiki(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idWiki = Integer.parseInt(request.getParameter("idWiki"));
            this.wiki = this.wikiA.finWikiID(idWiki);
            List<Usuario> usuarios = this.getUsuariosWikiArticulo(this.wikiA.findUsersID(wiki));
            List<Usuario> candidatos = this.getUsuariosNoVinculados(this.usuarioA.findAllUsers(), usuarios);
            request.setAttribute("wiki", this.wiki);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("candidatos", candidatos);
            request.getRequestDispatcher("/WEB-INF/paginas/gestor/gestorUsuariosWiki.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns users coordinadores and colaboradores who are linked to a wiki
     *
     * @param usuarios
     * @return
     */
    private List<Usuario> getUsuariosWikiArticulo(List<Usuario> usuarios) {
        List<Usuario> usuarioAux = new ArrayList();
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Coordinador) {
                usuarioAux.add(usuario);
            } else if (usuario instanceof Supervisor) {
                usuarioAux.add(usuario);
            }
        }
        return usuarioAux;
    }

    /**
     * Returns a list of users not included in the wiki/article passed as second parameter
     *
     * @param usuarioSistema
     * @param usuarioVinculado
     * @return
     */
    private List<Usuario> getUsuariosNoVinculados(List<Usuario> usuarioSistema, List<Usuario> usuarioVinculado) {
        List<Usuario> usuarioAux = new ArrayList();
        for (Usuario usuario : usuarioSistema) {
            if (usuario instanceof Coordinador) {
                usuarioAux.add(usuario);
            } else if (usuario instanceof Supervisor) {
                usuarioAux.add(usuario);
            }
        }

        for (Usuario agregados : usuarioVinculado) {
            for (int i = 0; i < usuarioAux.size(); i++) {
                Usuario usuario = usuarioAux.get(i);
                if (agregados.getId() == usuario.getId()) {
                    usuarioAux.remove(i);
                }
            }
        }

        return usuarioAux;
    }

    /**
     * Delete the Wiki selected by the user manager
     *
     * @param request
     * @param response
     */
    private void deleteWiki(HttpServletRequest request, HttpServletResponse response) {
        int idWiki = Integer.parseInt(request.getParameter("idWiki"));
        Wiki wiki = this.wikiA.finWikiID(idWiki);
        this.wikiA.deleteWiki(wiki);
        this.accionPorDefecto(request, response);
    }

    /**
     * Shows the form to modify to the selected Wiki
     *
     * @param request
     * @param response
     */
    private void formularioWiki(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idWiki = Integer.parseInt(request.getParameter("idWiki"));
            this.wiki = this.wikiA.finWikiID(idWiki);
            request.setAttribute("wiki", this.wiki);
            request.getRequestDispatcher("/WEB-INF/paginas/gestor/gestorModificarWiki.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
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
                case "modificarWiki":
                    this.modificarWiki(request, response);
                    break;
                case "addUserWiki":
                    this.addUserWiki(request, response);
                    break;
                case "addUserArticulo":
                    this.addUserArticulo(request, response);
                    break;
                case "crearWiki":
                    this.crearWiki(request, response);
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
     * Receive the form data and create a new Wiki in the system
     *
     * @param request
     * @param response
     */
    private void crearWiki(HttpServletRequest request, HttpServletResponse response) {
        String titulo = request.getParameter("Titulo");
        String descripcion = request.getParameter("Descripcion");
        Wiki wiki = this.factoriAW.crearWiki(titulo, descripcion);
        this.wikiA.insertWiki(wiki);

        this.accionPorDefecto(request, response);

    }

    /**
     * Add the selected supervisor/coordinador users in the context article
     *
     * @param request
     * @param response
     */
    private void addUserArticulo(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] idsUsuario = request.getParameterValues("checkbox");

            if (idsUsuario.length > 0) {
                for (String indice : idsUsuario) {
                    this.articuloA.insertUser(this.articulo, this.usuarioA.findUserID(Integer.parseInt(indice)));
                }
            }

            this.accionPorDefecto(request, response);
        } catch (Exception e) {
            try {
                List<Usuario> usuarios = this.getUsuariosWikiArticulo(this.articuloA.findUsersID(articulo));
                List<Usuario> candidatos = this.getUsuariosNoVinculados(this.usuarioA.findAllUsers(), usuarios);
                request.setAttribute("articulo", this.articulo);
                request.setAttribute("usuarios", usuarios);
                request.setAttribute("candidatos", candidatos);
                request.getRequestDispatcher("/WEB-INF/paginas/gestor/gestorUsuariosArticulo.jsp").forward(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     * Add users selected by the gestor to the wiki
     *
     * @param request
     * @param response
     */
    private void addUserWiki(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] idsUsuario = request.getParameterValues("checkbox");

            if (idsUsuario.length > 0) {
                for (String indice : idsUsuario) {
                    this.wikiA.insertUser(this.wiki, this.usuarioA.findUserID(Integer.parseInt(indice)));
                }
            }

            this.accionPorDefecto(request, response);
        } catch (Exception e) {
            try {
                List<Usuario> usuarios = this.getUsuariosWikiArticulo(this.wikiA.findUsersID(wiki));
                List<Usuario> candidatos = this.getUsuariosNoVinculados(this.usuarioA.findAllUsers(), usuarios);
                request.setAttribute("wiki", this.wiki);
                request.setAttribute("usuarios", usuarios);
                request.setAttribute("candidatos", candidatos);
                request.getRequestDispatcher("/WEB-INF/paginas/gestor/gestorUsuariosWiki.jsp").forward(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GestorServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Get the form parameters to modify to the context wiki
     *
     * @param request
     * @param response
     */
    private void modificarWiki(HttpServletRequest request, HttpServletResponse response) {
        String Titulo = request.getParameter("Titulo");
        String descripcion = request.getParameter("Descripcion");
        this.wiki.setTitulo(Titulo);
        this.wiki.setTematica(descripcion);
        this.wikiA.updateWiki(this.wiki);
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
            List<Wiki> wikis = this.wikiA.findAllWiki();
            HttpSession sesion = request.getSession();
            sesion.setAttribute("wikis", wikis);
            response.sendRedirect("gestorPrincipal.jsp");
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

}

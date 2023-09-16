
package dominio.dao;

import dominio.bbdd.Conexion;
import dominio.usuario.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements all the methods from the UsuarioDAO. Add, delete or update users and their components. 
 * @author Toni
 */
public class UsuarioDAOImpl implements UsuarioDAO {
    
    //Select states statement
    private static final String SQL_SELECT_ESTADO = "SELECT ID_Articulo, ID_Usuario, Estado FROM ColaboradorEstado";
    //Insert state statement
    private static final String SQL_INSERT_ESTADO = "INSERT INTO ColaboradorEstado (ID_Articulo, ID_Usuario, Estado) VALUES (?,?,?)";
    //Delete state statement
    private final String SQL_DELETE_ESTADO = "DELETE FROM ColaboradorEstado WHERE (ID_Articulo_ID_Usuario) = (?,?)";
    //Update state statement
    private final String SQL_UPDATE_ESTADO = "UPDATE ColaboradorEstado SET Estado =? WHERE (ID_Articulo, ID_Usuario) = (?,?)";

    
    //Insert user statement
    private final String SQL_INSERT = "INSERT INTO Usuario (ID_Usuario, Nombre, Apellido, ApellidoDos, Direccion, Telefono, Tipo) VALUES(?,?,?,?,?,?,?)";
    //Insert user access statement
    private final String SQL_INSERT_ACCESO = "INSERT INTO Acceso (ID_Usuario, NombreUsuario, Password) VALUES(?,?,?)";
    //Select users statement
    private final String SQL_SELECT = "SELECT ID_Usuario, Nombre, Apellido, ApellidoDos, Direccion, Telefono, Tipo, Solicitud FROM Usuario";
    //Select user by Id statement
    private final String SQL_SELECT_BY_ID = "SELECT ID_Usuario, Nombre, Apellido, ApellidoDos, Direccion, Telefono, Tipo, Solicitud FROM Usuario WHERE ID_Usuario = ?";
    //Select user access by Id
    private final String SQL_SELECT_ACCESO_ID = "SELECT ID_Usuario, NombreUsuario, Password FROM Acceso WHERE ID_Usuario = ?";
    //Update user statement
    private final String SQL_UPDATE = "UPDATE Usuario SET Nombre =?, Apellido=?, ApellidoDos=?, Direccion=?, Telefono=?, Tipo=?, Solicitud=? WHERE ID_Usuario = ?";
    //Update acceso statement
    private final String SQL_UPDATE_ACCESO = "UPDATE Acceso SET NombreUsuario=?, Password =? WHERE ID_Usuario = ?";
    //Delete user statement
    private final String SQL_DELETE = "DELETE FROM Usuario WHERE ID_Usuario = ?";
    //Delete acceso statement
    private final String SQL_DELETE_ACCESO = "DELETE FROM Acceso WHERE ID_Usuario = ?";
    
    
    /**
     * Finds all system users and returns them
     * @return: user List 
     */
    @Override
    public List<Usuario> findAllUsers() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = null;
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int idUsuario = rs.getInt("ID_Usuario");
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                String apellidoDos = rs.getString("ApellidoDos");
                String direccion = rs.getString("Direccion");
                String telefono = rs.getString("Telefono");
                String tipo = rs.getString("Tipo");
                String solicitud = rs.getString("Solicitud");
                switch (tipo) {                 
                    case "Coordinador":
                        usuario = new Coordinador(nombre, apellido, apellidoDos, direccion, telefono, idUsuario);
                        break;
                    case "Colaborador":
                        usuario = new Colaborador(nombre, apellido, apellidoDos, direccion, telefono, idUsuario);
                        break;
                    case "Gestor":
                        usuario = new Gestor(nombre, apellido, apellidoDos, direccion, telefono, idUsuario);
                        break;
                    case "Supervisor":
                        usuario = new Supervisor(nombre, apellido, apellidoDos, direccion, telefono, idUsuario);
                        break;
                }
                usuario.setSolicitud(solicitud);
                if (usuario instanceof Colaborador) {
                    ((Colaborador) usuario).setEstado((ArrayList<Estado>) this.findUserStates(usuario));
                }
                usuarios.add(findAcceso(usuario));

            }
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }

    /**
     * Find the access user element and insert this into the user parameter
     *
     * @param usuario
     * @return
     * @throws ClassNotFoundException
     */
    private Usuario findAcceso(Usuario usuario) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Acceso acceso = null;
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_SELECT_ACCESO_ID);
            stmt.setInt(1, usuario.getId());
            rs = stmt.executeQuery();
            rs.next();

            String nombreU = rs.getString("NombreUsuario");
            String pass = rs.getString("Password");

            acceso = new Acceso(nombreU, pass);
            usuario.setAcceso(acceso);
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            e.getMessage();
        }
        return usuario;
    }
    
    /**
     * Find the user by his ID and return him
     * @param id
     * @return 
     */
    @Override
    public Usuario findUserID(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            rs.next();

            String nombre = rs.getString("Nombre");
            String apellido = rs.getString("Apellido");
            String apellidoDos = rs.getString("ApellidoDos");
            String direccion = rs.getString("Direccion");
            String telefono = rs.getString("Telefono");
            String tipo = rs.getString("Tipo");
            String solicitud = rs.getString("Solicitud");

            switch (tipo) {              
                case "Coordinador":
                    usuario = new Coordinador(nombre, apellido, apellidoDos, direccion, telefono, id);
                    break;
                case "Colaborador":
                    usuario = new Colaborador(nombre, apellido, apellidoDos, direccion, telefono, id);
                    break;
                case "Gestor":
                    usuario = new Gestor(nombre, apellido, apellidoDos, direccion, telefono, id);
                    break;
                case "Supervisor":
                    usuario = new Supervisor(nombre, apellido, apellidoDos, direccion, telefono, id);
                    break;
            }
            usuario.setSolicitud(solicitud);
        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }
    
    
    /**
     * Find the user by his name and last name and return him
     * @param nombre
     * @param apellido
     * @return 
     */
    @Override
    public Usuario findUserNameLastName(String nombre, String apellido) {
        Usuario usuario = null;
        List<Usuario> usuarios = this.findAllUsers();

        for (Usuario u : usuarios) {
            if ((u.getNombre().equals(nombre)) || (u.getApellido().equals(apellido))) {

                usuario = u;
                break;
            }
        }

        return usuario;
    }
    
    
    /**
     * Update the user into the database system
     * @param usuario 
     */
    @Override
    public void updateUser(Usuario usuario) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getApellidoDos());
            stmt.setString(4, usuario.getDireccion());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getTipo());
            stmt.setString(7, usuario.getSolicitud());
            stmt.setInt(8, usuario.getId());
            stmt.executeUpdate();
            stmt = conn.prepareStatement(SQL_UPDATE_ACCESO);
            stmt.setString(1, usuario.getAcceso().getUsuario());
            stmt.setString(2, usuario.getAcceso().getPassword());
            stmt.setInt(3, usuario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Insert the user into the database system
     * @param usuario 
     */
    @Override
    public void insertUser(Usuario usuario) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, usuario.getId());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellido());
            stmt.setString(4, usuario.getApellidoDos());
            stmt.setString(5, usuario.getDireccion());
            stmt.setString(6, usuario.getTelefono());
            stmt.setString(7, usuario.getTipo());
            stmt.executeUpdate();
            stmt = conn.prepareStatement(SQL_INSERT_ACCESO);
            stmt.setInt(1, usuario.getId());
            stmt.setString(2, usuario.getAcceso().getUsuario());
            stmt.setString(3, usuario.getAcceso().getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete the user from the database
     * @param usuario 
     */
    @Override
    public void deleteUser(Usuario usuario) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, usuario.getId());
            stmt.closeOnCompletion();
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete the user state from the database
     * @param estado
     * @param usuario 
     */
    @Override
    public void deleteEstado(Estado estado, Usuario usuario) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_DELETE_ESTADO);
            stmt.setInt(1, estado.getIdArticulo());
            stmt.setInt(2, usuario.getId());
            stmt.closeOnCompletion();
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
     /**
     * Find the Colaborador user states and return them. 
     * @param usuario
     * @return 
     */
    @Override
    public List<Estado> findUserStates(Usuario usuario) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Estado> estados = new ArrayList<>();
        Estado estado = null;
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_SELECT_ESTADO);
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (usuario.getId() == rs.getInt("ID_Usuario")) {
                    estados.add(new Estado(rs.getInt("ID_Articulo"), rs.getString("Estado")));
                }

            }
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estados;
    }
    
    
    /**
     * Insert the state into the user log
     * @param usuario
     * @param estado 
     */
    @Override
    public void insertState(Usuario usr, Estado estado) {
        try {
            Connection conn = null;
            PreparedStatement stmt = null;

            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_INSERT_ESTADO);
            stmt.setInt(1, estado.getIdArticulo());
            stmt.setInt(2, usr.getId());
            stmt.setString(3, estado.getEstado());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     * Update the user state from the database
     * @param usuario
     * @param estado 
     */
    public void updateEstado(Usuario usuario, Estado estado){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_UPDATE_ESTADO);
            stmt.setString(1, estado.getEstado());
            stmt.setInt(2, estado.getIdArticulo());
            stmt.setInt(3, usuario.getId());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}



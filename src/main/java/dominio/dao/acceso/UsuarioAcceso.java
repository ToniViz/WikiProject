
package dominio.dao.acceso;

import dominio.usuario.Estado;
import dominio.usuario.Usuario;
import java.util.List;

/**
 * This interface has the methods to convert data and save system consistency from the user table
 * @author Toni
 */
public interface UsuarioAcceso {
    
     /**
     * Find and return all users system
     * @return: all users  
     */
    public List<Usuario> findAllUsers();
    
    /**
     * Find and return an user by his Id
     * @param id
     * @return: User 
     */
    public Usuario findUserID(int id);
    
    /**
     * Find and return an user by his name and last name
     * @param nombre
     * @param apellido
     * @return: User 
     */
    public Usuario findUserNameLastName(String nombre, String apellido);
    
    /**
     * Update user from the database
     * @param usuario 
     */
    public void updateUser(Usuario usuario);
    
    /**
     * Insert user into the database
     * @param usuario 
     */
    public void insertUser(Usuario usuario);
    
    /**
     * Insert the state into the Colaborador user  
     * @param usr
     * @param estado 
     */
    public void insertEstado(Usuario usuario, Estado estado);
    
    /**
     * Find and return all user states 
     * @param usuario
     * @return: list of states
     */
    public List<Estado> findUserStates(Usuario usuario);
    
    /**
     * Delete user from the database. If this user belongs to a wiki or article, its index is deleted
     * @param usuario 
     */
    public void deleteUser(Usuario usuario);
    
    /**
     * Update the user state
     * @param usr
     * @param estado 
     */
    public void updateEstado(Usuario usuario, Estado estado);
}

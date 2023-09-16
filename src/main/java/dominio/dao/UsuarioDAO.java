
package dominio.dao;

import dominio.usuario.Estado;
import dominio.usuario.Usuario;
import java.util.List;

/**
 * This interface has the methods to save, add, update or delete the Wiki articles into the Database
 * @author Toni
 */
public interface UsuarioDAO {
    
    /**
     * Find the Colaborador user states and return them. 
     * @param usuario
     * @return 
     */
    public List<Estado> findUserStates(Usuario usuario);
    
    /**
     * Insert the state into the user log
     * @param usuario
     * @param estado 
     */
    public void insertState(Usuario usuario, Estado estado);
    
    /**
     * Finds all system users and returns them
     * @return: user List 
     */
    public List<Usuario> findAllUsers();
    
    
    /**
     * Find the user by his ID and return him
     * @param id
     * @return 
     */
    public Usuario findUserID(int id);
    
    
    /**
     * Find the user by his name and last name and return him
     * @param nombre
     * @param apellido
     * @return 
     */
    public Usuario findUserNameLastName(String nombre, String apellido);
    
    /**
     * Update the user into the database system
     * @param usuario 
     */
    public void updateUser(Usuario usuario);
    
    /**
     * Insert the user into the database system
     * @param usuario 
     */
    public void insertUser(Usuario usuario);
    
    
    /**
     * Delete the user from the database
     * @param usuario 
     */
    public void deleteUser(Usuario usuario);
    
    
    /**
     * Delete the user state from the database
     * @param estado
     * @param usuario 
     */
    public void deleteEstado(Estado estado, Usuario usuario);
    
    
    /**
     * Update the user state from the database
     * @param usuario
     * @param estado 
     */
    public void updateEstado(Usuario usuario, Estado estado);
    
}


package dominio.dao.acceso;

import dominio.articulo.Articulo;
import dominio.dao.ArticuloDAO;
import dominio.dao.ArticuloDAOImpl;
import dominio.dao.UsuarioDAO;
import dominio.dao.UsuarioDAOImpl;
import dominio.dao.WikiDAO;
import dominio.dao.WikiDAOImpl;
import dominio.usuario.Estado;
import dominio.usuario.Usuario;
import dominio.wiki.Wiki;
import java.util.ArrayList;
import java.util.List;

/**
 * The function of this class is to convert data received into useful ones for system customers and delete dependency data from the Usuario table. 
 * @author Toni
 */
public class UsuarioAccesoImpl implements UsuarioAcceso {
    
    //The field is used to access the DAO Article
    private ArticuloDAO articulo;
     //The field is used to access the DAO Wiki
    private WikiDAO wiki;
    //The field is used to access the DAO User
    private UsuarioDAO usuario;

    /**
     * UsuarioAccesoImpl constructor
     */
    public UsuarioAccesoImpl() {
        this.articulo = new ArticuloDAOImpl();
        this.usuario = new UsuarioDAOImpl();
        this.wiki = new WikiDAOImpl();
    }
    
    
    /**
     * Find and return all users system
     * @return: all users  
     */
    @Override
    public List<Usuario> findAllUsers() {
        return this.usuario.findAllUsers();
    }

    /**
     * Find and return an user by his Id
     * @param id
     * @return. User 
     */
    @Override
    public Usuario findUserID(int id) {
        return this.usuario.findUserID(id);
    }

    /**
     * Find and return an user by his name and last name
     * @param nombre
     * @param apellido
     * @return: User 
     */
    @Override
    public Usuario findUserNameLastName(String nombre, String apellido) {
        return this.usuario.findUserNameLastName(nombre, apellido);
    }

    /**
     * Update user from the database
     * @param usuario 
     */
    @Override
    public void updateUser(Usuario usuario) {
        this.usuario.updateUser(usuario);
    }

    /**
     * Insert user into the database
     * @param usuario 
     */
    @Override
    public void insertUser(Usuario usuario) {
        this.usuario.insertUser(usuario);
    }

    /**
     * Delete user from the database. If this user belongs to a wiki or article, its index is deleted
     * @param usuario 
     */
    @Override
    public void deleteUser(Usuario usuario) {
        List<Articulo> articulos = this.articulo.findAllArticles();
        List<Integer> usuarios = new ArrayList<>();
        for(Articulo art: articulos){
            usuarios = this.articulo.findUsersID(art);
            for(Integer entero: usuarios){
                if(entero==usuario.getId()){
                    this.articulo.deleteUser(art, usuario);
                }
            }
        }
        
        //Borrando de Wikis
        List<Wiki> wikis = this.wiki.findAllWiki();
        for(Wiki wik: wikis){
            usuarios = this.wiki.findUsersID(wik);
            for(Integer entero: usuarios){
                if(entero==usuario.getId()){
                    this.wiki.deleteUser(wik, usuario);
                }
            }
        }
        
        List<Estado> estados = this.usuario.findUserStates(usuario);
        for(Estado state: estados){
            this.usuario.deleteEstado(state, usuario);
        }
        
        this.usuario.deleteUser(usuario);
        
    }
    
    /**
     * Find and return all user states 
     * @param usuario
     * @return: list of states
     */
    public List<Estado> findUserStates(Usuario usuario){
        return this.usuario.findUserStates(usuario);
    }

    /**
     * Insert the state into the Colaborador user  
     * @param usr
     * @param estado 
     */
    @Override
    public void insertEstado(Usuario usr, Estado estado) {
        this.usuario.insertState(usr, estado);
    }

    /**
     * Update the user state
     * @param usr
     * @param estado 
     */
    @Override
    public void updateEstado(Usuario usr, Estado estado) {
        this.usuario.updateEstado(usr, estado);
    }
    
}

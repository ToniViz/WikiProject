
package dominio.dao.acceso;

import dominio.articulo.Articulo;
import dominio.articulo.Contenido;
import dominio.usuario.Usuario;
import dominio.wiki.Wiki;
import java.util.List;

/**
 * This interface has the methods to convert data and save system consistency from the Wiki table
 * @author Toni
 */
public interface WikiAcceso {
    
    /**
     * Find and return the Wiki list of the database
     * @return: all wikis 
     */
    public List<Wiki> findAllWiki();
    
     /**
     * Find and return the list of all Wiki articles
     * @param wiki
     * @return: the list of Wiki articles
     */
    public List<Articulo> findArticlesID(Wiki wiki);
    
    /**
     * Find and return the list of all Wiki users
     * @param wiki
     * @return: the list of Wiki users  
     */
    public List<Usuario> findUsersID(Wiki wiki);
    
    /**
     * Find and return the Wiki by its Id
     * @param id
     * @return: Wiki 
     */
    public Wiki finWikiID(int id);
    
    /**
     * Find and return the Wiki by its title
     * @param Wiki
     * @return: Wiki 
     */
    public Wiki findWikiTitle(Wiki Wiki);
    
    /**
     * Update Wiki from the database
     * @param wiki 
     */
    public void updateWiki(Wiki wiki);
    
    /**
     * Insert the Wiki into the database
     * @param wiki 
     */
    public void insertWiki(Wiki wiki);
    
     /**
     * Delete the Wiki from the database
     * @param wiki 
     */
    public void deleteWiki(Wiki wiki);
    
    /**
     * Insert Wiki user into the database
     * @param wiki
     * @param user 
     */
    public void insertUser(Wiki wiki, Usuario user);
    
    /**
     * Delete Wiki user from the database
     * @param wiki
     * @param user 
     */
    public void deleteUser(Wiki wiki, Usuario user);
    
    /**
     * Insert Wiki article into the database
     * @param wiki
     * @param articulo 
     */
    public void insertContent(Wiki wiki, Articulo articulo);
    
    /**
     * Delete Wiki article from the database
     * @param wiki
     * @param articulo 
     */
    public void deleteContent(Wiki wiki, Articulo articulo);
}


package dominio.dao;

import dominio.articulo.Articulo;
import dominio.usuario.Usuario;
import dominio.wiki.Wiki;
import java.util.List;

/**
 * This interface defines the methods for adding, deleting, and updating Wikis on the system. 
 * @author Toni
 */
public interface WikiDAO {
    
    /**
     * Find all the Wikis into the system database
     * @return 
     */
    public List<Wiki> findAllWiki();
    
    /**
     * Find and return the indexes of Wiki articles
     * @param wiki
     * @return 
     */
    public List<Integer> findArticlesID(Wiki wiki);
    
    /**
     * Find and return the indexes of Wiki users
     * @param wiki
     * @return 
     */
    public List<Integer> findUsersID(Wiki wiki);
    
    /**
     * Finf and return the Wiki by its ID
     * @param id
     * @return 
     */
    public Wiki finWikiID(int id);
    
    /**
     * Find and return the Wiki by its title
     * @param Wiki
     * @return 
     */
    public Wiki findWikiTitle(Wiki Wiki);
    
    /**
     * Update the Wiki from the system database
     * @param wiki 
     */
    public void updateWiki(Wiki wiki);
    
    /**
     * Insert the Wiki into the system database
     * @param wiki 
     */
    public void insertWiki(Wiki wiki);
    
    /**
     * Delete the Wiki from the database
     * @param wiki 
     */
    public void deleteWiki(Wiki wiki);
    
    /**
     * Insert the Wiki user into the database
     * @param wiki
     * @param user 
     */
    public void insertUser(Wiki wiki, Usuario user);
    
    /**
     * Delete the Wiki user from the database
     * @param wiki
     * @param user 
     */
    public void deleteUser(Wiki wiki, Usuario user);
    
    /**
     * Insert the Wiki article into the database
     * @param wiki
     * @param articulo 
     */
    public void insertContent(Wiki wiki, Articulo articulo);
    
    /**
     * Delete the Wiki article from the database
     * @param wiki
     * @param articulo 
     */
    public void deleteContent(Wiki wiki, Articulo articulo);
}

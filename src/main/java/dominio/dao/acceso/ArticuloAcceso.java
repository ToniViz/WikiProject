
package dominio.dao.acceso;

import dominio.articulo.Articulo;
import dominio.articulo.Contenido;
import dominio.usuario.Usuario;
import java.util.List;

/**
 * This interface has the methods to convert data and save system consistency
 * @author Toni
 */
public interface ArticuloAcceso {
    
    /**
     * Find and return all articles with its users and articles
     * @return: all articles  
     */
    public List<Articulo> findAllArticles();
    
    /**
     * Find and return all users from the article
     * @param articulo
     * @return 
     */
    public List<Usuario> findUsersID(Articulo articulo);

    /**
     * Find and return the article content
     * @param articulo
     * @return 
     */
    public List<Contenido> findContentID(Articulo articulo);
    
    /**
     * Find and return the article by its Id
     * @param id
     * @return 
     */
    public Articulo findArticleID(int id);
    
    /**
     * Find and return the article by its title
     * @param titulo
     * @return 
     */
    public Articulo findArticleTitle(String titulo);
    
    /**
     * Insert the article user into the database
     * @param articulo
     * @param usuario 
     */
    public void insertUser(Articulo articulo, Usuario usuario);
    
    /**
     * Insert the article content into the database
     * @param articulo
     * @param contenido 
     */
    public void insertContent(Articulo articulo, Contenido contenido);
    
    /**
     * Delete the article user from the database
     * @param articulo
     * @param usuario 
     */
    public void deleteUser(Articulo articulo, Usuario usuario);
    
     /**
     * Delete the article content from the database
     * @param articulo
     * @param contenido 
     */
    public void deleteContent(Articulo articulo, Contenido contenido);
    
    /**
     * Update the article from the database
     * @param articulo 
     */
    public void updateArticle(Articulo articulo);
    
    /**
     * Update the article content in the database
     * @param articulo
     * @param contenido 
     */
    public void updateContent(Articulo articulo, Contenido contenido);
    
    /**
     * Insert the article into the database
     * @param articulo 
     */
    public void insertArticle(Articulo articulo);
    
    /**
     * Delete the article from the database
     * @param articulo 
     */
    public void deleteArticle(Articulo articulo);
}

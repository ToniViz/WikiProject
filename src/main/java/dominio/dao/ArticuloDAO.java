
package dominio.dao;

import dominio.articulo.Articulo;
import dominio.articulo.Contenido;
import dominio.usuario.Usuario;
import java.util.List;

/**
 * This interface has the statements of the methods to save, add, update or delete the Wiki articles into the Database
 * @author Toni
 */
public interface ArticuloDAO {
    
    /**
     * Find the app articles and return this without their users and content
     * @return: all the articles
     */
    public List<Articulo> findAllArticles();
    
    /**
     * Find the users IDs of the article and return this
     * @param articulo
     * @return: Users IDs 
     */
    public List<Integer> findUsersID(Articulo articulo);
    
    /**
     * Find the article content and return this
     * @param articulo
     * @return: article content
     */
    public List<Contenido> findContentID(Articulo articulo);
    
    /**
     * Find the article by its ID, but this article doesn't have its users and content
     * @param id
     * @return: article
     */
    public Articulo findArticleID(int id);
    
    /**
     * Find the article by its title, but this article doesn't have its users and content
     * @param titulo
     * @return: article by its title
     */
    public Articulo findArticleTitle(String titulo);
    
    /**
     * Insert the user into the article users list
     * @param articulo
     * @param usuario 
     */
    public void insertUser(Articulo articulo, Usuario usuario);
    
    /**
     * Insert the content into the article content list
     * @param articulo
     * @param contenido 
     */
    public void insertContent(Articulo articulo, Contenido contenido);
    
    /**
     * Delete the user of the article users list
     * @param articulo
     * @param usuario 
     */
    public void deleteUser(Articulo articulo, Usuario usuario);
    
    /**
     * Delete the content of the article content list
     * @param articulo
     * @param contenido 
     */
    public void deleteContent(Articulo articulo, Contenido contenido);
    
    /**
     * Update the article fields
     * @param articulo 
     */
    public void updateArticle(Articulo articulo);
    
    /**
     * Update the article content (only this one)
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
     * Delete the article of the database
     * @param articulo 
     */
    public void deleteArticle(Articulo articulo);
    
}

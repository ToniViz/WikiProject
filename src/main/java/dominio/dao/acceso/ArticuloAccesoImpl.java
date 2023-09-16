
package dominio.dao.acceso;

import dominio.articulo.*;
import dominio.dao.ArticuloDAO;
import dominio.dao.ArticuloDAOImpl;
import dominio.dao.UsuarioDAO;
import dominio.dao.UsuarioDAOImpl;
import dominio.dao.WikiDAO;
import dominio.dao.WikiDAOImpl;
import dominio.usuario.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 * The function of this class is to convert data received into useful ones for system customers and delete dependency data from the Articulo table. 
 * @author Toni
 */
public class ArticuloAccesoImpl implements ArticuloAcceso {
    
    //The field is used to access the DAO Article
    private ArticuloDAO articulo;
    //The field is used to access the DAO Wiki
    private WikiDAO wiki;
    //The field is used to access the DAO User
    private UsuarioDAO usuario;
    
    /**
     * ArticuloAccessoImpl constructor
     */
    public ArticuloAccesoImpl() {
        this.articulo = new ArticuloDAOImpl();
        this.wiki = new WikiDAOImpl();
        this.usuario = new UsuarioDAOImpl();
        
    }
    
    
    /**
     * Find and return all articles with its users and articles
     * @return: all articles  
     */
    @Override
    public List<Articulo> findAllArticles() {
        List<Contenido> listaCont = new ArrayList<>();
        List<Usuario> usuario = new ArrayList<>();
        List<Articulo> lista = articulo.findAllArticles();
        for(Articulo art: lista){
            listaCont = this.findContentID(art);
            usuario = this.findUsersID(art);
        }
        return lista;
    }

    /**
     * Find and return all users from the article
     * @param articulo
     * @return 
     */
    @Override
    public List<Usuario> findUsersID(Articulo articulo) {
        List<Integer> lista = this.articulo.findUsersID(articulo);
        List<Usuario> usuarios = new ArrayList<>();
        for(Integer indice: lista){
            usuarios.add(usuario.findUserID(indice));
        }
        return usuarios;
    }
    
    /**
     * Find and return the article content
     * @param articulo
     * @return 
     */
    @Override
    public List<Contenido> findContentID(Articulo articulo) {
        return this.articulo.findContentID(articulo);
    }

    /**
     * Find and return the article by its Id
     * @param id
     * @return 
     */
    @Override
    public Articulo findArticleID(int id) {
        return this.articulo.findArticleID(id);
    }

    /**
     * Find and return the article by its title
     * @param titulo
     * @return 
     */
    @Override
    public Articulo findArticleTitle(String titulo) {
        return this.articulo.findArticleTitle(titulo);
    }

    /**
     * Insert the article user into the database
     * @param articulo
     * @param usuario 
     */
    @Override
    public void insertUser(Articulo articulo, Usuario usuario) {
        this.articulo.insertUser(articulo, usuario);
    }

    /**
     * Insert the article content into the database
     * @param articulo
     * @param contenido 
     */
    @Override
    public void insertContent(Articulo articulo, Contenido contenido) {
        this.articulo.insertContent(articulo, contenido);
    }

    /**
     * Delete the article user from the database
     * @param articulo
     * @param usuario 
     */
    @Override
    public void deleteUser(Articulo articulo, Usuario usuario) {
        this.articulo.deleteUser(articulo, usuario);
    }

    /**
     * Delete the article content from the database
     * @param articulo
     * @param contenido 
     */
    @Override
    public void deleteContent(Articulo articulo, Contenido contenido) {
        this.articulo.deleteContent(articulo, contenido);
    }

    /**
     * Update the article from the database
     * @param articulo 
     */
    @Override
    public void updateArticle(Articulo articulo) {
        this.articulo.updateArticle(articulo);
    }

    /**
     * Update the article content in the database
     * @param articulo
     * @param contenido 
     */
    @Override
    public void updateContent(Articulo articulo, Contenido contenido) {
        this.articulo.updateContent(articulo, contenido);
    }

    /**
     * Insert the article into the database
     * @param articulo 
     */
    @Override
    public void insertArticle(Articulo articulo) {
        this.articulo.insertArticle(articulo);
    }

    /**
     * Delete the article from the database
     * @param articulo 
     */
    @Override
    public void deleteArticle(Articulo articulo) {
        this.articulo.deleteArticle(articulo);
        List<Usuario> usuarios = this.findUsersID(articulo);
        for(Usuario user: usuarios){
            this.articulo.deleteUser(articulo, user);
        }
    }
 
}


package dominio.dao.acceso;

import dominio.articulo.Articulo;
import dominio.articulo.Contenido;
import dominio.dao.*;
import dominio.usuario.Usuario;
import dominio.wiki.Wiki;
import java.util.ArrayList;
import java.util.List;

/**
 * The function of this class is to convert data received into useful ones for system customers and delete dependency data from the Wiki table. 
 * @author Toni
 */
public class WikiAccesoImpl implements WikiAcceso {
    
     //The field is used to access the DAO Wiki
    private WikiDAO wiki;
    //The field is used to access the DAO Article
    private ArticuloDAO articulo;
    //The field is used to access the DAO User
    private UsuarioDAO usuario;
    //The field is used to access the users of an article
    private ArticuloAcceso  acceso;

    /**
     * WikiAccesoImpl constructor
     */
    public WikiAccesoImpl() {
        this.wiki = new WikiDAOImpl();
        this.articulo = new ArticuloDAOImpl();
        this.usuario = new UsuarioDAOImpl();
        this.acceso = new ArticuloAccesoImpl();
    }
    
    
    
    /**
     * Find and return the Wiki list of the database
     * @return: all wikis 
     */
    @Override
    public List<Wiki> findAllWiki() {
        List<Wiki> wikis = this.wiki.findAllWiki();
        List<Articulo> art = new ArrayList<>();
        List<Usuario> user = new ArrayList<>();
        for(Wiki wik: wikis){
           
            art = this.findArticlesID(wik);
            user = this.findUsersID(wik);
            wik.setArticulos((ArrayList<Articulo>) art);
            wik.setUsuarios((ArrayList<Usuario>) user);
        }
        return wikis;
    }

    /**
     * Find and return the list of all Wiki articles
     * @param wiki
     * @return: the list of Wiki articles
     */
    @Override
    public List<Articulo> findArticlesID(Wiki wiki) {
        List<Integer> entero = this.wiki.findArticlesID(wiki);
        List<Articulo> articulos = new ArrayList<>();
        for(Integer indice: entero){
            articulos.add(this.articulo.findArticleID(indice));
        }
        
        return articulos;
        
    }

    /**
     * Find and return the list of all Wiki users
     * @param wiki
     * @return: the list of Wiki users  
     */
    @Override
    public List<Usuario> findUsersID(Wiki wiki) {
        List<Integer> entero = this.wiki.findUsersID(wiki);
        List<Usuario> usuarios = new ArrayList<>();
        for(Integer indice: entero){
            usuarios.add(this.usuario.findUserID(indice));
        }
        return usuarios;
    }

    /**
     * Find and return the Wiki by its Id
     * @param id
     * @return: Wiki 
     */
    @Override
    public Wiki finWikiID(int id) {
        return this.wiki.finWikiID(id);
       
    }

    /**
     * Find and return the Wiki by its title
     * @param Wiki
     * @return: Wiki 
     */
    @Override
    public Wiki findWikiTitle(Wiki Wiki) {
        return this.wiki.findWikiTitle(Wiki);
    }

    /**
     * Update Wiki from the database
     * @param wiki 
     */
    @Override
    public void updateWiki(Wiki wiki) {
        this.wiki.updateWiki(wiki);
    }

    /**
     * Insert the Wiki into the database
     * @param wiki 
     */
    @Override
    public void insertWiki(Wiki wiki) {
        this.wiki.insertWiki(wiki);
    }

    /**
     * Delete the Wiki from the database
     * @param wiki 
     */
    @Override
    public void deleteWiki(Wiki wiki) {
        List<Articulo> articulos = this.findArticlesID(wiki);
        
        
        for(Articulo art: articulos){
            art.setContenido((ArrayList<Contenido>) this.articulo.findContentID(art));
            this.articulo.deleteArticle(art);
            List<Usuario> usuariosDos = this.acceso.findUsersID(art);
            for(Usuario usuarios: usuariosDos){
                this.articulo.deleteUser(art, usuarios);
            }
        }
        List<Usuario> usuarios = this.findUsersID(wiki);
        for(Usuario user: usuarios){
            this.wiki.deleteUser(wiki, user);
        }
        
        this.wiki.deleteWiki(wiki);
        
    }

    /**
     * Insert Wiki user into the database
     * @param wiki
     * @param user 
     */
    @Override
    public void insertUser(Wiki wiki, Usuario user) {
        this.wiki.insertUser(wiki, user);
    }

    /**
     * Delete Wiki user from the database
     * @param wiki
     * @param user 
     */
    @Override
    public void deleteUser(Wiki wiki, Usuario user) {
        this.wiki.deleteUser(wiki, user);
    }

    /**
     * Insert Wiki article into the database
     * @param wiki
     * @param articulo 
     */
    @Override
    public void insertContent(Wiki wiki, Articulo articulo) {
        this.wiki.insertContent(wiki, articulo);
    }

    /**
     * Delete Wiki article from the database
     * @param wiki
     * @param articulo 
     */
    @Override
    public void deleteContent(Wiki wiki, Articulo articulo) {
        this.wiki.deleteContent(wiki, articulo);
    }
    
}

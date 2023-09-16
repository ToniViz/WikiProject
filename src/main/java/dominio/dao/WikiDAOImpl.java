
package dominio.dao;

import dominio.articulo.Articulo;
import dominio.articulo.Contenido;
import dominio.bbdd.Conexion;
import dominio.usuario.Usuario;
import dominio.wiki.Wiki;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the WikiDAO interface methods. It stores the indexes to users and articles that belong to the Wiki. 
 * @author Toni
 */
public class WikiDAOImpl implements WikiDAO {
    
    //Insert Wiki statement
    private static final String SQL_INSERT_WIKI = "INSERT INTO Wiki(ID_Wiki, Titulo, Tematica) VALUES (?,?,?)";
    //Select Wikis statement
    private static final String SQL_SELECT_WIKI = "SELECT ID_Wiki, Titulo, Tematica FROM Wiki";
    //Select Wiki articles indexes statement
    private static final String SQL_SELECT_WIKIART = "SELECT ID_Wiki, ID_Articulo FROM WikiArticulo";
    //Select Wiki users statement
    private static final String SQL_SELECT_WIKIUSER = "SELECT ID_Wiki, ID_Usuario FROM UsuarioWiki";
    //Select Wiki by Id statement
    private static final String SQL_SELECT_WIKIID = "SELECT ID_Wiki, Titulo, Tematica FROM Wiki WHERE ID_Wiki=?";
    //Update Wiki statement
    private static final String SQL_UPDATE_WIKI = "UPDATE Wiki SET Titulo=?, Tematica=? WHERE ID_Wiki=?";
    //Delete Wiki statement
    private static final String SQL_DELETE_WIKI = "DELETE FROM Wiki WHERE ID_Wiki=?";
    //Insert Wiki user statement
    private static final String SQL_INSERT_WIKIUSER = "INSERT INTO UsuarioWiki(ID_Wiki, ID_Usuario) VALUES (?,?)";
    //Delete Wiki user statement
    private static final String SQL_DELETE_WIKIUSER = "DELETE FROM UsuarioWiki WHERE (ID_Wiki, ID_Usuario) = (?,?)";
    //Insert Wiki article statement
    private static final String SQL_INSERT_WIKIART = "INSERT INTO WikiArticulo(ID_Wiki, ID_Articulo) VALUES (?,?)";
    //Delete Wiki article statement
    private static final String SQL_DELETE_WIKIART = "DELETE FROM WikiArticulo WHERE (ID_Wiki, ID_Articulo) = (?,?)";
 
    /**
     * Find all the Wikis into the system database
     * @return 
     */
    @Override
    public List<Wiki> findAllWiki() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Wiki> wiki = new ArrayList<>();
        try{
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_SELECT_WIKI);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                int idWiki = rs.getInt("ID_Wiki");
                String titulo = rs.getString("Titulo");
                String tematica = rs.getString("Tematica");
                
                wiki.add(new Wiki(idWiki, titulo, tematica));
            }
        }catch(SQLException e){
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WikiDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wiki;
    }

    /**
     * Find and return the indexes of Wiki articles
     * @param wiki
     * @return 
     */
    @Override
    public List<Integer> findArticlesID(Wiki wiki) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integer> indices = new ArrayList<>();
        
        try{
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_SELECT_WIKIART);
            rs = stmt.executeQuery();
            while(rs.next()){
                if(wiki.getId()== rs.getInt("ID_Wiki")){
                    indices.add(rs.getInt("ID_Articulo"));
                }
            }
            
            
        }catch(SQLException e){
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WikiDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return indices;
    }
    
   

    /**
     * Find and return the indexes of Wiki users
     * @param wiki
     * @return 
     */
    @Override
    public List<Integer> findUsersID(Wiki wiki) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integer> indices = new ArrayList<>();
        try{
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_SELECT_WIKIUSER);
            rs = stmt.executeQuery();
            while(rs.next()){
                if(wiki.getId() == rs.getInt("ID_Wiki")){
                    indices.add(rs.getInt("ID_Usuario"));
                }
            }
        }catch(SQLException e){
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WikiDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return indices;
    }

    /**
     * Finf and return the Wiki by its ID
     * @param id
     * @return 
     */
    @Override
    public Wiki finWikiID(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Wiki wiki = null;
        try{
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_SELECT_WIKIID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.next();
            
            int wikiID = rs.getInt("ID_Wiki");
            String titulo = rs.getString("Titulo");
            String tematica = rs.getString("Tematica");
            
            wiki = new Wiki(wikiID, titulo, tematica);
        }catch(SQLException e){
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WikiDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wiki;
    }

    /**
     * Find and return the Wiki by its title
     * @param Wiki
     * @return 
     */
    @Override
    public Wiki findWikiTitle(Wiki Wiki) {
      
        List<Wiki> wiki = this.findAllWiki();
        for(Wiki wi: wiki){
            if(wi.getTitulo()==Wiki.getTitulo()){
                return wi;
            }
        }
        return null;
    }

    /**
     * Update the Wiki from the system database
     * @param wiki 
     */
    @Override
    public void updateWiki(Wiki wiki) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try{
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_UPDATE_WIKI);
            stmt.setString(1, wiki.getTitulo());
            stmt.setString(2, wiki.getTematica());
            stmt.setInt(3, wiki.getId());
            stmt.executeUpdate();
        }catch(SQLException e){
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WikiDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     /**
     * Insert the Wiki into the system database
     * @param wiki 
     */
    @Override
    public void insertWiki(Wiki wiki) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try{
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_INSERT_WIKI);
            stmt.setInt(1, wiki.getId());
            stmt.setString(2, wiki.getTitulo());
            stmt.setString(3, wiki.getTematica());
            stmt.executeUpdate();
        }catch(SQLException e){
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WikiDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete the Wiki from the database
     * @param wiki 
     */
    @Override
    public void deleteWiki(Wiki wiki) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try{
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_DELETE_WIKI);
            stmt.setInt(1, wiki.getId());
            stmt.executeUpdate();
        }catch(SQLException e){
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WikiDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     /**
     * Insert the Wiki user into the database
     * @param wiki
     * @param user 
     */
    @Override
    public void insertUser(Wiki wiki, Usuario user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_INSERT_WIKIUSER);
            stmt.setInt(1, wiki.getId());
            stmt.setInt(2, user.getId());
            stmt.executeUpdate();
        }catch(SQLException e){
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WikiDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete the Wiki user from the database
     * @param wiki
     * @param user 
     */
    @Override
    public void deleteUser(Wiki wiki, Usuario user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try{
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_DELETE_WIKIUSER);
            stmt.setInt(1, wiki.getId());
            stmt.setInt(2, user.getId());
            stmt.executeUpdate();
        }catch(SQLException e){
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WikiDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     /**
     * Insert the Wiki article into the database
     * @param wiki
     * @param articulo 
     */
    @Override
    public void insertContent(Wiki wiki, Articulo articulo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_INSERT_WIKIART);
            stmt.setInt(1, wiki.getId());
            stmt.setInt(2, articulo.getId());
            stmt.executeUpdate();
        }catch(SQLException e){
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WikiDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

     /**
     * Delete the Wiki article from the database
     * @param wiki
     * @param articulo 
     */
    @Override
    public void deleteContent(Wiki wiki, Articulo articulo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_DELETE_WIKIART);
            stmt.setInt(1, wiki.getId());
            stmt.setInt(2, articulo.getId());
            stmt.executeUpdate();
        }catch(SQLException e){
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WikiDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}

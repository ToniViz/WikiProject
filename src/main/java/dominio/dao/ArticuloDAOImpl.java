
package dominio.dao;

import dominio.articulo.Articulo;
import dominio.articulo.Contenido;
import dominio.bbdd.Conexion;
import dominio.usuario.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation from the ArticuloDAO interface: add, update and delete fields.  
 * @author Toni
 */
public class ArticuloDAOImpl implements ArticuloDAO {
    
 
    //Select article statement
    private static final String SQL_SELECT_ART = "SELECT ID_Articulo, Titulo, Descripcion, Borrador, Archivo FROM Articulo";
    //Select content statement
    private static final String SQL_SELECT_CON = "SELECT ID_Articulo, ID_Contenido, Subtitulo, Frases FROM Contenido";
    //Select article user statement
    private static final String SQL_SELECT_USUART = "SELECT ID_Articulo, ID_Usuario FROM UsuarioArticulo";  
    
    
    //Select article statement by its id 
    private static final String SQL_SELECT_ART_ID = "SELECT ID_Articulo, Titulo, Descripcion, Borrador, Archivo FROM Articulo WHERE ID_Articulo = ?";
    //Update article statement
    private static final String SQL_UPDATE_ART = "UPDATE Articulo SET Titulo=?, Descripcion=?, Borrador=?, Archivo=? WHERE ID_Articulo =?";    
    //Update content statement
    private static final String SQL_UPDATE_CON = "UPDATE Contenido SET Subtitulo=?, Frases =? WHERE (ID_Articulo, ID_Contenido) = (?,?)";
    //Insert article statement
    private static final String SQL_INSERT_ART = "INSERT INTO Articulo(ID_Articulo, Titulo, Descripcion, Borrador, Archivo) VALUES (?,?,?,?,?)";
    //Delete article statement
    private static final String SQL_DELETE_ART = "DELETE FROM Articulo WHERE ID_Articulo =?";  
    //Delete content statement
    private static final String SQL_DELETE_CONT = "DELETE FROM Contenido WHERE (ID_Articulo, ID_Contenido) = (?,?)";
    //Insert article user statement
    private static final String SQL_INSERT_USER = "INSERT INTO UsuarioArticulo (ID_Articulo, ID_Usuario) VALUES (?,?)";
    //Insert article content statement
    private static final String SQL_INSERT_CONT = "INSERT INTO Contenido (ID_Articulo, ID_Contenido, Subtitulo, Frases) VALUES (?,?,?,?)";
    //Delete article user statement
    private static final String SQL_DELETE_USER = "DELETE FROM UsuarioArticulo WHERE (ID_Articulo, ID_Usuario) = (?,?)";

    /**
     * Find the app articles and return this without their users and content
     * @return: all the articles
     */
    @Override
    public List<Articulo> findAllArticles() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ArrayList<Articulo> articulo = new ArrayList<>();
       
        
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_SELECT_ART);
            rs = stmt.executeQuery();
        
            while (rs.next()) {
                int idArticulo = rs.getInt("ID_Articulo");
                String titulo = rs.getString("Titulo");
                String descripcion = rs.getString("Descripcion");
                int borrador = rs.getInt("Borrador");
                String archivo = rs.getString("Archivo");
           
                articulo.add(new Articulo(idArticulo, titulo, descripcion, borrador, archivo));
            }

        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return articulo;
    }
    
    /**
     * Find the contents of the article and return its
     * @param id
     * @return
     * @throws ClassNotFoundException 
     */
    private ArrayList<Contenido> getContenido(int id) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Contenido> contenido = new ArrayList<>();

        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_SELECT_CON);
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (id == rs.getInt("ID_Articulo")) {
                    int identificador = rs.getInt("ID_Articulo");
                    int ideContenido = rs.getInt("ID_Contenido");
                    String subtitulo = rs.getString("Subtitulo");
                    String frase = rs.getString("Frases");
                    contenido.add(new Contenido(subtitulo, frase, ideContenido));
                }
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return contenido;
    }
    
    /**
     * Find the article content and return this
     * @param articulo
     * @return: article content
     */
    public List<Contenido> findContentID(Articulo articulo) {
        List<Contenido> cont = null;
        try {
            cont = getContenido(articulo.getId());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cont;
    }
    
     /**
     * Find the users IDs of the article and return this
     * @param articulo
     * @return: Users IDs 
     */
    @Override
    public List<Integer> findUsersID(Articulo articulo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        List<Integer> usuarios = new ArrayList<>();

        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_SELECT_USUART);
            rs = stmt.executeQuery();
            while(rs.next()){
                if(articulo.getId()==rs.getInt("ID_Articulo")){
                    usuarios.add(rs.getInt("ID_Usuario"));
                }
            }

        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuarios;
    }
     
    
    /**
     * Find the article by its ID, but this article doesn't have its users and content
     * @param id
     * @return: article
     */
    @Override
    public Articulo findArticleID(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
      
        Articulo articuloAux = null;
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_SELECT_ART_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.next();
            

            String titulo = rs.getString("Titulo");
            String descripcion = rs.getString("Descripcion");
            int borrador = rs.getInt("Borrador");
            String archivo = rs.getString("Archivo");
            
            
            articuloAux = new Articulo(id, titulo, descripcion, borrador, archivo);

        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return articuloAux;
       
    }

    /**
     * Find the article by its title, but this article doesn't have its users and content
     * @param titulo
     * @return: article by its title
     */
    @Override
    public Articulo findArticleTitle(String titulo) {
        Articulo articuloAux = null;
        List<Articulo> articulos = new ArrayList<>();
        for (Articulo articulo : articulos) {
            if (articulo.getTituloArticulo().compareTo(titulo) == 0) {
                articuloAux = articulo;
                break;
            }
        }
        return articuloAux;
    }
    
    /**
     * Update the article fields
     * @param articulo 
     */
    @Override
    public void updateArticle(Articulo articulo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_UPDATE_ART);
            stmt.setString(1, articulo.getTituloArticulo());
            stmt.setString(2, articulo.getDescripcion());
            stmt.setInt(3, articulo.isBorrador());
            stmt.setString(4, articulo.getArchivo());
            stmt.setInt(5, articulo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Update the article content (only this one)
     * @param articulo
     * @param contenido 
     */
    @Override
    public void updateContent(Articulo articulo, Contenido contenido) {
        
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_UPDATE_CON);
            stmt.setString(1, contenido.getSubtitulo());
            stmt.setString(2, contenido.getFrases());
            stmt.setInt(3, articulo.getId());
            stmt.setInt(4, contenido.getId());
            stmt.executeUpdate();
            

        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     * Insert the article into the database
     * @param articulo 
     */
    @Override
    public void insertArticle(Articulo articulo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_INSERT_ART);
            stmt.setInt(1, articulo.getId());
            stmt.setString(2, articulo.getTituloArticulo());
            stmt.setString(3, articulo.getDescripcion());
            stmt.setInt(4, articulo.isBorrador());
            stmt.setString(5, articulo.getArchivo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete the article of the database
     * @param articulo 
     */
    @Override
    public void deleteArticle(Articulo articulo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        List<Contenido> contenido = articulo.getContenido();
        try {
            conn = Conexion.getConnexion();
            for (Contenido conte : contenido) {
                stmt = conn.prepareStatement(SQL_DELETE_CONT);
                stmt.setInt(1, articulo.getId());
                stmt.setString(2, conte.getSubtitulo());
                stmt.executeUpdate();
            }
            stmt.close();
            stmt = conn.prepareStatement(SQL_DELETE_ART);
            stmt.setInt(1, articulo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Insert the user into the article users list
     * @param articulo
     * @param usuario 
     */
    @Override
    public void insertUser(Articulo artcl, Usuario usr) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
       
           
        
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_INSERT_USER);
            
            stmt.setInt(1, artcl.getId());
            stmt.setInt(2, usr.getId());
           
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    /**
     * Insert the content into the article content list
     * @param articulo
     * @param contenido 
     */
    @Override
    public void insertContent(Articulo artcl, Contenido cntnd) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_INSERT_CONT);
            stmt.setInt(1, artcl.getId());
            stmt.setInt(2, cntnd.getId());
            stmt.setString(3, cntnd.getSubtitulo());
            stmt.setString(4, cntnd.getFrases());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     /**
     * Delete the user of the article users list
     * @param articulo
     * @param usuario 
     */
    @Override
    public void deleteUser(Articulo artcl, Usuario usr) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_DELETE_USER);
            stmt.setInt(1, artcl.getId());
            stmt.setInt(2, usr.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       

    /**
     * Delete the content of the article content list
     * @param articulo
     * @param contenido 
     */
    @Override
    public void deleteContent(Articulo artcl, Contenido cntnd) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.getConnexion();
            stmt = conn.prepareStatement(SQL_DELETE_CONT);
            
            stmt.setInt(1, artcl.getId());
            stmt.setInt(2, cntnd.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArticuloDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

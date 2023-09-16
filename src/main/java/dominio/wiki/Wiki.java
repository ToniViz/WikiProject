
package dominio.wiki;

import dominio.articulo.Articulo;
import dominio.usuario.Usuario;
import java.util.ArrayList;

/**
 * The purpose of this class is to represent system Wikis
 * @author Toni
 */
public class Wiki {
    
    private static int CONTADOR = 1;
    //Wiki ID
    private int id;
    //Wiki title
    private String titulo;
    //Wiki description
    private String tematica;
    //Users who belong to the Wiki
    ArrayList<Usuario> usuarios;
    //Articles that belong to the Wiki
    ArrayList<Articulo> articulos;

    /**
     * First Wiki constructor. Used to create a Wiki for the first time on the system
     * @param titulo
     * @param tematica 
     */
    public Wiki(String titulo, String tematica) {
        this.usuarios = new ArrayList<>();
        this.articulos = new ArrayList<>();
        this.tematica = tematica;
        this.titulo = titulo;
        this.id = Wiki.CONTADOR++;
    }

    /**
     * Second Wiki constructor. Used when recovering data from an existing Wiki
     * @param id
     * @param titulo
     * @param tematica
     * @param usuarios
     * @param articulos 
     */
    public Wiki(int id, String titulo, String tematica, ArrayList<Usuario> usuarios, ArrayList<Articulo> articulos) {
        this.id = id;
        this.titulo = titulo;
        this.tematica = tematica;
        this.usuarios = usuarios;
        this.articulos = articulos;
    }

    /**
     * Third Wiki constructor. Used to get some information from a Wiki
     * @param id
     * @param titulo
     * @param tematica 
     */
    public Wiki(int id, String titulo, String tematica) {
        this.id = id;
        this.titulo = titulo;
        this.tematica = tematica;
    }
    
    /**
     * Fourth Wiki javabean constructor
     */
    public Wiki() {
    }
    

    /**
     * Return Wiki ID
     * @return : ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set Wiki ID
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    

    /**
     * Return the users list from the Wiki
     * @return: users list
     */
    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Set the user list
     * @param usuarios 
     */
    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    

    /**
     * Return the Wiki title
     * @return : Wiki title
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Set the Wiki title
     * @param titulo 
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Return the Wiki description
     * @return 
     */
    public String getTematica() {
        return tematica;
    }

    /**
     * Set the Wiki description
     * @param tematica 
     */
    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    /**
     * Return the Wiki articles
     * @return 
     */
    public ArrayList<Articulo> getArticulos() {
        return articulos;
    }

    /**
     * Set the Wiki articles
     * @param articulos 
     */
    public void setArticulos(ArrayList<Articulo> articulos) {
        this.articulos = articulos;
    }
    
    
}

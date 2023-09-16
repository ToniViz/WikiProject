
package dominio.articulo;

import dominio.usuario.Usuario;
import java.util.ArrayList;


/**
 * The article is part of the articles contained within a Wiki.  It has a list of users assigned to the management of the article and a list of content. 
 * @author Toni
 */
public class Articulo {
    
    
    private static int CONTADOR = 1;
    // It's a counter used as an ID
    private int id;
    //Title of the article
    private String tituloArticulo;
    //Description of the article
    private String descripcion;
    //Content list of the article
    private ArrayList<Contenido> contenido;
    //Users who manage the article
    private ArrayList<Usuario> usuarios;
    //Like a boolean. 0 is a draft article, 0 is not.  
    private int borrador;
    //Modified file saved as a String
    private String archivo;
    
    /**
     * Constructor with a title
     * @param tituloArticulo 
     */
    public Articulo(String tituloArticulo) {
        this.tituloArticulo = tituloArticulo;
        this.id = Articulo.CONTADOR++;
        this.usuarios = new ArrayList<>();
        this.contenido = new ArrayList<>();
        this.borrador = 0;
    }
    
    
    /**
     * Constructor with a title and description
     * @param tituloArticulo
     * @param descripcion 
     */
    public Articulo(String tituloArticulo, String descripcion) {
        this.tituloArticulo = tituloArticulo;
        this.descripcion = descripcion;
        this.usuarios = new ArrayList<>();
        this.contenido = new ArrayList<>();
        this.id = Articulo.CONTADOR++;
    }
    
    
    /**
     * Constructor with all fields
     * @param id
     * @param tituloArticulo
     * @param descripcion
     * @param contenido
     * @param borrador 
     */
    public Articulo(int id, String tituloArticulo, String descripcion, ArrayList<Contenido> contenido, int borrador) {
        this.id = id;
        this.tituloArticulo = tituloArticulo;
        this.descripcion = descripcion;
        this.contenido = contenido;
        this.usuarios = new ArrayList<>();
        this.borrador = borrador;
    }
    
    /**
     * Constructor with some article fields
     * @param id
     * @param tituloArticulo
     * @param descripcion
     * @param borrador
     * @param archivo 
     */
    public Articulo(int id, String tituloArticulo, String descripcion, int borrador, String archivo) {
        this.id = id;
        this.tituloArticulo = tituloArticulo;
        this.descripcion = descripcion;
        this.borrador = borrador;
        this.archivo = archivo;
    }
    
    /**
     * Articulo javaBean constructor
     */
    public Articulo() {
    }
    
    /**
     * Return the modified file
     * @return file as a string
     */
    public String getArchivo() {
        return archivo;
    }
    
    /**
     * Change the file
     * @param archivo 
     */
    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }
    
    /**
     * Return the ID
     * @return: ID 
     */
    public int getId() {
        return id;
    }
    
    /**
     * Change the article ID
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Return a numeric value like a boolean
     * @return: 1 is a draft; 0 is not. 
     */
    public int isBorrador() {
        return borrador;
    }
    
    /**
     * Change the file flag (boolean)
     * @param borrador 
     */
    public void setBorrador(int borrador) {
        this.borrador = borrador;
    }
    
    
    /**
     * Return the title of the article
     * @return 
     */
    public String getTituloArticulo() {
        return tituloArticulo;
    }
    
    /**
     * Set the title of the article
     * @param tituloArticulo 
     */
    public void setTituloArticulo(String tituloArticulo) {
        this.tituloArticulo = tituloArticulo;
    }
    
    /**
     * Return the description of the article
     * @return 
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Set the description of the article
     * @param descripcion 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * Return the article content: texts
     * @return 
     */
    public ArrayList<Contenido> getContenido() {
        return contenido;
    }
    
    /**
     * Set the article content
     * @param contenido 
     */
    public void setContenido(ArrayList<Contenido> contenido) {
        this.contenido = contenido;
    }
    
    /**
     * Add content into the list
     * @param contenido 
     */
    public void addContenido(Contenido contenido){
        this.contenido.add(contenido);
    }
    
    
    /**
     * Return the users list: coordinador, colaborador, supervisor
     * @return 
     */
    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
    
    
    /**
     * Change the users list
     * @param usuarios 
     */
    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    
    
}

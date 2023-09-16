
package dominio.usuario;

/**
 *  The class stores the ID of the referenced article and the status of the change request made in the article. Created when a contributor makes a change to some article.
 * @author Toni
 */
public class Estado {
    
    //Article ID
    private int idArticulo;
    //Article state
    private String estado;
    //State ID
    private int idEstado;
    
    /**
     * First Estado constructor
     * @param idArticulo
     * @param estado 
     */
    public Estado(int idArticulo, String estado) {
        this.idArticulo = idArticulo;
        this.estado = estado;
        this.idEstado = 0;
    }

    /**
     * Second Estado constructor
     * @param idArticulo
     * @param estado
     * @param idEstado 
     */
    public Estado(int idArticulo, String estado, int idEstado) {
        this.idArticulo = idArticulo;
        this.estado = estado;
        this.idEstado = idEstado;
    }
    
    
    /**
     * JavaBean Estado constructor
     */
    public Estado() {
    }

    /**
     * Return the ID State
     * @return: ID State
     */
    public int getIdEstado() {
        return idEstado;
    }

    /**
     * Set the ID State
     * @param idEstado 
     */
    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }
    
    
    /**
     * Return the article ID
     * @return: Article 
     */
    public int getIdArticulo() {
        return idArticulo;
    }

    /**
     * Set the article ID
     * @param idArticulo 
     */
    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    /**
     * Get the state
     * @return 
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Set the state
     * @param estado 
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}

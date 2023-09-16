
package dominio.articulo;


/**
 * This class is the content belonging article
 * @author Toni
 */
public class Contenido {
    
    
    //Content subtitle
    private String subtitulo;
    //Content sentence
    private String frases;
    private static int CONTADOR = 1;
    //Counter of the content (id)
    private int id;
    
    /**
     * Constructor with a subtitle and sentence
     * @param subtitulo
     * @param frases 
     */
    public Contenido(String subtitulo, String frases) {
        this.subtitulo = subtitulo;
        this.frases = frases;
        this.id = Contenido.CONTADOR++;
    }
    
    /**
     * Constructor with a title, sentence and ID
     * @param subtitulo
     * @param frases
     * @param id 
     */
    public Contenido(String subtitulo, String frases, int id) {
        this.subtitulo = subtitulo;
        this.frases = frases;
        this.id = id;
    }
    
    /**
     * JavaBean Constructor
     */
    public Contenido() {
    }
    
    /**
     * Return the content ID
     * @return: ID 
     */
    public int getId() {
        return id;
    }
    
    /**
     * Set the content ID
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the sentence os the content
     * @return: sentence 
     */
    public String getFrases() {
        return frases;
    }
    
    /**
     * Set the content sentence
     * @param frases 
     */
    public void setFrases(String frases) {
        this.frases = frases;
    }
    
    /**
     * Return the subtitle of the content
     * @return 
     */
    public String getSubtitulo() {
        return subtitulo;
    }
    
    /**
     * Set the subtitle of the article
     * @param subtitulo 
     */
    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }
    
    
}

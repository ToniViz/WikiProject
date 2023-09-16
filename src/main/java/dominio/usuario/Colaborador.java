
package dominio.usuario;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The contributor class contains a list of restrictions on access to articles, a list of the registry of article modifications and the last registered status. 
 * @author Toni
 */
public class Colaborador extends Usuario {
    
    //Type of user
    private final String tipo = "Colaborador";
    //List of article rectrictions
    private ArrayList<Integer> veto;
    //List of articles states
    private ArrayList<Estado> estado;
    //Current state
    private Estado estadoActual;
    
    /**
     * First Colaborador constructor
     * @param nombre
     * @param apellido
     * @param apellidoDos
     * @param direccion
     * @param telefono 
     */
    public Colaborador(String nombre, String apellido, String apellidoDos, String direccion, String telefono) {
        super(nombre, apellido, apellidoDos, direccion, telefono);
        this.veto = new ArrayList();
        this.estado = new ArrayList();
        this.estadoActual = null;
    }

    /**
     * Second Colaborador constructor
     * @param nombre
     * @param apellido
     * @param apellidoDos
     * @param direccion
     * @param telefono
     * @param id 
     */
    public Colaborador(String nombre, String apellido, String apellidoDos, String direccion, String telefono, int id) {
        super(nombre, apellido, apellidoDos, direccion, telefono, id);
        this.veto = new ArrayList();
        this.estado = new ArrayList();
        this.estadoActual = null;
    }

    /**
     * JavaBean Colaborador constructor
     */
    public Colaborador() {
    }
    
    /**
     * Return the current state
     * @return: current state
     */
    public Estado getEstadoActual(){
        return this.estadoActual;
    }
    
    
    /**
     * Returns the index list of constraints to articles
     * @return: restrictions list 
     */
    public ArrayList<Integer> getVeto() {
        return veto;
    }

    /**
     * Set constraints list
     * @param veto 
     */
    public void setVeto(ArrayList<Integer> veto) {
        this.veto = veto;
    }

    /**
     * Return the correct value if this user has a restriction access to the article
     * @param entero
     * @return: If it exists, it's true, otherwise it's false
     */
    public boolean isVeto(int entero){
        for(Integer indice: this.veto){
            if(entero==indice){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Add a veto by the article ID
     * @param indice 
     */
    public void addVeto(int indice){
        this.veto.add(indice);
    }
    
    /**
     * Delete a veto by the article ID
     * @param indice 
     */
    public void deleteVeto(int indice){
        Iterator<Integer> it = this.veto.iterator();
        while(it.hasNext()){
            Integer entero = it.next();
            if(entero==indice){
                it.remove();
            }
        }
    }
    
    /**
     * Return the articles states
     * @return 
     */
    public ArrayList<Estado> getEstado() {
        return estado;
    }
    
    /**
     * Set the list of states
     * @param estado 
     */
    public void setEstado(ArrayList<Estado> estado) {
        this.estado = estado;
    }
    
    /**
     * Add a state into the article and updates the current status
     * @param idArticulo
     * @param estado 
     */
    public void addEstado(int idArticulo, String estado){
        Estado estadoA = new Estado(idArticulo, estado);
        this.estado.add(estadoA);
        this.estadoActual = estadoA;
        
    }
    
    
    
    
    /**
     * Return the user type
     * @return: user type
     */
    @Override
    public String getTipo() {
        return this.tipo;
    }
    
}

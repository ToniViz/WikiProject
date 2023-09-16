
package dominio.usuario;

/**
 * This class represents the system Coordinador
 * @author Toni
 */
public class Coordinador extends Usuario {
    //Type of user
    private final String tipo = "Coordinador";
    
    /**
     * First Coordinador constructor
     * @param nombre
     * @param apellido
     * @param apellidoDos
     * @param direccion
     * @param telefono 
     */
    public Coordinador(String nombre, String apellido, String apellidoDos, String direccion, String telefono) {
        super(nombre, apellido, apellidoDos, direccion, telefono);
    }
    /**
     * Second Coordinador constructor
     * @param nombre
     * @param apellido
     * @param apellidoDos
     * @param direccion
     * @param telefono
     * @param id 
     */
    public Coordinador(String nombre, String apellido, String apellidoDos, String direccion, String telefono, int id) {
        super(nombre, apellido, apellidoDos, direccion, telefono, id);
    }
    
    

    /**
     * Return the user type
     * @return: type of user 
     */
    @Override
    public String getTipo() {
        return this.tipo;
    }
    
}

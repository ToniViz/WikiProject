
package dominio.usuario;

/**
 * This class represents the system Supervisor
 * @author Toni
 */
public class Supervisor extends Usuario {
    //Type of user
    private final String tipo = "Supervisor";
    
    /**
     * First Supervisor constructor
     * @param nombre
     * @param apellido
     * @param apellidoDos
     * @param direccion
     * @param telefono 
     */
    public Supervisor(String nombre, String apellido, String apellidoDos, String direccion, String telefono) {
        super(nombre, apellido, apellidoDos, direccion, telefono);
    }
    /**
     * Second Supervisor constructor
     * @param nombre
     * @param apellido
     * @param apellidoDos
     * @param direccion
     * @param telefono
     * @param id 
     */
    public Supervisor(String nombre, String apellido, String apellidoDos, String direccion, String telefono, int id) {
        super(nombre, apellido, apellidoDos, direccion, telefono, id);
    }
    
    
    /**
     * Return the type of user
     * @return: user's type
     */
    @Override
    public String getTipo() {
        return this.tipo;
    }
    
}

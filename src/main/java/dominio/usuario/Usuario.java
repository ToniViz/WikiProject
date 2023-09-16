
package dominio.usuario;

/**
 * This class contains the necessary fields to configure users registered in the system. 
 * @author Toni
 */
public abstract class Usuario {
    
    //User's name
    private String nombre;
    //First user's las name
    private String apellido;
    //Second user's last name
    private String apellidoDos;
    //User's address
    private String direccion;
    //User's telephone number
    private String telefono;
    //User's access
    private Acceso acceso;
    //User's role change request 
    private String solicitud;
    private static int CONTADOR = 1;
    //User's ID
    private int id;

    /**
     * The constructor creates the user for the first time to save it to the system
     * @param nombre
     * @param apellido
     * @param apellidoDos
     * @param direccion
     * @param telefono 
     */
    public Usuario(String nombre, String apellido, String apellidoDos, String direccion, String telefono) {
        this.nombre = nombre;
        this.solicitud = null;
        this.apellido = apellido;
        this.apellidoDos = apellidoDos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.acceso = new Acceso(nombre.substring(0, 2).concat(apellido), apellido);
        this.id = Usuario.CONTADOR++;
    }

    /**
     *  The constructor is used to recover the status of a user that has already been created
     * @param nombre
     * @param apellido
     * @param apellidoDos
     * @param direccion
     * @param telefono
     * @param id 
     */
    public Usuario(String nombre, String apellido, String apellidoDos, String direccion, String telefono, int id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.apellidoDos = apellidoDos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.acceso = new Acceso(nombre.substring(0, 2).concat(apellido), apellido);
        this.id = id;
    }
    
    
    /**
     * JavaBean User constructor
     */
    public Usuario() {
    }
    
    
    
    /**
     * Return the request
     * @return: request
     */
    public String getSolicitud() {
        return solicitud;
    }

    /**
     * Set the request
     * @param solicitud 
     */
    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }
  
    /**
     * Return the user name
     * @return: name 
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Set the user name
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Return the first user last name
     * @return: first last name
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Set the first user last name
     * @param apellido 
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Return the second user last name
     * @return: second last name
     */
    public String getApellidoDos() {
        return apellidoDos;
    }

    /**
     * Set the second user last name
     * @param apellidoDos 
     */
    public void setApellidoDos(String apellidoDos) {
        this.apellidoDos = apellidoDos;
    }

    /**
     * Return the user address
     * @return: user address
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Set the user address
     * @param direccion 
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Return the user's number telephone
     * @return 
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Set the user's number telephone
     * @param telefono 
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Return user's access element
     * @return 
     */
    public Acceso getAcceso() {
        return acceso;
    }

    /**
     * Set the user's access element
     * @param acceso 
     */
    public void setAcceso(Acceso acceso) {
        this.acceso = acceso;
    }

    /**
     * Return the user's id
     * @return: ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set the user's ID
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Return the type of user
     * @return: user's type
     */
    public abstract String getTipo();
    
    
    
    
    
    
}

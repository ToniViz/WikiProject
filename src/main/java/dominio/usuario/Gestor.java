/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio.usuario;

/**
 * This class represents the system Gestor
 * @author Toni
 */
public class Gestor extends Usuario {
    
    //Type of user
    private final String tipo = "Gestor";
    
    /**
     * First Gestor constructor
     * @param nombre
     * @param apellido
     * @param apellidoDos
     * @param direccion
     * @param telefono 
     */
    public Gestor(String nombre, String apellido, String apellidoDos, String direccion, String telefono) {
        super(nombre, apellido, apellidoDos, direccion, telefono);
    }

    /**
     * Second Gestor constructor
     * @param nombre
     * @param apellido
     * @param apellidoDos
     * @param direccion
     * @param telefono
     * @param id 
     */
    public Gestor(String nombre, String apellido, String apellidoDos, String direccion, String telefono, int id) {
        super(nombre, apellido, apellidoDos, direccion, telefono, id);
    }
    
    
    /**
     * Return the user's type
     * @return: type of user
     */
    @Override
    public String getTipo() {
        return this.tipo;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio.utilidades;

import dominio.dao.acceso.UsuarioAcceso;
import dominio.dao.acceso.UsuarioAccesoImpl;
import dominio.usuario.Usuario;

/**
 * This class creates different types of system users and adds them to the database
 * @author Toni
 */
public abstract class FactoryUsuario {
    //User access field
    protected UsuarioAcceso usuario;

    /**
     * FactoryUser constructor
     */
    public FactoryUsuario() {
        this.usuario = new UsuarioAccesoImpl();
    }
    
    
    
   /**
     * Create a user with parameters 
     * @param nombre
     * @param apellido
     * @param apellidoDos
     * @param direccion
     * @param telefono
     * @return 
     */
    public abstract Usuario crearUsuario(String nombre, String apellido, String apellidoDos, String direccion, String telefono);
    
    /**
     * Method for adding a new user into the system with his parameters
     * @param nombre
     * @param apellido
     * @param apellidoDos
     * @param direccion
     * @param telefono 
     */
    public void addUsuario(String nombre, String apellido, String apellidoDos, String direccion, String telefono){
        this.usuario.insertUser(this.crearUsuario(nombre, apellido, apellidoDos, direccion, telefono));
    }
    
}

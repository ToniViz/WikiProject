/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio.utilidades;

import dominio.dao.acceso.UsuarioAcceso;
import dominio.dao.acceso.UsuarioAccesoImpl;
import dominio.articulo.Articulo;
import dominio.usuario.*;



/**
 * This class is the factory colaborador user. It implements the methods of the EstadoCreator interface and extends the FactoryUser class
 * @author Toni
 */
public class FactoryColaborador extends FactoryUsuario implements EstadoCreator {
    
    
    /**
     * Create a colaborador user with parameters 
     * @param nombre
     * @param apellido
     * @param apellidoDos
     * @param direccion
     * @param telefono
     * @return 
     */
    @Override
    public Usuario crearUsuario(String nombre, String apellido, String apellidoDos, String direccion, String telefono) {
        return new Colaborador(nombre, apellido, apellidoDos, direccion, telefono);
    }
    

    /**
     * Add the user created in the system
     * @param usuario
     * @param articulo
     * @param estado 
     */
    @Override
    public void addEstado(Usuario usuario, Articulo articulo, String estado) {
        this.usuario.insertEstado(usuario, this.createEstado(articulo, estado));
    }

    /**
     * Updates the state specified as a parameter>
     * @param usuario
     * @param articulo
     * @param estado 
     */
    @Override
    public void updateEstado(Usuario usuario, Articulo articulo, String estado) {
        
        this.usuario.updateEstado(usuario, this.createEstado(articulo, estado));
    }

    /**
     * Create a colaborador state and return it
     * @param usuario
     * @param articulo
     * @param estado 
     */
    private Estado createEstado(Articulo articulo, String estado){
        return new Estado(articulo.getId(), estado);
    }

    
    
}

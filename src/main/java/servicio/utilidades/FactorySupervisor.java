/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio.utilidades;

import dominio.usuario.Supervisor;
import dominio.usuario.Usuario;

/**
 * This class is the factory coordinador class
 * @author Toni
 */
public class FactorySupervisor extends FactoryUsuario {

    
    /**
     * Create a gestor user with parameters 
     * @param nombre
     * @param apellido
     * @param apellidoDos
     * @param direccion
     * @param telefono
     * @return 
     */
    @Override
    public Usuario crearUsuario(String nombre, String apellido, String apellidoDos, String direccion, String telefono) {
        return new Supervisor(nombre, apellido, apellidoDos, direccion, telefono);
    }
    
}

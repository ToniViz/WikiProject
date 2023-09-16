/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio.utilidades;

import dominio.usuario.Coordinador;
import dominio.usuario.Usuario;

/**
 * This class is the factory coordinador class
 * @author Toni
 */
public class FactoryCoordinador extends FactoryUsuario {

    /**
     * Create a coordinador user with parameters 
     * @param nombre
     * @param apellido
     * @param apellidoDos
     * @param direccion
     * @param telefono
     * @return 
     */
    @Override
    public Usuario crearUsuario(String nombre, String apellido, String apellidoDos, String direccion, String telefono) {
        return new Coordinador(nombre, apellido, apellidoDos, direccion, telefono);
    }
    
    
    
}

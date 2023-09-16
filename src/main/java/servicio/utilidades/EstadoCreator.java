/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio.utilidades;

import dominio.articulo.Articulo;
import dominio.usuario.Usuario;

/**
 * This interface contains the methods to manipulate the colaborador user states
 * @author Toni
 */
public interface EstadoCreator {
    
    /**
     * Add a colaborador state into the article
     * @param usuario
     * @param articulo
     * @param estado 
     */
    public void addEstado(Usuario usuario, Articulo articulo, String estado);
    
    /**
     * Updates the state specified as a parameter>
     * @param usuario
     * @param articulo
     * @param estado 
     */
    public void updateEstado(Usuario usuario, Articulo articulo, String estado);
    
   
    
}

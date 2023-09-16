/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio.controlador;

import javax.servlet.http.*;

/**
 * The interface contains the list of methods that will use all controllers used
 * @author Toni
 */
public interface Servicio {
    
    /**
     * Receives doGet requests and redirects them to their corresponding methods
     * @param request
     * @param response 
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response);
    
    /**
     * Receives doPost requests and redirects them to their corresponding methods
     * @param request
     * @param response 
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio.utilidades;

import dominio.articulo.Articulo;
import dominio.wiki.Wiki;

/**
 * This interface contains methods for creating wikis and articles
 * @author Toni
 */
public interface FactoryArtWiki {
    
    /**
     * Creates and returns an article with the attributes specified in the parameters
     * @param titulo
     * @param descripcion
     * @return: Article
     */
    public Articulo crearArticulo(String titulo, String descripcion);
    
    /**
     * Creates and returns an wiki with the attributes specified in the parameters
     * @param titulo
     * @param descripcion
     * @return 
     */
    public Wiki crearWiki(String titulo, String descripcion);    
    
}

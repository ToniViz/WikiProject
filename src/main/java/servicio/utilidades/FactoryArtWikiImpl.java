/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio.utilidades;

import dominio.dao.acceso.ArticuloAcceso;
import dominio.dao.acceso.WikiAccesoImpl;
import dominio.dao.acceso.WikiAcceso;
import dominio.dao.acceso.ArticuloAccesoImpl;
import dominio.articulo.Articulo;
import dominio.wiki.Wiki;


/**
 * This class contains methods for creating wikis and articles
 * @author Toni
 */
public class FactoryArtWikiImpl implements FactoryArtWiki {
    
   
    
    
    /**
     * Creates and returns an article with the attributes specified in the parameters
     * @param titulo
     * @param descripcion
     * @return: Article
     */
    @Override
    public Articulo crearArticulo(String titulo, String descripcion) {
       return new Articulo(titulo, descripcion);
    }
    
     /**
     * Creates and returns an wiki with the attributes specified in the parameters
     * @param titulo
     * @param descripcion
     * @return 
     */
    @Override
    public Wiki crearWiki(String titulo, String descripcion) {
        return new Wiki(titulo, descripcion);
    }
    
    
    
    
}

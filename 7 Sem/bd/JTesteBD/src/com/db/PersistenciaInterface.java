/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db;

import com.objetos.Marca;
import java.util.ArrayList;

/**
 *
 * @author manchini
 */
public interface PersistenciaInterface {
    
    public void insert(Object obj);
    
    public void update(Object obj);
    
    public void delete(Object object);
        
    public void carregar(Object id);
    
    public ArrayList listarTudo(Class classe);
    
    public void conectar();
    
    public void desconectar();
    
    public Marca getMarcaAleatoria(int tam);
    
}

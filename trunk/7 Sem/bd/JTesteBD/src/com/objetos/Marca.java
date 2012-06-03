/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.objetos;

import java.io.Serializable;

/**
 *
 * @author manchini
 */
public class Marca extends  ObjetoBase implements Serializable{

    public Marca() {
    }

    
    public Marca(Integer id) {
        this.id = id;
    }
    
    
    
   
    
    private String nome;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Marca other = (Marca) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
    
    
    
}

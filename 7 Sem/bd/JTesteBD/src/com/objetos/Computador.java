/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.objetos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author manchini
 */
public class Computador extends  ObjetoBase implements Serializable{
    
    
    
    private String nome;
    
    private ArrayList<Componente> componentes = new ArrayList<Componente>();

    public Computador() {
    }

    public Computador(Integer id) {
        this.id = id;
    }

    

    public ArrayList<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(ArrayList<Componente> componentes) {
        this.componentes = componentes;
    }
    
    public void addComponente(Componente comp){
        componentes.add(comp);
    }
    public void remComponente(Componente comp){
        componentes.remove(comp);
    }
    


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
        final Computador other = (Computador) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
    
    
    
       
    
}

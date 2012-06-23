/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aula.catavento;

/**
 *
 * @author manchini
 */
public interface EventoListener {
    
    public void leuRetorno(String ret);

    public boolean tenhoQueEscrever();
    
    public String comando();
    
}

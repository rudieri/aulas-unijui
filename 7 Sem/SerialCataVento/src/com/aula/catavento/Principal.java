/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aula.catavento;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manchini
 */
public class Principal implements EventoListener{
    
    int ideal = 600;
    boolean temComando = false;
    String comando = "";
    int valor = 50;
    

    @Override
    public void leuRetorno(String ret) {
        System.out.println("Veio "+ret);
        //[618]<1001>
        //[1000]<1002>
        String[] valores = ret.split("<");
        int posicap = new Integer(valores[0].substring(1,valores[0].length()-1));
        if(posicap>ideal){
            valor=-10;
        }else if(posicap<ideal){
            valor=+10;
        }
    }

    @Override
    public boolean tenhoQueEscrever() {
        return temComando;
    }

    @Override
    public String comando() {
        System.out.println("Foir: {"+valor+"}");
        temComando = false;
        return comando;
    }
    
    
    private void loop(){
        new Serial().start(this);
        
        while(true){
            try {
                comando = "{"+valor+"}";
                temComando = true;
                
                Thread.sleep(100);
            } catch (InterruptedException ex) {
               ex.printStackTrace();
            }
        }
    }
    
    
    public static void main(String[] args) {
        new Principal().loop();
    }
    
}

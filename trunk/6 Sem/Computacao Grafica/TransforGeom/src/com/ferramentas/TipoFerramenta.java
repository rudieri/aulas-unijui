/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ferramentas;

import java.util.ArrayList;

/**
 *
 * @author rudieri
 */
public class TipoFerramenta {

    public  static final byte POLIGONO_LIVRE = 0;
    public  static final byte RETANGULO = 1;
    public  static final byte CIRCULO = 2;
    public static final byte SELECAO=3;
    private byte ferramenta;
    
    private ArrayList<FerramentaListener> listeners;

    public TipoFerramenta() {
        listeners = new ArrayList<FerramentaListener>();
        ferramenta=SELECAO;
    }
    
    public void addListener(FerramentaListener listener){
        listeners.add(listener);
    }

    
    public void set(byte ferramenta) {
        byte old=this.ferramenta;
        this.ferramenta = ferramenta;
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).ferramentaMudou(old, ferramenta);
            
        }
    }
    public byte get(){
        return ferramenta;
    }
}

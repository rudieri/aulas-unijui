/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.manchini.secureBlueFC;

import java.util.ArrayList;

/**
 *
 * @author manchini
 */
public class Senhas {
    
    private ArrayList<String> lista = new ArrayList<String>();

    public ArrayList<String> getLista() {
        if(lista.isEmpty()){
            senhaPadrao();
        }
        return lista;
    }
    
    private void senhaPadrao(){
        lista.add("1234");
        lista.add("8888");
        lista.add("0000");
        lista.add("1111");
    }     
    
    public void addSenha(String aux){
        lista.add(aux);
    }
    public void remove(String aux){
        lista.remove(aux);
    }
    
    
    
}

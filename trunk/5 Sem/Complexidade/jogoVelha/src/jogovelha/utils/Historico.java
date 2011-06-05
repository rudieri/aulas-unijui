/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.utils;

import java.util.ArrayList;
import jogovelha.marcacao.Ponto;

/**
 *
 * @author rudieri
 */
public class Historico {
    public ArrayList<Ponto> lista;
    public int resultado;
    public Historico() {
        lista = new ArrayList<Ponto>();
    }

    public Historico(ArrayList<Ponto> lista) {
        this.lista = lista;
        resultado=0;
    }

    public Historico(ArrayList<Ponto> lista, int resultado) {
        this.lista = lista;
        this.resultado = resultado;
    }
    public void addPonto(Ponto p){
        lista.add(p);
    }
    
    
}

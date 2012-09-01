/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.hci.geradorprova.cadastro;

import java.util.ArrayList;

/**
 *
 * @author rudieri
 */
public class CadastroQuestoes{
    private ArrayList<String> materias = new ArrayList<String>();
    private String selecionado;
    
    public CadastroQuestoes() {
        materias.add("Matemática");
        materias.add("Português");
    }

    
    
    public ArrayList<String> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<String> materias) {
        this.materias = materias;
    }

    public String getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(String selecionado) {
        System.out.println(selecionado);
        this.selecionado = selecionado;
    }
    
    
}

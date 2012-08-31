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
public class CadastroQuestoes {
    private ArrayList<String> materias = new ArrayList<String>();

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
    
    
}

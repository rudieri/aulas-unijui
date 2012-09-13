/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.hci.geradorprova.cadastro;

import br.hci.geradorprova.bancofake.BancoAssuntos;
import java.util.ArrayList;

/**
 *
 * @author rudieri
 */
public class CadastroAssuntos {

    private String assunto;
    private String materiaSelecionada = "Matemática";
     public ArrayList<String> listarAssuntos() {
        return BancoAssuntos.listarAssuntos(materiaSelecionada);
    }

    public void setAssuntos(ArrayList<String> assuntos) {
        for (int i = 0; i < assuntos.size(); i++) {
            String assunto = assuntos.get(i);
            BancoAssuntos.adicionarAssunto(materiaSelecionada, assunto);
        }
    }

    public String getMateriaSelecionada() {
        return materiaSelecionada;
    }

    public void setMateriaSelecionada(String materiaSelecionada) {
        System.out.println("WTF? " + materiaSelecionada);
        this.materiaSelecionada = materiaSelecionada;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }
    public void salvarAssunto(){
        BancoAssuntos.adicionarAssunto(materiaSelecionada, assunto);
        assunto = null;
        materiaSelecionada = null;
    }
    
}

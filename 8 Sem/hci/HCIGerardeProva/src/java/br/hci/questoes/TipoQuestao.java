/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.hci.questoes;

/**
 *
 * @author rudieri
 */
public enum TipoQuestao {
    DESCRITIVA("Descritiva"),
    OBJETIVA("Objetiva");
    private String nome;

    private TipoQuestao(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
    
    public static TipoQuestao getPorNome(String nome){
        for (int i = 0; i < values().length; i++) {
            if (values()[i].getNome().equals(nome)) {
                return values()[i];
            }
        }
        throw new IllegalArgumentException("Nome de constante inválido.");
    }

    @Override
    public String toString() {
        return nome;
    }
    
}

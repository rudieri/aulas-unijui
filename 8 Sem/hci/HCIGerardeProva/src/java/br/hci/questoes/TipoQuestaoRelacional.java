/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.hci.questoes;

/**
 *
 * @author rudieri
 */
public enum TipoQuestaoRelacional {
    LIGAR_PONTINHOS("Ligar os pontos"),
    RELACIONAR_LETRAS("Relacionar letras"),
    RELACIONAR_NUMEROS("Relacionar números"),
    ;
        private String nome;

    private TipoQuestaoRelacional(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }
    
      public static TipoQuestaoRelacional getPorNome(String nome){
        for (int i = 0; i < values().length; i++) {
            if (values()[i].getNome().equals(nome)) {
                return values()[i];
            }
        }
        throw new IllegalArgumentException("Nome de constante inválido.");
    }
    
        
}

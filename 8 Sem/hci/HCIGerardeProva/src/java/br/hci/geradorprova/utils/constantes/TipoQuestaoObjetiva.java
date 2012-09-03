/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.hci.geradorprova.utils.constantes;

/**
 *
 * @author rudieri
 */
public enum TipoQuestaoObjetiva {

    MULTIPLA_ESCOLHA_3("Múltipla escolha (3 opções)"),
    MULTIPLA_ESCOLHA_5("Múltipla escolha (5 opções)"),
    VERDADEIRO_FALSO("Verdadeiro ou Falso"),
    RELACIONAL("Relacional");
    private String nome;

    private TipoQuestaoObjetiva(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }

    public static TipoQuestaoObjetiva getPorNome(String nome) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].getNome().equals(nome)) {
                return values()[i];
            }
        }
        throw new IllegalArgumentException("Nome de constante inválido.");
    }
}

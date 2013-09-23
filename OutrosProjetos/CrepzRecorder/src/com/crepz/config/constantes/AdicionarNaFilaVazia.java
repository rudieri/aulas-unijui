/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crepz.config.constantes;

/**
 *
 * @author rudieri
 */
public enum AdicionarNaFilaVazia {
    REPRODUZIR_MUSICA("Tocar imediatamente a m�sica"),
    NADA("N�o fa�a nada"),
    ;
    private String nomeFake;
    

    private AdicionarNaFilaVazia(String nome) {
        this.nomeFake = nome;
    }

    public String getNomeFake() {
        return nomeFake;
    }
     public static String[] getNomesFakes() {
        AdicionarNaFilaVazia[] values = values();
        String[] nomes = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            AdicionarNaFilaVazia acoesFilaVazia = values[i];
            nomes[i] = acoesFilaVazia.nomeFake;
        }
        return nomes;
    }
}

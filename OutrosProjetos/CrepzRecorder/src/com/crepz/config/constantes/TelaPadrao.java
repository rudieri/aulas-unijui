/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crepz.config.constantes;

/**
 *
 * @author rudieri
 */
public enum TelaPadrao {
    J_PRINCIPAL("Tela Inicial"),
    J_MINI("Ícone na área de notificação"),
    J_MINI_GRAVANDO("Ícone na área de notificação (Gravando)");

    private final  String nomeFake;

    private TelaPadrao(String nomeFake) {
        this.nomeFake = nomeFake;
    }

    public static String[] getNomesFakes() {
        TelaPadrao[] values = values();
        String[] nomes = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            TelaPadrao acaoPadraoFila = values[i];
            nomes[i] = acaoPadraoFila.nomeFake;
        }
        return nomes;
    }

    public String getNomeFake() {
        return nomeFake;
    }

}

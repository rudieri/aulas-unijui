/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.marcacao;

import jogovelha.tabuleiro.Tabuleiro;

/**
 *
 * @author rudieri
 */
public class TriPonto {

    private Ponto p1;
    private Ponto p2;
    private Ponto p3;
    private Tabuleiro tabuleiro;

    public TriPonto(Ponto p1, Ponto p2, Ponto p3, Tabuleiro tabuleiro) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.tabuleiro = tabuleiro;
    }

    public Ponto getPontoLivre() {
        if (tabuleiro.estaLivre(p1)) {
            return p1;
        } else if (tabuleiro.estaLivre(p2)) {
            return p2;
        } else if (tabuleiro.estaLivre(p3)) {
            return p3;
        } else {
            return null;
        }
    }
}

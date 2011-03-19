/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.ai;

import java.util.logging.Level;
import java.util.logging.Logger;
import jogovelha.marcacao.Ponto;
import jogovelha.tabuleiro.Tabuleiro;

/**
 *
 * @author rudieri
 */
public class Jogador2 {

    private Tabuleiro tabuleiroReal;
    private static final int eu = Tabuleiro.JOGADOR_COMPUTADOR;

    public Jogador2() {
        init();
    }

    private void init() {
     
        

    }

    public void comecar() {
        new Thread(new Runnable() {

            public void run() {
                try {
                    Thread.sleep(500);
                    pense(new Ponto(0, 0));
                } catch (InterruptedException ex) {
                    Logger.getLogger(Jogador2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    public void foiMarcado(Ponto p, int jogador) {
        if (jogador == Tabuleiro.JOGADOR_COMPUTADOR) {
            return;
        }
        Ponto tp = tabuleiroReal.verificarPossivelVencedor(Tabuleiro.COMPUATADOR_VENCER);
        if (tp != null) {
            jogue(tp);
            return;
        }
        tp = tabuleiroReal.verificarPossivelVencedor(Tabuleiro.HUMANO_VENCER);
        if (tp != null) {
            jogue(tp);
            return;
        }
        //   marcar(p, Tabuleiro.JOGADOR_HUMANO);
        //  p = euPossoGanhar();

        pense(new Ponto(0, 0));

    }

    private void pense(Ponto ponto) {
        if (!tabuleiroReal.existemCasas()) {
            return;
        }
        if (tabuleiroReal.estaLivre(ponto)) {
            jogue(ponto);
        } else {
            ponto.somar(4);
            pense(ponto);
            // marcar(ponto, Tabuleiro.JOGADOR_COMPUTADOR);
        }
    }

    private void jogue(Ponto p) {
        tabuleiroReal.jogar(Tabuleiro.JOGADOR_COMPUTADOR, p.linha, p.coluna);
    }

    public void setTabuleiroReal(Tabuleiro tabuleiroReal) {
        this.tabuleiroReal = tabuleiroReal;
    }
    
}

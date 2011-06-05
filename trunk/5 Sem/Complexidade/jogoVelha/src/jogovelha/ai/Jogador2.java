/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.ai;

import java.util.ArrayList;
import jogovelha.interfaces.Jogador;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jogovelha.marcacao.Ponto;
import jogovelha.tabuleiro.Tabuleiro;

/**
 *
 * @author rudieri
 */
public class Jogador2 implements Jogador {

    private Tabuleiro tabuleiro;
    private static final byte eu = Tabuleiro.JOGADOR_COMPUTADOR;
    private final byte masqPadrao = 4;
    private final Ponto zero = new Ponto(0, 0);

    public Jogador2() {
        init();
    }

    private void init() {
    }

    @Override
    public void comecar() {
        new Thread(new Runnable() {

            public void run() {
                try {
                    Thread.sleep(500);
                    pense(new Ponto(0, 0), masqPadrao);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Jogador2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    public void minhaVez(Ponto ponto) {

        Ponto tp = tabuleiro.verificarPossivelVencedor(Tabuleiro.COMPUATADOR_VENCER);
        if (tp != null) {
            jogue(tp);
            return;
        }
        tp = tabuleiro.verificarPossivelVencedor(Tabuleiro.HUMANO_VENCER);
        if (tp != null) {
            jogue(tp);
            return;
        }
        ArrayList<Ponto> pl = tabuleiro.getPosicoesLivres();
        if (pl.size()<=2) {
            if (pl.isEmpty()) {
                return ;
            }
            pense(pl.get(0), masqPadrao);
            return ;
        }
        //   marcar(p, Tabuleiro.JOGADOR_HUMANO);
        //  p = euPossoGanhar();
        if (!ponto.isCenter() ) {
           byte dono= tabuleiro.getDonoDoMeio();
           dono=dono==eu?dono:0;
            pense(1, 1, 7+dono);
        } else {

            pense(new Ponto(0, 0), masqPadrao);
        }

    }

    private void pense(int linha, int coluna, int masq) {
        pense(new Ponto(linha, coluna), (byte) masq);
    }

    private void pense(Ponto ponto, byte masq) {
        if (!tabuleiro.existemCasas()) {
            return;
        }
        if (tabuleiro.estaLivre(ponto)) {
            jogue(ponto);
        } else {
            ponto.somar(masq);
            pense(ponto);
            // marcar(ponto, Tabuleiro.JOGADOR_COMPUTADOR);
        }
    }
    private void pense(Ponto ponto) {
       pense(ponto, masqPadrao);
    }

    private void jogue(Ponto p) {
        tabuleiro.jogar(eu, p.linha, p.coluna);
    }

    @Override
    public void setTabuleiro(Tabuleiro tabuleiroReal) {
        this.tabuleiro = tabuleiroReal;
    }

    public void gameIsOver(byte vencedor) {
        if (vencedor == eu) {
            JOptionPane.showMessageDialog(null, "MUHHUAHAHAHAHA!!!", "Computador diz...", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Se aproveitam de minha nobreza...", "Computador diz...", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void novoJogo() {
       
    }
}

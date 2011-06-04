/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.ai;

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
public class RJogador implements Jogador {

    private Tabuleiro tabuleiro;
    private static final byte eu = Tabuleiro.JOGADOR_COMPUTADOR;
    private int leuPontos = 0;

    public RJogador() {
        init();
    }

    private void init() {
    }

    @Override
    public void comecar() {
//        new Thread(new Runnable() {
//
//            public void run() {
//                try {
//                    Thread.sleep(500);
        pense(new Ponto(0, 0), new Ponto(-1, -1));
        leuPontos = 0;
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(RJogador.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }).start();
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
        //   marcar(p, Tabuleiro.JOGADOR_HUMANO);
        //  p = euPossoGanhar();

        pense(new Ponto(0, 0), new Ponto(-1, -1));
        leuPontos = 0;

    }

    public void pense(Ponto ponto, Ponto melhorPonto) {
        if (!tabuleiro.existemCasas()) {
            return;
        }
        if (leuPontos >= 9) {
            jogue(melhorPonto);
            return;
        }

        ponto.somar(1);

        //Se estiver no Meio e livre Melhor Ponto
        if (ponto.linha == ponto.getLimiteLinhas() / 2
                && ponto.coluna == ponto.getLimiteColunas() / 2
                && tabuleiro.estaLivre(ponto)) {
            melhorPonto = new Ponto(ponto.linha, ponto.coluna);
            leuPontos = 9;
        }
        /*
         * Se não for o centro e  melhor ponto nao for o centro
         * e for um canto
         */
        if (melhorPonto != null && !melhorPonto.equals(new Ponto(ponto.getLimiteLinhas() / 2, ponto.getLimiteColunas() / 2))
                && (ponto.linha == 0 || ponto.linha == ponto.getLimiteLinhas())
                && (ponto.coluna == 0 || ponto.coluna == ponto.getLimiteColunas())
              && tabuleiro.estaLivre(ponto)) {
            melhorPonto = new Ponto(ponto.linha, ponto.coluna);
        }


        /***a
         * Se o melhor ponto nao for o meio e nem um canto
         */
        if (melhorPonto != null
                && !melhorPonto.equals(new Ponto(ponto.getLimiteLinhas() / 2, ponto.getLimiteColunas() / 2))
                && !(melhorPonto.linha == 0 || melhorPonto.linha == ponto.getLimiteLinhas())
                && !(melhorPonto.coluna == 0 || melhorPonto.coluna == ponto.getLimiteColunas())
                && (ponto.linha > 0 && ponto.linha < ponto.getLimiteLinhas())
                && (ponto.coluna > 0 || ponto.coluna < ponto.getLimiteColunas())
                && tabuleiro.estaLivre(ponto)) {
            melhorPonto = new Ponto(ponto.linha, ponto.coluna);
        }
        
        leuPontos++;
        pense(ponto, melhorPonto);



    }

    private void jogue(Ponto p) {
        tabuleiro.jogar(eu, p.linha, p.coluna);
    }

    @Override
    public void setTabuleiro(Tabuleiro tabuleiroReal) {
        this.tabuleiro = tabuleiroReal;
    }

    public void gamaIsOver(byte vencedor) {
        if (vencedor == eu) {
            JOptionPane.showMessageDialog(null, "MUHHUAHAHAHAHA!!!", "Computador diz...", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Se aproveitam de minha nobreza...", "Computador diz...", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.ai;

import jogovelha.interfaces.Jogador;
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
        leuPontos = 0;
        pense(new Ponto(0, 0), new Ponto(-1, -1));
        
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

        leuPontos = 0;
        pense(new Ponto(0, 0), new Ponto(-1, -1));
        

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
//        if (ponto.linha ==1
//                && ponto.coluna == 1
//                && tabuleiro.estaLivre(ponto)) {
//            melhorPonto = new Ponto(ponto.linha, ponto.coluna);
//        }
        /*
         * Se não for o centro e  melhor ponto nao for o centro
         * e for um canto
         */
        if (melhorPonto != null && !melhorPonto.equals(new Ponto(1, 1))
                && (ponto.linha == 0 || ponto.linha == 2)
                && (ponto.coluna == 0 || ponto.coluna == 2)
                && tabuleiro.estaLivre(ponto)) {
            melhorPonto = new Ponto(ponto.linha, ponto.coluna);
        }


        /***a
         * Se o melhor ponto nao for o meio e nem um canto
         */
        if (melhorPonto != null
                && !melhorPonto.equals(new Ponto(1, 1))
                && !(melhorPonto.linha == 0 || melhorPonto.linha == 2)
                && !(melhorPonto.coluna == 0 || melhorPonto.coluna == 2)
                && (ponto.linha > 0 && ponto.linha <2)
                && (ponto.coluna > 0 || ponto.coluna < 2)
                && tabuleiro.estaLivre(ponto)) {
            melhorPonto = new Ponto(ponto.linha, ponto.coluna);
        }

        leuPontos++;
        pense(ponto, melhorPonto);



    }

    private void jogue(Ponto p) {
        tabuleiro.jogar(eu, p.linha, p.coluna);
        leuPontos = 9;
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

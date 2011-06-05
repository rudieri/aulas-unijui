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
public class Jogador2 implements Jogador {

    private Tabuleiro tabuleiro;
    private static final byte eu = Tabuleiro.JOGADOR_COMPUTADOR;

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
                    pense(new Ponto(0, 0));
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
        //   marcar(p, Tabuleiro.JOGADOR_HUMANO);
        //  p = euPossoGanhar();

        pense(new Ponto(0, 0));

    }

    private void pense(Ponto ponto) {
        if (!tabuleiro.existemCasas()) {
            return;
        }
        if (tabuleiro.estaLivre(ponto)) {
            jogue(ponto);
        } else {
            ponto.somar(4);
            pense(ponto);
            // marcar(ponto, Tabuleiro.JOGADOR_COMPUTADOR);
        }
    }

    private void jogue(Ponto p) {
        tabuleiro.jogar(eu, p.linha, p.coluna);
    }

    @Override
    public void setTabuleiro(Tabuleiro tabuleiroReal) {
        this.tabuleiro = tabuleiroReal;
    }

    public void gamaIsOver(byte vencedor) {
        if (vencedor==eu) {
            JOptionPane.showMessageDialog(null, "MUHHUAHAHAHAHA!!!","Computador diz...",JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Se aproveitam de minha nobreza...","Computador diz...",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void novoJogo() {
        
    }
    
}

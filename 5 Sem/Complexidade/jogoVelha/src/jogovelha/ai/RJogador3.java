/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.ai;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jogovelha.interfaces.Jogador;
import jogovelha.marcacao.Ponto;
import jogovelha.tabuleiro.Tabuleiro;
import jogovelha.tabuleiro.TabuleiroSimulacao;
import jogovelha.utils.Historico;

/**
 *
 * @author rudieri
 */
public class RJogador3 implements Jogador {

    private Tabuleiro tabuleiro;
//    private TabuleiroSimulacao tabuleiroTeste;
    private boolean estouSimulando;
    private static final byte eu = Tabuleiro.JOGADOR_COMPUTADOR;
    private static final byte ele = Tabuleiro.JOGADOR_HUMANO;
    private byte vez;
    private ArrayList<Historico> historicos;

    public RJogador3() {
        this(null);
    }

    public RJogador3(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
//        tabuleiroTeste = new TabuleiroSimulacao(this, this);
        historicos = new ArrayList<Historico>();
    }

    public void comecar() {
        new Thread(new Runnable() {

            public void run() {
                try {
                    Thread.sleep(500);
                    estouSimulando = true;
                    pense(new Ponto(0, 0));
                } catch (InterruptedException ex) {
                    Logger.getLogger(Jogador2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    private void pense(Ponto ponto) {
        if (estouSimulando) {
            TabuleiroSimulacao tabuleiroTeste = new TabuleiroSimulacao(this, this);
            tabuleiroTeste.setMatriz(tabuleiro.getcloneMatriz());
            vez = eu;
            Historico historico = new Historico();
            simula(new Ponto(0, 0), historico, tabuleiroTeste);
            estouSimulando = false;
            System.out.println(historicos.size());
        } else {
            for (int i = 0; i < historicos.size(); i++) {
                Historico historico = historicos.get(i);
                for (int j = 0; j < historico.lista.size(); j++) {
                    Ponto ponto1 = historico.lista.get(j);
                    System.out.println(ponto1.toString() + " :: " + historico.resultado);
                }
            }
        }
    }

    private void simula(Ponto ponto, Historico historico, TabuleiroSimulacao tabuleiroTeste) {

        for (int i = ponto.linha * 3 + ponto.coluna; i < 9; i++) {
            boolean jogou = tabuleiroTeste.jogar(vez, ponto);
            if (jogou) {
                historico.addPonto(ponto);
                vez = (byte) -vez;
                if (tabuleiroTeste.isGameOver()) {
                    if (vez == eu) {
                        historico.resultado += (eu * 3 + ponto.getValor());
                        historicos.add(historico);
                    } else {
                       // historico.resultado += (ele * 3 - ponto.getValor());
                        historicos.add(historico);
                    }
                }
                ponto.somar(1);
            }

            if (ponto.equals(new Ponto(0, 0))) {

                return;
            } else {
                ponto.somar(1);
                simula(ponto, historico, tabuleiroTeste);
            }
        }


    }

    public void minhaVez(Ponto ultimoJogada) {
        if (estouSimulando) {
            TabuleiroSimulacao tabuleiroTeste = new TabuleiroSimulacao(this, this);
            tabuleiroTeste.setMatriz(tabuleiro.getcloneMatriz());

            vez = eu;
            Historico historico = new Historico();
            simula(new Ponto(0, 0), historico, tabuleiroTeste);
            estouSimulando = false;
            System.out.println(historicos.size());
            pense(ultimoJogada);
        } else {
            System.err.println("WTF");
        }
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public void gameIsOver(byte vencedor) {
    }

    public void novoJogo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

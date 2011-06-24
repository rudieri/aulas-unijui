/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.ai;

import jogovelha.dados.Jogada;
import jogovelha.interfaces.Jogador;
import jogovelha.marcacao.Ponto;
import jogovelha.tabuleiro.Tabuleiro;
import jogovelha.tabuleiro.TabuleiroSimulacao;

/**
 *
 * @author rudieri
 */
public class LeJogadorRecursivo1 implements Jogador{

    TabuleiroSimulacao simulacao ;
    private static final byte eu=1;
    private static final byte ele=-1;
    private byte vez;

    public LeJogadorRecursivo1() {
        simulacao= new TabuleiroSimulacao(this, this);
    }
    
    public void comecar() {
        vez=-1;
        
    }

    public void minhaVez(Ponto ultimoJogada) {
        
    }
    

    public void setTabuleiro(Tabuleiro tabuleiro) {
        
    }

    public void gameIsOver(byte vencedor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void novoJogo() {
        System.err.println("Novo jogo!");
    }
    
}

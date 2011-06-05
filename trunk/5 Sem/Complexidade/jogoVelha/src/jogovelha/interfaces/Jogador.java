/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.interfaces;

import jogovelha.marcacao.Ponto;
import jogovelha.tabuleiro.Tabuleiro;

/**
 *
 * @author rudieri
 */
public interface Jogador {

    /**
     É chamado apenas quando o computador começa jogando.
     */
    
    void comecar();

    /**
     Metodo chamado quando for sua vez de jogar.
     @param ultimoJogada Ultimo ponto que foi marcado pelo jogador humano
     
     */
    void minhaVez(Ponto ultimoJogada);
    /**
     Usado para que você tenha acesso ao tabuleiro...
     @param tabuleiro Objeto tabuleiro que é criado na classe Loader
     */
    void setTabuleiro(Tabuleiro tabuleiro);
    
    void gamaIsOver(byte vencedor);
    
    void novoJogo();
}

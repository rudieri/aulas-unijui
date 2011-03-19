/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jogovelha.ai.Jogador2;
import jogovelha.tabuleiro.Tabuleiro;
import jogovelha.tela.TelaVelha;

/**
 *
 * @author rudieri
 */
public class Loader {

    private Jogador2 computador;
    private Tabuleiro tabuleiro;
    private TelaVelha telaVelha;

    public Loader() {
        try {
            telaVelha = new TelaVelha(null, true);
            computador = new Jogador2();
            tabuleiro = new Tabuleiro(telaVelha, computador);
            computador.setTabuleiroReal(tabuleiro);
            telaVelha.setTabuleiroReal(tabuleiro);
            telaVelha.setVisible(true);

        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new Loader();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jogovelha.interfaces.Jogador;
import jogovelha.tabuleiro.Tabuleiro;
import jogovelha.tela.TelaVelha;

/**
 *
 * @author rudieri
 */
public class Loader {

    private Jogador computador;
    private Tabuleiro tabuleiro;
    private TelaVelha telaVelha;
    public static final String pacotePadrao = "jogovelha.ai.";
    public static final String jogadorPadrao = "RJogador";

    public Loader() {
        try {
            telaVelha = new TelaVelha(null, true, this);
            computador = (Jogador) Class.forName(pacotePadrao + jogadorPadrao).newInstance();
            tabuleiro = new Tabuleiro(telaVelha, computador);
            computador.setTabuleiro(tabuleiro);
            telaVelha.setTabuleiroReal(tabuleiro);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(telaVelha, "Classe " + pacotePadrao + jogadorPadrao + " não encontrada.\nEscolha outro jogador nas preferencias do jogo.", "Sem AI para computador.", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (telaVelha==null) {
                telaVelha = new TelaVelha(null, true, this);
            }
            telaVelha.setVisible(true);
            System.exit(0);
        }
    }

    public void alterarJogador(Jogador computador) {
        this.computador = computador;
//        tabuleiro = new Tabuleiro(telaVelha, computador);
        tabuleiro.setComputador(computador);
        computador.setTabuleiro(tabuleiro);
    }
    public String getComputerName(){
        return computador.getClass().getName();
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        new Loader();
    }
}

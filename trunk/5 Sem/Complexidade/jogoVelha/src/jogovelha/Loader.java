/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jogovelha.ai.Jogador2;
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
    private final String jogadorPadrao="jogovelha.ai.Jogador2";

    public Loader() {
        try {
            telaVelha = new TelaVelha(null, true);
            computador = (Jogador) Class.forName(jogadorPadrao).newInstance();
            tabuleiro = new Tabuleiro(telaVelha, computador);
            computador.setTabuleiro(tabuleiro);
            telaVelha.setTabuleiroReal(tabuleiro);
            telaVelha.setVisible(true);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }catch(IllegalAccessException ex){
            
        }catch(InstantiationException ex){
            
        }
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        new Loader();
    }
}

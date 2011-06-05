/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.tabuleiro;

import jogovelha.interfaces.Jogador;
import jogovelha.marcacao.Ponto;

/**
 *
 * @author rudieri
 */
public class TabuleiroSimulacao {

    private byte[][] tabuleiro;
    private byte LINHAS;
    private byte COLUNAS;
    private byte casasRestantes;
    public static final byte JOGADOR_HUMANO = -1;
    public static final byte JOGADOR_COMPUTADOR = 1;
    public static final byte COMPUATADOR_VENCER = 2;
    public static final byte HUMANO_VENCER = -2;
    private byte vezDeJogar=JOGADOR_COMPUTADOR;
    private final Jogador j1;
    private final Jogador j2;

    public TabuleiroSimulacao(Jogador j1, Jogador j2) {
        this.j1 = j1;
        this.j2 = j2;
        inicializaMatriz();
    }

    private void inicializaMatriz() {
        tabuleiro = new byte[3][3];
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                tabuleiro[i][j] = 0;
            }
        }
        casasRestantes = 9;
    }
    public void setMatriz(byte[][] m){
        tabuleiro=m;
    }
    public boolean jogar(byte jogador,Ponto p){
        return jogar(jogador, p.linha, p.coluna);
    }

    public boolean jogar(byte jogador, byte linha, byte coluna) {
        if (!estaLivre(linha, coluna)) {
            return false;
        }
        if (casasRestantes==0) {
            return false;
        }
        if (jogador == this.vezDeJogar) {
            setValue(jogador, linha, coluna);
        }
//        if (isGameOver()) {
//            //j1.gameIsOver(vezDeJogar);
//            return true;
//        }
        this.vezDeJogar = (byte) -this.vezDeJogar;
        if (vezDeJogar == JOGADOR_COMPUTADOR) {
           // j1.minhaVez(new Ponto(linha, coluna));
        } else {
            //j2.minhaVez(new Ponto(linha, coluna));
        }
        return true;
    }

    private void setValue(byte jogador, byte linha, byte coluna) {
        casasRestantes--;
        tabuleiro[linha][coluna] = this.vezDeJogar;
    }

    public boolean estaLivre(byte linha, byte coluna) {
        return tabuleiro[linha][coluna] == 0;
    }

    public boolean estaLivre(Ponto p) {
        return estaLivre(p.linha, p.coluna);
    }

    public  boolean isGameOver() {
//        tabuleiro
//        return tabuleiro[1][1]+tabuleiro[1][2]+tabuleiro[1][3]==
        for (byte i = 0; i < tabuleiro.length; i++) {
            byte[] linha = tabuleiro[i];
            byte soma = somaLinha(linha);
            if (Math.abs(soma) == 3) {
                return true;
            }
        }
        for (byte i = 0; i < tabuleiro.length; i++) {

            byte soma = (byte) (tabuleiro[0][i] + tabuleiro[1][i] + tabuleiro[2][i]);
            if (Math.abs(soma) == 3) {
                return true;
            }
        }
        byte soma = (byte) (tabuleiro[0][0] + tabuleiro[1][1] + tabuleiro[2][2]);
        if (Math.abs(soma) == 3) {
            return true;
        }
         soma = (byte) (tabuleiro[2][0] + tabuleiro[1][1] + tabuleiro[0][2]);
        if (Math.abs(soma) == 3) {
            return true;
        }

        return false;
    }

    private byte somaLinha(byte[] linha) {
        byte saida = 0;
        for (byte b : linha) {
            saida += b;
        }
        return saida;
    }
}

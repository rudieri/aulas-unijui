/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.tabuleiro;

import java.util.logging.Level;
import java.util.logging.Logger;
import jogovelha.ai.Jogador2;
import jogovelha.marcacao.Mensageiro;
import jogovelha.marcacao.Ponto;
import jogovelha.marcacao.TriPonto;
import jogovelha.tela.TelaVelha;

/**
 *
 * @author rudieri
 */
public class Tabuleiro {

    private static final int LINHAS = 3;
    private static final int COLUNAS = 3;
    public static final int JOGADOR_HUMANO = -1;
    public static final int JOGADOR_COMPUTADOR = 1;
    public static final int COMPUATADOR_VENCER = 2;
    public static final int HUMANO_VENCER = -2;
    private int vezDeJogar;
    private int[][] tabuleiro;
    private int casasRestantes;
    private TelaVelha telaVelha;
    private Jogador2 computador;
//    private boolean 

    public Tabuleiro(TelaVelha telaVelha, Jogador2 computador) {
        init(telaVelha, computador);
    }

    private void init(TelaVelha telaVelha, Jogador2 computador) {
        this.telaVelha = telaVelha;
        this.computador = computador;

    }

    public void setComputador(Jogador2 _computador) {
        this.computador = _computador;
    }

    public void start(int quemComeca) {
        vezDeJogar = quemComeca;
        tabuleiro = new int[LINHAS][COLUNAS];
        inicializaMatriz();
        if (vezDeJogar == JOGADOR_COMPUTADOR) {
            computador.comecar();
        }
    }

    public void setTelaVelha(TelaVelha telaVelha) {
        this.telaVelha = telaVelha;
    }

    private void inicializaMatriz() {
        tabuleiro = new int[3][3];
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                tabuleiro[i][j] = 0;
            }
        }
        casasRestantes = 9;
    }

    public boolean jogar(int jogador, int linha, int coluna) {
        if (!estaLivre(linha, coluna)) {
            return false;
        }
        if (jogador == this.vezDeJogar) {
            setValue(jogador, linha, coluna);
        }
        if (isGameOver()) {
            return true;
        }
        this.vezDeJogar = -this.vezDeJogar;
        computador.foiMarcado(new Ponto((byte) linha, (byte) coluna), jogador);
        return true;
    }

    public void setValue(int jogador, int linha, int coluna) {
        casasRestantes--;
        tabuleiro[linha][coluna] = this.vezDeJogar;
        telaVelha.setValor(jogador, linha, coluna);
    }

    public boolean estaLivre(int linha, int coluna) {
        return tabuleiro[linha][coluna] == 0;
    }

    public boolean estaLivre(Ponto p) {
        return estaLivre(p.linha, p.coluna);
    }

    public Ponto verificarPossivelVencedor(int idWinJogador) {

        int linhas;
        int colunas;

        int somaDiagonalPrincipal = 0;
        int somaDiagonalSecundaria = 0;
        int somaCols = 0;
        int somaLinhas = 0;
        for (linhas = 0; linhas < LINHAS; linhas++) {
            somaCols = 0;
            somaLinhas = 0;
            somaDiagonalPrincipal += tabuleiro[linhas][linhas];
            somaDiagonalSecundaria += tabuleiro[linhas][2 - linhas];
            for (colunas = 0; colunas < COLUNAS; colunas++) {
                somaCols += tabuleiro[linhas][colunas];
                somaLinhas += tabuleiro[colunas][linhas];
            }
            if (somaCols == idWinJogador) {
                TriPonto tp = new TriPonto(new Ponto(linhas, 0), new Ponto(linhas, 1), new Ponto(linhas, 2), this);
                Ponto p = tp.getPontoLivre();
                if (p != null) {
                    return p;
                }

            }
            if (somaLinhas == idWinJogador) {
                TriPonto tp = new TriPonto(new Ponto(0, linhas), new Ponto(1, linhas), new Ponto(2, linhas), this);
                Ponto p = tp.getPontoLivre();
                if (p != null) {
                    return p;
                }

            }
            if (somaDiagonalPrincipal == idWinJogador) {
                TriPonto tp = new TriPonto(new Ponto(0, 0), new Ponto(1, 1), new Ponto(2, 2), this);
                Ponto p = tp.getPontoLivre();
                if (p != null) {
                    return p;
                }

            }
            if (somaDiagonalSecundaria == idWinJogador) {
                TriPonto tp = new TriPonto(new Ponto(0, 2), new Ponto(1, 1), new Ponto(2, 0), this);
                Ponto p = tp.getPontoLivre();
                if (p != null) {
                    return p;
                }
            }
        }
        return null;
    }

    private boolean isGameOver() {

        int linhas;
        int colunas;

        int somaDiagonalPrincipal = 0;
        int somaDiagonalSecundaria = 0;
        int somaCols = 0;
        int somaLinhas = 0;
        Mensageiro m = null;
        for (linhas = 0; linhas < LINHAS; linhas++) {
            somaCols = 0;
            somaLinhas = 0;
            somaDiagonalPrincipal += tabuleiro[linhas][linhas];
            somaDiagonalSecundaria += tabuleiro[linhas][2 - linhas];
            for (colunas = 0; colunas < COLUNAS; colunas++) {
                somaCols += tabuleiro[linhas][colunas];
                somaLinhas += tabuleiro[colunas][linhas];
            }
            if (Math.abs(somaCols) == 3) {
                m = new Mensageiro(vezDeJogar, new Ponto(linhas, 0), new Ponto(linhas, 1), new Ponto(linhas, 2));
                break;
            }
            if (Math.abs(somaLinhas) == 3) {
                m = new Mensageiro(vezDeJogar, new Ponto(0, linhas), new Ponto(1, linhas), new Ponto(2, linhas));
                break;
            }
            if (Math.abs(somaDiagonalPrincipal) == 3) {
                m = new Mensageiro(vezDeJogar, new Ponto(0, 0), new Ponto(1, 1), new Ponto(2, 2));
                break;
            }
            if (Math.abs(somaDiagonalSecundaria) == 3) {
                m = new Mensageiro(vezDeJogar, new Ponto(0, 2), new Ponto(1, 1), new Ponto(2, 0));
                break;
            }
        }
        if (m != null) {
            telaVelha.gameOver(m);
            return true;
        }

        return false;
    }

    public boolean existemCasas() {
        return casasRestantes > 0;
    }
}

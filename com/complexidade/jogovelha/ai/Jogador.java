/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.complexidade.jogovelha.ai;

import com.complexidade.jogovelha.marcacao.Ponto;
import com.complexidade.jogovelha.tela.TelaVelha;

/**
 *
 * @author rudieri
 */
public class Jogador {

    private int tabuleiro[][];
    private TelaVelha telaVelha;
    private int casasRestantes;

    public Jogador(TelaVelha t) {
        tabuleiro = new int[3][3];
        telaVelha = t;
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                tabuleiro[i][j] = 0;
            }
        }

        for (int[] vetor : tabuleiro) {
            for (int i : vetor) {
                System.out.println("i: " + i);
            }
        }
        casasRestantes = 9;

    }

    public void pense() {
        if (!existemCasas()) {
            return;
        }

        byte x = (byte) (Math.random() * 3);
        byte y = (byte) (Math.random() * 3);
        tenteJogar(new Ponto(x, y));

    }

    private void tenteJogar(Ponto p) {
        if (marcar(p, TelaVelha.JOGADOR_COMPUTADOR)) {
            telaVelha.jogar(TelaVelha.JOGADOR_COMPUTADOR, p.linha, p.coluna);
        } else {
            pense();
        }
    }

    private void jogue(Ponto p) {
        telaVelha.jogar(TelaVelha.JOGADOR_COMPUTADOR, p.linha, p.coluna);
    }

    public void inimigoMarcou(Ponto p) {
        marcar(p, TelaVelha.JOGADOR_HUMANO);
        p = euPossoGanhar();
        if (p != null) {
            jogue(p);
        } else {
            pense();
        }
    }

    private boolean marcar(Ponto p, int jogador) {
        if (tabuleiro[p.linha][p.coluna] == 0) {
            tabuleiro[p.linha][p.coluna] = jogador;
            casasRestantes--;
            return true;
        }

        return false;
    }

    private Ponto euPossoGanhar() {
        int i;
        int j = 0;
       
        for (i = 0; i < tabuleiro.length; i++) {
            int somaV = 0;
            int somaH = 0;
            Ponto pLivreV = null;
            Ponto pLivreH = null;
            for (j = 0; j < tabuleiro[i].length; j++) {
                somaV += tabuleiro[i][j];
                somaH += tabuleiro[j][i];
                if (tabuleiro[i][j] == 0) {
                    pLivreV = new Ponto(i, j);
                }
                if (tabuleiro[j][i] == 0) {
                    pLivreH = new Ponto(j, i);
                }
            }
            if (somaV == 2) {
                return pLivreV;
            }
            if (somaH == 2) {
                return pLivreH;
            }
        }


        return null;
    }

    private boolean existemCasas() {
        return casasRestantes > 0;
    }
}

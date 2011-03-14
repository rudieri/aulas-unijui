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
public class Jogador2 {

    private int tabuleiro[][];
    private TelaVelha telaVelha;
    private int casasRestantes;

    public Jogador2(TelaVelha t) {
        telaVelha = t;
        inicializaMatriz();
    }

    private void inicializaMatriz() {
        tabuleiro = new int[3][3];
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

    private boolean marcar(Ponto p, int jogador) {
        if (tabuleiro[p.linha][p.coluna] == 0) {
            tabuleiro[p.linha][p.coluna] = jogador;
            casasRestantes--;
            return true;
        }
        return false;
    }

    private boolean estaMarcado(Ponto p) {
        return tabuleiro[p.linha][p.coluna] != 0;
    }

    public void foiMarcado(Ponto p, int jogador) {
        if (jogador==TelaVelha.JOGADOR_COMPUTADOR) {
            return ;
        }
        marcar(p, TelaVelha.JOGADOR_HUMANO);
        //  p = euPossoGanhar();
     //   if (p != null) {
       //     jogue(p);
     //   } else {
            pense(new Ponto(0, 0));
       // }
    }

    private boolean existemCasas() {
        return casasRestantes > 0;
    }

    private void pense() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void pense(Ponto ponto) {
        if (!existemCasas()) {
            return ;
        }
        if (estaMarcado(ponto)) {
            ponto.somar(2);
            pense(ponto);
        } else {
            jogue(ponto);
            marcar(ponto, TelaVelha.JOGADOR_COMPUTADOR);
        }
    }

    private void jogue(Ponto p) {
        telaVelha.jogar(TelaVelha.JOGADOR_COMPUTADOR, p.linha, p.coluna);
    }
}

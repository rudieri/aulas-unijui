/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geomanalitica.utils;

import com.aula.carrinho.Sample4View;
import com.geomanalitica.utils._2d.Ponto2D;
import com.geomanalitica.utils._2d.Vetor2D;
import java.util.ArrayList;
import org.opencv.core.Mat;

/**
 *
 * @author rudieri
 */
public class Vetorizador {

    public static final int MAX_ERROS = 5;

    public static ArrayList<Ponto2D> vetorizar(boolean[][] vetor) {
        int i = 0;
        boolean[] linhaVet = vetor[i];
        if (linhaVet == null) {
            return null;
        }
        ArrayList<Ponto2D> pontos = new ArrayList<Ponto2D>(vetor.length);
        for (int j = 0; j < linhaVet.length; j++) {
            if (linhaVet[j]) {
                Ponto2D pinicial = new Ponto2D(j, i);
                Ponto2D pfinal = new Ponto2D(j, i);
                boolean chegouNoFim = vetorizar(vetor, i, j, pinicial, pfinal, pontos);
                if (!chegouNoFim) {
                    pontos.clear();
                } else {
                    return pontos;
                }
                System.out.println("Final? " + chegouNoFim);
            }
        }
        return pontos;
    }

    public static ArrayList<Ponto2D> vetorizar(Mat vetor) {
        int X = Sample4View.X_INICIAL;

//        System.out.println("Linhas: " + vetor.rows());
//        System.out.println("Colunas: " + vetor.cols());
        ArrayList<Ponto2D> pontos = new ArrayList<Ponto2D>(vetor.cols());
        for (; X < vetor.cols(); X += 25) {
            // para vetorizar com o método vetorizarAgrupado, deve-se usar Y inicial = TAMANHO grupo
            for (int Y = 0; Y < vetor.rows(); Y += 1) {
//            for (int Y = TAMANHO_GRUPO; Y < vetor.rows(); Y += TAMANHO_GRUPO) {
                if (vetor.get(Y, X)[0] > 0) {
                    Ponto2D pinicial = new Ponto2D(X, Y);
                    Ponto2D pfinal = new Ponto2D(X, Y);
                    boolean chegouNoFim = vetorizar(vetor, X, Y, pinicial, pfinal, pontos);
                    if (!chegouNoFim) {
                        pontos.clear();
                    } else {
                        return pontos;
                    }
                }
            }
        }
        return pontos;
    }

    //<editor-fold defaultstate="collapsed" desc="Não usado... a recursividade fode com o android">
    /**
     * Metodo recursivo para...
     *
     * @deprecated
     */
    private static boolean vetorizar(byte[][] vetor, int i, int j, Ponto2D pInicial, Ponto2D pFinal, ArrayList<Ponto2D> pontos, int level) {
        if (vetor[0] == null) {
            return false;
        }
        level++;
        System.out.println("Level: " + level);
        boolean chegou = false;
        final int width = vetor[0].length;
        int iAv = i + 1;
        if (iAv < vetor.length) {
            for (int k = j - 1; k < j + 2; k++) {
                if (k >= 0 && k < width) {
                    if (vetor[iAv][k] == 1) {

                        final Ponto2D novoFinal = new Ponto2D(k, iAv);

                        Vetor2D v1 = new Vetor2D(pInicial, pFinal);
                        Vetor2D v2 = new Vetor2D(pInicial, novoFinal);
                        if (!Vetor2D.isParalelo(v1, v2)) {
                            if (!pontos.contains(pFinal)) {
                                pontos.add(pFinal);
                            } else {
                                System.out.println("Ja tinha: " + pFinal);
                            }
                            pInicial = pFinal;
                        }
                        pFinal = novoFinal;
                        chegou = vetorizar(vetor, iAv, k, pInicial, pFinal, pontos, level);
                    }
                }
            }
        } else {
            pontos.add(pFinal);
            chegou = true;
        }
        return chegou;
    }
    //</editor-fold>

    @Deprecated
    private static boolean vetorizar(boolean[][] vetor, int i, int j, Ponto2D pInicial, Ponto2D pFinal, ArrayList<Ponto2D> pontos) {
        boolean chegou = false;
        int iAv = i + 1;
        boolean temPontos = true;
        byte contaErro = 0;
        while (temPontos) {
            temPontos = false;
            if (iAv < vetor.length) {
                for (int k = j - 1; k < j + 2; k++) {
                    if (k >= 0 && k < vetor[0].length) {
                        if (vetor[iAv][k]) {
                            temPontos = true;

                            final Ponto2D novoFinal = new Ponto2D(k, iAv);
                            Vetor2D v1 = new Vetor2D(pInicial, pFinal);
                            Vetor2D v2 = new Vetor2D(pInicial, novoFinal);
                            if (!Vetor2D.isParalelo(v1, v2)) {
                                if (!pontos.contains(pFinal)) {
                                    pontos.add(pFinal);
                                } else {
                                    System.out.println("Ja tinha: " + pFinal);
                                }
                                pInicial = pFinal;
                            }
                            pFinal = novoFinal;
                            j = k; // sem recursividade
//                            chegou = vetorizar(vetor, iAv, k, pInicial, pFinal, pontos, level);
                        }
                    }
                }
            } else {
                pontos.add(pFinal);
                chegou = true;
            }
            if (!temPontos && contaErro < MAX_ERROS) {
                temPontos = true;
                contaErro++;
            } else {
                contaErro = 0;
            }
            iAv++;
        }
        return chegou;
    }

    private static boolean vetorizar(Mat vetor, int xInicial, int yInicial, Ponto2D pInicial, Ponto2D pFinal, ArrayList<Ponto2D> pontos) {
        boolean chegou = false;
        int x = xInicial + 1;
        boolean temPontos = true;
        byte contaErro = 0;
        while (temPontos) {
            temPontos = false;
            if (x < vetor.cols()) {
                int erro = contaErro >> 1;// A operação x >> n equivale a divisão de x por 2^n, só que mais rápido
                for (int y = yInicial - 1 - erro; y < yInicial + 2 + erro; y++) {
                    if (y >= 0 && y < vetor.rows()) {
                        if (vetor.get(y, x)[0] > 0) {
                            contaErro = 0;
                            temPontos = true;
                            final Ponto2D novoFinal = new Ponto2D(x, y);
                            Vetor2D v1 = new Vetor2D(pInicial, pFinal);
                            Vetor2D v2 = new Vetor2D(pInicial, novoFinal);
                            if (!Vetor2D.isParalelo(v1, v2)) {
                                pontos.add(pFinal);
                                pInicial = pFinal;
                            }
                            pFinal = novoFinal;
                            yInicial = y; // sem recursividade
                        }
                    }else{
                        break;
                    }
                }
                if (!temPontos && contaErro < MAX_ERROS) {
                    temPontos = true;
                    contaErro++;
//                    System.out.println("Continuando mesmo com erros... " + contaErro);
                } else {
                    contaErro = 0;
                }
            } else {
                pontos.add(pFinal);
                chegou = true;
            }
            x++;
        }
        return chegou;
    }
    public static final int TAMANHO_GRUPO = 2;

    private static boolean vetorizarAgrupado(Mat vetor, int xInicial, int yInicial, Ponto2D pInicial, Ponto2D pFinal, ArrayList<Ponto2D> pontos) {
        if (yInicial < TAMANHO_GRUPO) {
            throw new IllegalStateException("yInicial deve ser >= ao Vetorizador.TAMNHO_GRUPO");
        }
        boolean chegou = false;
        int x = xInicial + 1;
        boolean temPontos = true;
        byte contaErro = 0;
        while (temPontos) {
            temPontos = false;
            int x2 = x + 1;
            if (x2 < vetor.cols()) {
                int erro = contaErro >> 1;// A operação x >> n equivale a divisão de x por 2^n, só que mais rápido
                int y = yInicial - erro - 1;
                int y2 = y + 1;
                if (y >= 0 && y2 < vetor.rows()) {
                    double corGrupo = vetor.get(y, x)[0] + vetor.get(y, x2)[0] + vetor.get(y2, x)[0] + vetor.get(y2, x2)[0];
                    if (corGrupo > 0) {
                        contaErro = 0;
                        temPontos = true;
                        final Ponto2D novoFinal = new Ponto2D(x, y);
                        Vetor2D v1 = new Vetor2D(pInicial, pFinal);
                        Vetor2D v2 = new Vetor2D(pInicial, novoFinal);
                        if (!Vetor2D.isParalelo(v1, v2)) {
//                                if (!pontos.contains(pFinal)) {
                            pontos.add(pFinal);
//                                } else {
//                                    System.out.println("Ja tinha: " + pFinal);
//                                }
                            pInicial = pFinal;
                        }
                        pFinal = novoFinal;
                        yInicial = y; // sem recursividade
                    }
                }
                if (!temPontos && contaErro < MAX_ERROS) {
                    temPontos = true;
                    contaErro++;
//                    System.out.println("Continuando mesmo com erros... " + contaErro);
                } else {
                    contaErro = 0;
                }
            } else {
                pontos.add(pFinal);
                chegou = true;
            }
            x++;
        }
        return chegou;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geomanalitica.utils;

import com.geomanalitica.utils._2d.Ponto2D;
import com.geomanalitica.utils._2d.Vetor2D;
import java.util.ArrayList;
import org.opencv.core.Mat;

/**
 *
 * @author rudieri
 */
public class Vetorizador {
    public static final int MAX_ERROS = 20;

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
        int i = 0;

        System.out.println("Linhas: " + vetor.rows());
        System.out.println("Colunas: " + vetor.cols());
        ArrayList<Ponto2D> pontos = new ArrayList<Ponto2D>(vetor.cols());
        for (int j = 0; j < vetor.rows(); j++) {
            if (vetor.get(j, i)[0] > 0) {
                Ponto2D pinicial = new Ponto2D(i, j);
                Ponto2D pfinal = new Ponto2D(i, j);
                boolean chegouNoFim = vetorizar(vetor, i, j, pinicial, pfinal, pontos);
                if (!chegouNoFim) {
                    pontos.clear();
                } else {
                    return pontos;
                }
            }
        }
        return pontos;
    }

    //<editor-fold defaultstate="collapsed" desc="Não usado... a recursividade fode com o android">
    /**
     * Metodo recursivo para...
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

    private static boolean vetorizar(Mat vetor, int i, int j, Ponto2D pInicial, Ponto2D pFinal, ArrayList<Ponto2D> pontos) {
        boolean chegou = false;
        int iAv = i + 1;
        boolean temPontos = true;
        byte contaErro = 0;
        while (temPontos) {
            temPontos = false;
            if (iAv < vetor.cols()) {
                for (int k = j - 1 - contaErro; k < j + 2 + contaErro; k++) {
                    if (k >= 0 && k < vetor.rows()) {
                        if (vetor.get(k, iAv)[0] > 0) {
                            contaErro = 0;
                            temPontos = true;
                            final Ponto2D novoFinal = new Ponto2D(iAv, k);
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
                            j = k; // sem recursividade
                        }
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
            iAv++;
        }
        return chegou;
    }
}

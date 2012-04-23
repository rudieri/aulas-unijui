/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geomanalitica.utils;

import com.geomanalitica.utils._2d.Ponto2D;
import com.geomanalitica.utils._2d.Vetor2D;
import java.util.ArrayList;

/**
 *
 * @author rudieri
 */
public class Vetorizador {

    public static ArrayList<Ponto2D> vetorizar(byte[][] vetor) {
        ArrayList<Ponto2D> pontos = new ArrayList<>();
        int i = 0;
        byte[] linhaVet = vetor[i];
        if (linhaVet == null) {
            return new ArrayList<>();
        }
        for (int j = 0; j < linhaVet.length; j++) {
            byte dado = linhaVet[j];
            if (dado == 1) {
                Ponto2D pinicial = new Ponto2D(j, i);
                Ponto2D pfinal = new Ponto2D(j, i);
                boolean chegouNoFim = vetorizar(vetor, i, j, pinicial, pfinal, pontos);
                if (!chegouNoFim) {
                    pontos.clear();
                }
                System.out.println("Final? " + chegouNoFim);
                if (pfinal == null) {
                    continue;
                }
            }
        }
        return pontos;
    }

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

    private static boolean vetorizar(byte[][] vetor, int i, int j, Ponto2D pInicial, Ponto2D pFinal, ArrayList<Ponto2D> pontos) {
        if (vetor[0] == null) {
            return false;
        }
        boolean chegou = false;
        final int width = vetor[0].length;
        int iAv = i + 1;
        boolean temPontos = true;
        while (temPontos) {
            temPontos = false;
            if (iAv < vetor.length) {
                for (int k = j - 1; k < j + 2; k++) {
                    if (k >= 0 && k < width) {
                        if (vetor[iAv][k] == 1) {
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
            iAv++;
        }
        return chegou;
    }
}

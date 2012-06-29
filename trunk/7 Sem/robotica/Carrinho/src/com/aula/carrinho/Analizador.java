/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aula.carrinho;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import com.geomanalitica.utils._2d.Ponto2D;
import com.geomanalitica.utils._2d.Vetor2D;
import java.util.Date;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author rudieri
 */
public class Analizador {

    private static TelaActivity tela;
    private static int contagemParaParada = 0;
    public static Vetor2D vetorCentral = new Vetor2D(new Ponto2D(160, 0), new Ponto2D(160, 240));

    public static void setTela(TelaActivity tela) {
        Analizador.tela = tela;
    }

    public static Mat analizarCanny(Mat mat, Bitmap bmp) {
//        Mat linhasVerticais = new Mat();
        Mat linhas = new Mat();
//        Mat linhasAngulares = new Mat();
//        Imgproc.HoughLinesP(mat, linhasVerticais, 2, Math.PI / 2, 5, 5);
        Date inicial = new Date();
        Imgproc.HoughLinesP(mat, linhas, 2, Math.PI / 180, 1, 60, 20);
        Log.d("DELAY", "HoughLinesP: " + (new Date().getTime() - inicial.getTime()) + " ms");
//        Imgproc.HoughLinesP(mat, linhasAngulares, 1, Math.PI / 3, 5, 5);
//        Mat tudo = new Mat(1, linhasAngulares.cols() + linhasVerticais.cols(), linhasAngulares.type());
//        for (int i = 0; i < linhasAngulares.cols(); i++) {
//            tudo.put(0, i, linhasAngulares.get(0, i));
//        }
//        for (int i = 0; i < linhasVerticais.cols(); i++) {
//            tudo.put(0, linhasAngulares.rows() + i, linhasVerticais.get(0, i));
//        }

        //        if (bmp != null) {
//            System.out.println("Rows: " + linhasAngulares.rows() + " Cols: " + linhasAngulares.cols());
//        if (linhasAngulares.rows() > 0) {
//            for (int j = 0; j < linhasAngulares.cols(); j++) {
//                double[] vetor = linhasAngulares.get(0, j);
//                Sample4View.linhaToBmp(vetor[0], vetor[1], vetor[2], vetor[3], bmp, Color.WHITE);
//            }
//        }
        inicial = new Date();
        if (linhas.rows() > 0) {
            for (int j = 0; j < linhas.cols(); j++) {
                double[] vetor = linhas.get(0, j);
                Sample4View.linhaToBmp(vetor[0], vetor[1], vetor[2], vetor[3], bmp, Color.GREEN);
            }
        }
        Log.d("DELAY", "linhaToBmp(houg): " + (new Date().getTime() - inicial.getTime()) + " ms");
//        if (linhasVerticais.rows() > 0) {
//            for (int j = 0; j < linhasVerticais.cols(); j++) {
//                double[] vetor = linhasVerticais.get(0, j);
//                Sample4View.linhaToBmp(vetor[0], vetor[1], vetor[2], vetor[3], bmp, Color.BLUE);
//            }
//        }
//        }
        return linhas;
//        return tudo;

    }

    private static double[] getMelhorLinha(Mat pontos) {
        int maior = 0;
        int maiorIndex = 0;
        for (int i = 0; i < pontos.cols(); i++) {
//            try {
            final double[] vetor = pontos.get(0, i);
            if (vetor.length < 3) {
                continue;
            }
            double tamanho = Math.abs(vetor[0] - pontos.get(0, i)[2]);
            if (tamanho > maior) {
                maiorIndex = i;
                maior = (int) tamanho;
            }
//            } catch (Exception ex) {
//                Log.e("CARRINHO", "Erro ao buscar melhor linha... i = " + i + " de " + pontos.cols() + " vetor.length= " + pontos.get(0, i).length, ex);
//
//            }
        }
        return pontos.get(0, maiorIndex);
    }

    private static double[] procurarAcima(Mat pontos, double[] start) {
        double[] original = start;
        double diferencaMinima = 400;
        for (int i = 0; i < pontos.cols(); i++) {
            double[] vetor = pontos.get(0, i);
            double dif = Math.abs(original[1] - vetor[3]);
            if (dif < diferencaMinima) {
                diferencaMinima = dif;
                original = vetor;
                i = 0;
            }
        }
        return original;
    }

    private static double[] procurarAbaixo(Mat pontos, double[] start) {
        double[] original = start;
        double diferencaMinima = 0;
        for (int i = 0; i < pontos.cols(); i++) {
            double[] vetor = pontos.get(0, i);
            double dif = Math.abs(original[1] - vetor[3]);
            if (dif > diferencaMinima) {
                diferencaMinima = dif;
                original = vetor;
                i = 0;
            }
        }
        return original;
    }

    public static void facaAlgoComCarrinho(Mat pontos, Bitmap bmp) {
        if (pontos != null && pontos.rows() > 0) {
            //            System.out.println("Pontos: " + pontos.size());
        } else {
            contagemParaParada--;
            if (contagemParaParada <= 0) {
                if (tela.isModoAutonomo()) {

                    tela.enviarPotencia(0, 0);
                    tela.setModoAutonomo(false);
                }
                contagemParaParada = 3;
            }
            return;
        }
        tela.setModoAutonomo(true);
        System.out.println("Procurando melhor linha...");
        Date inicial = new Date();
        double[] melhorLinha = getMelhorLinha(pontos);
        Log.d("DELAY", "getMelhorLinha: " + (new Date().getTime() - inicial.getTime()) + " ms");
        if (melhorLinha == null) {
            System.out.println("Melhor linha é null...");
            return;
        }
//        System.out.println("Procurando acima...");
//        double[] melhorArriba = procurarAcima(pontos, melhorLinha);
//        if (melhorArriba == null) {
//            System.out.println("Melhor arriba é null...");
//            melhorArriba = melhorLinha;
//        }
//        System.out.println("Procurando abaixo...");
//        double[] melhorAbaixo = procurarAbaixo(pontos, melhorLinha);
//        if (melhorAbaixo == null) {
//            System.out.println("Melhor abaixo é null...");
//            melhorAbaixo = melhorLinha;
//        }
        inicial = new Date();
        final double x1 = melhorLinha[0];
        final double y1 = melhorLinha[1];
        final double x2 = melhorLinha[2];
        final double y2 = melhorLinha[3];
        Sample4View.linhaToBmp(x1, y1, x2, y2, bmp, Color.RED);
//        Sample4View.linhaToBmp(melhorArriba[0], melhorArriba[1], melhorArriba[2], melhorArriba[3], bmp, Color.YELLOW);
//        Sample4View.linhaToBmp(melhorAbaixo[0], melhorAbaixo[1], melhorAbaixo[2], melhorAbaixo[3], bmp, Color.BLUE);
//        Sample4View.linhaToBmp(melhorAbaixo[0], melhorAbaixo[1], melhorArriba[2], melhorArriba[3], bmp);
        Vetor2D melhorVetor = new Vetor2D(new Ponto2D((int) x1, (int) y1), new Ponto2D((int) x2, (int) y2));
        final float anguloInterno = Vetor2D.anguloInterno(vetorCentral, melhorVetor);
        // Verificar onde está o inicio do vetor...
        double xV;
        int potenciaEsquerda = 70;
        int potenciaDireita = 70;
        if (y1 > y2) {
            xV = x1;
        } else {
            xV = x2;
        }
        if (xV < 100) {
                potenciaDireita -= 20;
        } else if (xV > 220) {
                potenciaEsquerda += 20;
        } else {
            System.out.println("Angulo: " + anguloInterno);
            float dif = anguloInterno - 90;
            if (dif > 0) {
                potenciaDireita -= dif * 1.75;
            } else {
                potenciaEsquerda += dif * 1.75;
            }
        }
//        }
        potenciaEsquerda = Math.max(0, potenciaEsquerda);
        potenciaEsquerda = Math.min(potenciaEsquerda, 70);
        potenciaDireita = Math.max(0, potenciaDireita);
        potenciaDireita = Math.min(potenciaDireita, 70);

        tela.enviarPotencia(potenciaEsquerda, potenciaDireita);

        Log.d("DELAY", "restoFaçaAlgoCom...: " + (new Date().getTime() - inicial.getTime()) + " ms");

    }
}

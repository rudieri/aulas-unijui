package com.aula.carrinho.v2;

import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.SurfaceHolder;
import com.aula.carrinho.TelaActivity;
import java.util.ArrayList;
import java.util.List;
import org.opencv.android.Utils;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public class CarrinhoV2View extends CarrinhoV2ViewBase {

    private Mat imagemCamera;
    private final TelaActivity tela;

    public CarrinhoV2View(Context context) {
        super(context);
        this.tela = (TelaActivity) context;
    }

    @Override
    public void surfaceChanged(SurfaceHolder _holder, int format, int width, int height) {
        super.surfaceChanged(_holder, format, width, height);

        synchronized (this) {
            // initialize Mats before usage
            imagemCamera = new Mat(getCameraFrameWidth(), getCameraFrameHeight(), CvType.CV_8UC1);

        }
    }

    protected Bitmap processFrame(byte[] data) {


        imagemCamera.put(0, 0, data);


        Imgproc.resize(imagemCamera, imagemCamera, new Size(getCameraFrameWidth(), getCameraFrameHeight()));
        //Monocromatico
        Imgproc.threshold(imagemCamera, imagemCamera, 116, 255d, Imgproc.THRESH_BINARY);



        ///Processa
        ////
        /*
         * Reconhecedor MATRIZ
         */

        //Numero de Divisoes Matrix
        int maxCols = 10;
        int maxRow = 10;
        int auxC = 0;//getRedFrameWidth()/maxCols;
        int auxR = 0;//getRedFrameHeight()/maxRow;
        Mat[][] matrix = new Mat[maxCols][maxRow];
        for (int c = 0; c < maxCols; c++) {
            for (int r = 0; r < maxRow; r++) {
                auxC = (c * (imagemCamera.rows() / maxCols));
                auxR = (r * (imagemCamera.cols() / maxRow));
                int fimC = (c * (imagemCamera.rows() / maxCols)) + (imagemCamera.rows() / maxCols);
                int fimR = (r * (imagemCamera.cols() / maxRow)) + (imagemCamera.cols() / maxRow);
//                Log.i(TAG, "Submat " + "[" + c + "][" + r + "] " + auxR + "," + fimR + "," + auxC + "," + fimC);
                Mat submat = imagemCamera.submat(auxC, fimC, auxR, fimR);
                matrix[c][r] = submat;


            }
        }

        int potenciaEsq = 99;
        int potenciaDir = 99;
        int ultimaLinha = maxRow - 1;
        int meio = (maxCols / 2) - 1;
        String view = "";
        int[][] pontos = new int[maxCols][maxRow];
        //Processa Matrizes
        for (int r = 0; r < maxRow; r++) {
            for (int c = 0; c < maxCols; c++) {
                Mat submat = matrix[c][r]; // Apenas Ultima Linha
//            Log.i(TAG, "Submat [" + c + "][" + r + "]");
                Bitmap aux = Bitmap.createBitmap(submat.cols(), submat.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(submat, aux);
                int totalPreto = 0;
                int totalBranco = 0;
                for (int i = 0; i < submat.cols(); i += 10) {//amostragem de 10 e 10
                    for (int j = 0; j < submat.rows(); j += 10) {
                        int pixel = aux.getPixel(i, j);
                        if (pixel == Color.BLACK) {
                            totalPreto++;
                        } else {
                            totalBranco++;
                        }
                    }
                }
                if (totalPreto > totalBranco) {
                    pontos[c][r] = Color.BLACK;
                } else {
                    pontos[c][r] = Color.WHITE;
                }
//                Log.i(TAG, "Coluna: " + c + " Branco: " + totalBranco + " Preto: " + totalPreto);


                if (r == maxRow - 1) {//Ultima Linha
                    if (c < meio && pontos[c][r] == Color.BLACK) {
                        potenciaEsq = potenciaEsq - ((meio + 1 - c) * (10));
                    } else if (c > meio && pontos[c][r] == Color.BLACK) {
                        potenciaDir = potenciaDir - ((c - meio + 1) * (10));
                    }
                }
                
                if (r == maxRow - 6) {//Bem na Frente
                    if (c < meio && pontos[c][r] == Color.BLACK) {
                        potenciaEsq = potenciaEsq - ((meio + 1 - c) * (5));
                    } else if (c > meio && pontos[c][r] == Color.BLACK) {
                        potenciaDir = potenciaDir - ((c - meio + 1) * (5));
                    }
                }

                //Monta Matriz de Preto e banco
                view += (pontos[c][r] == Color.BLACK ? "1" : "0");


            }
            view += "\r\n";
        }





        if (potenciaDir < 0) {
            potenciaDir = 0;
        }
        if (potenciaEsq < 0) {
            potenciaEsq = 0;
        }

        Log.d(TAG + " Potencia", potenciaEsq + "x" + potenciaDir + "   \n" + view);
        tela.enviarPotencia(potenciaEsq, potenciaDir);


        Bitmap bmp = Bitmap.createBitmap(getCameraFrameWidth(), getCameraFrameHeight(), Bitmap.Config.ARGB_8888);


        Utils.matToBitmap(imagemCamera, bmp);

        return bmp;

    }

    @Override
    public void run() {
        super.run();

        synchronized (this) {
            // Explicitly deallocate Mats
            if (imagemCamera != null) {
                imagemCamera.release();
            }


            imagemCamera = null;

        }
    }

    public native void FindFeatures(long matAddrGr, long matAddrRgba);

    static {
        System.loadLibrary("mixed_sample");
    }
}

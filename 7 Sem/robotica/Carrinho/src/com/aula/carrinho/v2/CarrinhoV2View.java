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
        int maxRow = 5;
        int auxC = 0;//getRedFrameWidth()/maxCols;
        int auxR = 0;//getRedFrameHeight()/maxRow;
        Mat[][] matrix = new Mat[maxCols][maxRow];
        for (int c = 0; c < maxCols; c++) {
            for (int r = 0; r < maxRow; r++) {
                auxC = (c * (imagemCamera.cols() / maxCols));
                auxR = (r * (imagemCamera.rows() / maxRow));
                int fimC = (c * (imagemCamera.cols() / maxCols)) + (imagemCamera.cols() / maxCols);
                int fimR = (r * (imagemCamera.rows() / maxRow)) + (imagemCamera.rows() / maxRow);
//                Log.i(TAG, "Submat " + "[" + c + "][" + r + "] " + auxR + "," + fimR + "," + auxC + "," + fimC);
                Mat submat = imagemCamera.submat(auxR, fimR, auxC, fimC);
                matrix[c][r] = submat;


            }
        }

        int[][] pontos = new int[maxCols][maxRow];
        //Processa Matrizes
        //Apenas Linha Ultima
        int r = maxRow - 1;
        for (int c = 0; c < maxCols; c++) {
            Mat submat = matrix[c][r]; // Apenas Ultima Linha
//            Log.i(TAG, "Submat [" + c + "][" + r + "]");
            Bitmap aux = Bitmap.createBitmap(submat.cols(), submat.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(submat, aux);
            int totalPreto = 0;
            int totalBranco = 0;
            for (int i = 0; i < submat.cols(); i++) {
                for (int j = 0; j < submat.rows(); j++) {
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
            Log.i(TAG, "Coluna: " + c + " Branco: " + totalBranco + " Preto: " + totalPreto);

        }

        

        int potenciaEsq = 99;
        int potenciaDir = 99;
        
        
        int ultimaLinha = maxRow-1;
        int meio = (maxCols/2)-1;
        boolean tudoIgual = false;
        for (int c = 0; c < maxCols; c++) {
            if(c< meio && pontos[c][ultimaLinha] == Color.BLACK){
                potenciaEsq= potenciaEsq-((meio+1-c)*(20));
            }else if (c>meio && pontos[c][ultimaLinha] == Color.BLACK){
                 potenciaDir= potenciaDir-((c-meio+1)*(20));
            }
            
        } 
        //Tudo Igual Para
        if(tudoIgual){
            potenciaDir = 0;
            potenciaEsq =0;
        }
        
        if(potenciaDir<0){
            potenciaDir = 0;
        }
        if(potenciaEsq<0){
            potenciaEsq = 0;
        }

        Log.i(TAG+" Potencia", potenciaEsq+"x"+potenciaDir);
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

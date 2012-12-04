/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aula.carrinho.v3;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import com.aula.carrinho.ParametrosActivity;
import com.aula.carrinho.TelaActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.NativeCameraView;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author rudieri
 */
public class CarrinhoV3View implements CameraBridgeViewBase.CvCameraViewListener {

    private Mat mgray;
    private Mat mRgba;
    private Integer ignorarLinhas;
    private Integer potencia;
    private int ultimaDirValido;
    private int ultimaEsqValido;
    private final TelaActivity tela;

    public CarrinhoV3View(TelaActivity telaActivity) {
        this.tela = telaActivity;
    }

    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mgray = new Mat(height, width, CvType.CV_8UC1);

        ignorarLinhas = ParametrosActivity.ignorarLinhas;
        potencia = ParametrosActivity.potencia;

    }

    public void onCameraViewStopped() {
        mRgba.release();
    }

    public Mat onCameraFrame(Mat mat) {

        mRgba = mat;
        Imgproc.cvtColor(mRgba, mgray, Imgproc.COLOR_RGBA2GRAY);

        Imgproc.threshold(mgray, mgray, 80, 255d, Imgproc.THRESH_BINARY);

        boolean temVerde = CorUtils.temVerde(mRgba);

        ///Processa
        ////
        /*
         * Reconhecedor MATRIZ
         */

        //Numero de Divisoes Matrix
        int maxCols = 9;
        int maxRow = 9;
        int auxC = 0;//getRedFrameWidth()/maxCols;
        int auxR = 0;//getRedFrameHeight()/maxRow;
        int potenciaEsq = 80;
        int potenciaDir = 80;

        int meio = (maxCols / 2);
        String view = "";
        int[][] pontos = new int[maxCols][maxRow];
        Mat[][] matrix = new Mat[maxCols][maxRow];
        //
        int columSel = -1;
        int rowSel = -1;
        //
        if (!temVerde) {

            for (int r = maxRow - 1; r != 0; r--) {
                for (int c = 0; c < maxCols; c++) {

                    if (r < ignorarLinhas) {
                        continue;
                    }
                    if (((r == 2 || r == 3 || r == 4 || r == 5) && (c == 3 || c == 4 || c == 5))
                            || ((r == 6 || r == 7) && c == 5)) {//Ignorar Meios em forma de cone
//                    Log.v(TAG + "Ignorou", c + "x" + r);
                        continue;
                    }
                    //Quebra a Imagem em Subimagens
                    auxC = (c * (mgray.rows() / maxCols));
                    auxR = (r * (mgray.cols() / maxRow));
                    int fimC = (c * (mgray.rows() / maxCols)) + (mgray.rows() / maxCols);
                    int fimR = (r * (mgray.cols() / maxRow)) + (mgray.cols() / maxRow);
//                Log.i(TAG, "Submat " + "[" + c + "][" + r + "] " + auxR + "," + fimR + "," + auxC + "," + fimC);
                    Mat submat = mgray.submat(auxC, fimC, auxR, fimR);
                    matrix[c][r] = submat;
                    // FIm 

                    //Varre a Subimagem para ver se é branco ou preto
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
                    //FIM


//                Log.i(TAG, "Coluna: " + c + " Branco: " + totalBranco + " Preto: " + totalPreto);

                    if (pontos[c][r] == Color.BLACK && columSel < Math.abs(c - meio)) {
                        columSel = c;
                        rowSel = r;
                    }
                }



            }



//        Log.v(TAG, columSel + "x" + rowSel);
            switch (columSel) {
                case 0://Bem no canto                 
                    potenciaDir = (int)(potencia * 0.9);
                    potenciaEsq = (int) ((int)(potencia * -0.9));
                    break;
                case 1:
                    potenciaDir = (int)(potencia * 0.9);
                    potenciaEsq = (int) (potencia * -0.6);
                    break;
                case 2:
                    potenciaDir = (int)(potencia * 0.9);
                    potenciaEsq = (int) (potencia * 0.1);
                    break;
                case 3:// um pouco pra esquerda ajuste
                    potenciaDir = (int) ((int)(potencia * 0.9));
                    potenciaEsq = (int) (potencia * 0.7);
                    break;
                case 4: // no meio Potencia Maxima a frente
                    potenciaDir = (int) ((int)(potencia * 0.9));
                    potenciaEsq = (int) ((int)(potencia * 0.9));
                    break;
                case 5:
                    potenciaEsq = (int)(potencia * 0.9);
                    potenciaDir = (int) (potencia * 0.7);
                    break;
                case 6:
                    potenciaEsq = (int) ((int)(potencia * 0.9));
                    potenciaDir = (int) (potencia * 0.1);
                    break;
                case 7:
                    potenciaEsq = (int)(potencia * 0.9);
                    potenciaDir = (int) (potencia * -0.60);
                    break;
                case 8:
                    potenciaEsq = (int)(potencia * 0.9);
                    potenciaDir = (int)(potencia * -0.9);
                    break;
                default://Fudeu Engata RE
                    potenciaEsq = (int) (potencia * -0.50);
                    potenciaDir = (int) (potencia * -0.50);
                    break;

            }
            if (potenciaEsq == (int) (potencia * -0.50) && potenciaDir == (int) (potencia * -0.50)) {
                /*
                 * se for igual, não muda nada se esquerda é maior, estava andando
                 * para a direita, logo, a ré deve tender para a direita a mesma
                 * coisa (só que ao contrário) para a direita maior
                 */
                if (ultimaEsqValido > ultimaDirValido) {
                    potenciaEsq = (int) (potencia * -0.2);
                } else {
                    potenciaDir = (int) (potencia * -0.2);
                }
            } else {
                ultimaEsqValido = potenciaEsq;
                ultimaDirValido = potenciaDir;
            }
        } else {
            if (potenciaEsq == -50 && potenciaDir == -50 ) {
                potenciaDir = 0;
                potenciaEsq = 0;
            }else{
                potenciaDir = -50;
                potenciaEsq = -50;
            }
            Log.i("TEMVERDE", "TRUE");
        }

//        if (potenciaDir == (int)(potencia*1) && potenciaEsq == (int)(potencia*1) ) {
//            tela.enviarCamera(90);
//        } else {
//            tela.enviarCamera(0);
//        }


//        Log.v(TAG + " Potencia", potenciaEsq + "x" + potenciaDir + "   \n" + view);
        tela.enviarPotencia(potenciaEsq, potenciaDir);

        if (ParametrosActivity.rgb) {
            mat = mRgba;
        } else {
            mat = mgray;
        }

        return mat;
    }
}

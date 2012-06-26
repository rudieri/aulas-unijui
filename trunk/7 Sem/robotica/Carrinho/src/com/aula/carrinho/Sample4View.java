package com.aula.carrinho;

import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.SurfaceHolder;
import com.geomanalitica.utils.Vetorizador;
import com.geomanalitica.utils._2d.Ponto2D;
import com.geomanalitica.utils._2d.Vetor2D;
import com.utils.BitmapDataObject;
import com.utils.LogMod;
import java.util.ArrayList;
import java.util.Date;
import org.opencv.android.Utils;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public class Sample4View extends SampleViewBase {

    public static final int X_INICIAL = 120;
    public static final int X_FINAL = 280;
    public static final int Y_INICIAL = 1;
    private Mat mYuv;
    private Mat mRgba;
    private Mat mGraySubmat;
    private Mat mIntermediateMat;
    private final TelaActivity tela;
    private final boolean modoCaseiro;
    private int[] arrayInicializacaoBitmap;

    public Sample4View(Context context) {
        super(context);
        this.tela = (TelaActivity) context;
        Analizador.setTela(tela);
        this.modoCaseiro = false;
    }

    Sample4View(Context context, boolean modoCaseiro) {
        super(context);
        this.modoCaseiro = modoCaseiro;
        this.tela = (TelaActivity) context;
        Analizador.setTela(tela);
    }

    @Override
    public void surfaceChanged(SurfaceHolder _holder, int format, int width, int height) {
        super.surfaceChanged(_holder, format, width, height);

        synchronized (this) {
            // initialize Mats before usage
            mYuv = new Mat(getCameraFrameHeight() + getCameraFrameHeight() / 2, getCameraFrameWidth(), CvType.CV_8UC1);


            mRgba = new Mat();
            mIntermediateMat = new Mat();
        }
        arrayInicializacaoBitmap = new int[getRedFrameHeight() * getRedFrameWidth()];
        for (int i = 0; i < arrayInicializacaoBitmap.length; i++) {
            arrayInicializacaoBitmap[i] = Color.BLACK;

        }
    }
    byte contaPrint = 0;
    byte contagemParaParada = 3;
    boolean ignorar = false;

    protected Bitmap processFrame(byte[] data) {
        System.out.println("print");
        if (ignorar) {
            System.out.println("Ignorei");
            ignorar = false;
            return null;
        }
        ignorar = true;
        mYuv.put(0, 0, data);

        Imgproc.resize(mYuv, mYuv, new Size(getRedFrameWidth(), getRedFrameHeight()));
        //Monocromatico
        Imgproc.threshold(mYuv, mYuv, 128d, 255d, Imgproc.THRESH_BINARY);

        mGraySubmat = mYuv.submat(0, getRedFrameHeight(), 0, getRedFrameWidth());


        //Modo Canny
        Date inicial = new Date();
        Imgproc.Canny(mGraySubmat, mIntermediateMat, 80, 100);
        Log.d("DELAY", "Canny: " + (new Date().getTime() - inicial.getTime()) + " ms");
        if (!modoCaseiro) {
            inicial = new Date();
            Imgproc.cvtColor(mIntermediateMat, mRgba, Imgproc.COLOR_GRAY2BGR, 4);
            Log.d("DELAY", "cvtColor: " + (new Date().getTime() - inicial.getTime()) + " ms");
        }

////        Imgproc.cvtColor(mGraySubmat, mIntermediateMat, Imgproc.COLOR_YUV420sp2BGR, 4);
//        ArrayList<Mat> contornos = new ArrayList<Mat>();
////        Imgproc.blur(mIntermediateMat, mIntermediateMat, new Size(3, 3));
//        Imgproc.Canny(mGraySubmat, mIntermediateMat, 100, 200, 3);
//        Imgproc.findContours(mIntermediateMat, contornos, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
//        for (int i = 0; i < contornos.size(); i++) {
//            Mat mat = contornos.get(i);
//            System.out.println("r: " + mat.rows());
//            System.out.println("c: " + mat.cols());
//            mat.get(0, 0)
//        }

        inicial = new Date();
        Bitmap bmp = Bitmap.createBitmap(getRedFrameWidth(), getRedFrameHeight(), Bitmap.Config.ARGB_8888);
        Log.d("DELAY", "createBitmap: " + (new Date().getTime() - inicial.getTime()) + " ms");

//        System.out.println("Print");
//        contaPrint = 0;
        inicial = new Date();
        bmp.setPixels(arrayInicializacaoBitmap, 0, getRedFrameWidth(), 0, 0, getRedFrameWidth(), getRedFrameHeight());
//        for (int i = 0; i < bmp.getWidth(); i++) {
//            for (int j = 0; j < bmp.getHeight(); j++) {
//                bmp.setPixel(i, j, Color.BLACK);
//            }
//        }
        Log.d("DELAY", "bmp.setPixel: " + (new Date().getTime() - inicial.getTime()) + " ms");
//        if (true) {
//            Mat mat = Mat.zeros(mIntermediateMat.size(), CvType.CV_8UC1);
////            Range range = new Range(0, 255);
//            
//            Scalar color = new Scalar(127);
//            for (int i = 0; i < contornos.size()-1; i++) {
//                Imgproc.drawContours(mat, contornos, i, color);
////                Mat mat = contornos.get(i);
////                Mat proxMat = contornos.get(i);
//                
////                linhaToBmp(mat.get(0, 0)[0], mat.get(0, 0)[1], proxMat.get(0, 0)[0], proxMat.get(0, 0)[0], bmp);
////                Utils.matToBitmap(mat, bmp);
//                System.out.println("Mapping");
//            }
//            Utils.matToBitmap(mat, bmp);
//            return bmp;
//        }
        if (!modoCaseiro) {
            Mat pontos = Analizador.analizarCanny(mIntermediateMat, bmp);
            Analizador.facaAlgoComCarrinho(pontos, bmp);

            Mat aux = new Mat(getRedFrameWidth(), getRedFrameWidth(), CvType.CV_8UC1);
            Utils.bitmapToMat(bmp, aux);
//            if (aux.rows() > 0 && aux.cols() > 0) {
//                Log.i("IMAGEM", aux.rows() + " + " + aux.cols());
//                Imgproc.resize(aux, aux, new Size(getCameraFrameWidth(), getCameraFrameHeight()));
//                Utils.matToBitmap(aux, bmp);
//            }
            return bmp;
        }
        ArrayList<Ponto2D> pontos;
//            boolean[][] pretoBranco = converterParaPretoBranco(mRgba);
        pontos = Vetorizador.vetorizar(mRgba);

        Utils.matToBitmap(mRgba, bmp);

        if (pontos != null && pontos.size() > 1) {
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
            return bmp;
        }
        tela.setModoAutonomo(true);
        Vetor2D centro = new Vetor2D(new Ponto2D(mRgba.rows() / 2, 0), new Ponto2D(mRgba.rows() / 2, mRgba.cols()));

        ArrayList<Ponto2D> otm = new ArrayList<Ponto2D>(3);
        otm.add(pontos.get(0));
        if (pontos.size() > 2) {
            otm.add(pontos.get(pontos.size() / 2 + 1));
        }
        otm.add(pontos.get(pontos.size() - 1));
        Vetor2D vetor2D;
        if (otm.size() == 3) {
            vetor2D = new Vetor2D(otm.get(1), otm.get(2));

        } else {
            vetor2D = new Vetor2D(otm.get(0), otm.get(1));
        }
//        System.out.println("Centro: " + centro);
//        System.out.println("Vetor: " + vetor2D);
        final float anguloInterno = Vetor2D.anguloInterno(centro, vetor2D);

        int potenciaEsquerda = 70;
        int potenciaDireita = 70;
        System.out.println("Angulo: " + anguloInterno);
        float dif = anguloInterno - 90;
        if (dif > 0) {
            potenciaDireita -= dif * 1.5;
        } else {
            potenciaEsquerda += dif * 1.5;
        }
//        }
        potenciaEsquerda = Math.max(0, potenciaEsquerda);
        potenciaEsquerda = Math.min(potenciaEsquerda, 70);
        potenciaDireita = Math.max(0, potenciaDireita);
        potenciaDireita = Math.min(potenciaDireita, 70);

        tela.enviarPotencia(potenciaEsquerda, potenciaDireita);

        for (int i = 0; otm != null && i < otm.size() - 1; i++) {
            Ponto2D ponto = otm.get(i);
            Ponto2D proxPonto = otm.get(i + 1);
            linhaToBmp((int) ponto.getX(), (int) ponto.getY(), (int) proxPonto.getX(), (int) proxPonto.getY(), bmp);
        }
        BitmapDataObject bmpArray = new BitmapDataObject();
        bmpArray.bitMattoArray(bmp);
        LogMod.i(bmpArray);
//            pintar = false;
        return bmp;

    }

    private static byte sinal(int x) {
        return (byte) ((x < 0) ? -1 : ((x > 0) ? 1 : 0));
    }

    public static void linhaToBmp(int x1, int y1, int x2, int y2, Bitmap bmp) {
        linhaToBmp(x1, y1, x2, y2, bmp, Color.RED);
    }

    public static void linhaToBmp(int x1, int y1, int x2, int y2, Bitmap bmp, int color) {
        System.out.println("[" + x1 + "," + y1 + "] -> [" + x2 + "," + y2 + "]");
        if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0) {
            Log.w("TOBMP", "Tentativa de pintar bmp falhou, valores [" + x1 + "," + y1 + "] -> [" + x2 + "," + y2
                    + "] num bmp " + bmp.getWidth() + " x " + bmp.getHeight());
            return;
        }
        if (x1 >= bmp.getWidth() || x2 >= bmp.getWidth() || y1 >= bmp.getHeight() || y2 >= bmp.getHeight()) {
            return;
        }
        int dx, dy, sdx, sdy, px, py, dxabs, dyabs, i;
        float inclinacao;

        dx = x2 - x1;      /*
         * distância horizontal da linha
         */
        dy = y2 - y1;      /*
         * distância vertical da linha
         */
        dxabs = Math.abs(dx);
        dyabs = Math.abs(dy);
        sdx = sinal(dx);
        sdy = sinal(dy);

        if (dxabs >= dyabs) /*
         * a linha é mais horizontal que vertical
         */ {
            inclinacao = (float) dy / (float) dx;
            for (i = 0; i != dx; i += sdx) {
                px = i + x1;
                py = (int) (inclinacao * i + y1);    // dy=slope*delta_x
                bmp.setPixel(px, py, color);
            }
        } else /*
         * a linha é mais vertical que horizontal
         */ {
            inclinacao = (float) dx / (float) dy;
            for (i = 0; i != dy; i += sdy) {
                px = (int) (inclinacao * i + x1);
                py = i + y1;
                bmp.setPixel(px, py, color);
            }
        }
    }

    public static void linhaToBmp(double x1, double y1, double x2, double y2, Bitmap bmp, int color) {
        linhaToBmp((int) x1, (int) y1, (int) x2, (int) y2, bmp, color);
    }

    public static void linhaToBmp(double x1, double y1, double x2, double y2, Bitmap bmp) {
        linhaToBmp((int) x1, (int) y1, (int) x2, (int) y2, bmp);
    }

    private void converterParaPretoBranco(byte[] rgb, int limiar) {
        for (int i = 0; i < rgb.length; i++) {
            rgb[i] = (byte) (rgb[i] > limiar ? 255 : 0);
        }
    }

    private boolean[][] converterParaPretoBranco(Mat mat) {
        boolean[][] pretoBranco = new boolean[mat.rows()][mat.cols()];
        final int colunas = mRgba.cols() / 2;
        for (int r = 0; r < mRgba.rows(); r++) {
            for (int c = 0; c < colunas; c++) {
                pretoBranco[r][c] = mRgba.get(r, c)[0] > 0;
            }
        }
        return pretoBranco;
    }

    @Override
    public void run() {
        super.run();

        synchronized (this) {
            // Explicitly deallocate Mats
            if (mYuv != null) {
                mYuv.release();
            }
            if (mRgba != null) {
                mRgba.release();
            }
            if (mGraySubmat != null) {
                mGraySubmat.release();
            }
            if (mIntermediateMat != null) {
                mIntermediateMat.release();
            }

            mYuv = null;
            mRgba = null;
            mGraySubmat = null;
            mIntermediateMat = null;
        }
    }

    public native void FindFeatures(long matAddrGr, long matAddrRgba);

    static {
        System.loadLibrary("mixed_sample");
    }
}

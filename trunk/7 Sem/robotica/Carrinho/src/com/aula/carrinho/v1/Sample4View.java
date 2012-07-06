package com.aula.carrinho.v1;

import com.aula.carrinho.v1.Analizador;
import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.SurfaceHolder;
import com.aula.carrinho.TelaActivity;
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
        Imgproc.threshold(mYuv, mYuv, 116, 255d, Imgproc.THRESH_BINARY);

        mGraySubmat = mYuv.submat(0, getRedFrameHeight(), 0, getRedFrameWidth());
        
        
        Date inicial = new Date();
        Bitmap bmp = Bitmap.createBitmap(getRedFrameWidth(), getRedFrameHeight(), Bitmap.Config.ARGB_8888);
        Log.d("DELAY", "createBitmap: " + (new Date().getTime() - inicial.getTime()) + " ms");

        inicial = new Date();
        bmp.setPixels(arrayInicializacaoBitmap, 0, getRedFrameWidth(), 0, 0, getRedFrameWidth(), getRedFrameHeight());
        Log.d("DELAY", "bmp.setPixel: " + (new Date().getTime() - inicial.getTime()) + " ms");

        if (!modoCaseiro) {
            return bmp;
        }
        /* ####################################
         * 
         * ACHO QUE DA PARA APAGAR DAQUI PR BAIXO...
         * 
         * #########################################3
         */
        
       
        ArrayList<Ponto2D> pontos;
//            boolean[][] pretoBranco = converterParaPretoBranco(mRgba);
        pontos = Vetorizador.vetorizar(mRgba);

        Utils.matToBitmap(mRgba, bmp);

        if (pontos != null && pontos.size() > 1) {
//            System.out.println("Pontos: " + pontos.size());
        } else {
            contagemParaParada--;
            if (contagemParaParada <= 0) {
//                if (tela.isModoAutonomo()) {
//
//                    tela.enviarPotencia(0, 0);
//                    tela.setModoAutonomo(false);
//                }
                contagemParaParada = 3;
            }
            return bmp;
        }
//        tela.setModoAutonomo(true);
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

package com.aula.carrinho;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.SurfaceHolder;
import com.geomanalitica.utils.Vetorizador;
import com.geomanalitica.utils._2d.Ponto2D;
import java.util.ArrayList;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

class Sample4View extends SampleViewBase {

    private Mat mYuv;
    private Mat mRgba;
    private Mat mGraySubmat;
    private Mat mIntermediateMat;

    public Sample4View(Context context) {
        super(context);
    }

    @Override
    public void surfaceChanged(SurfaceHolder _holder, int format, int width, int height) {
        super.surfaceChanged(_holder, format, width, height);

        synchronized (this) {
            // initialize Mats before usage
            mYuv = new Mat(getFrameHeight() + getFrameHeight() / 2, getFrameWidth(), CvType.CV_8UC1);
            mGraySubmat = mYuv.submat(0, getFrameHeight(), 0, getFrameWidth());

            mRgba = new Mat();
            mIntermediateMat = new Mat();
        }
    }
    byte contaPrint = 0;

    @Override
    protected Bitmap processFrame(byte[] data) {
        if (contaPrint++ == 6) {
            contaPrint = 0;
        }else{
            return null;
        }
        mYuv.put(0, 0, data);


        //Modo Canny
        Imgproc.Canny(mGraySubmat, mIntermediateMat, 80, 100);
        Imgproc.cvtColor(mIntermediateMat, mRgba, Imgproc.COLOR_GRAY2BGR, 4);


        Bitmap bmp = Bitmap.createBitmap(getFrameWidth(), getFrameHeight(), Bitmap.Config.ARGB_8888);

        ArrayList<Ponto2D> pontos;
//            boolean[][] pretoBranco = converterParaPretoBranco(mRgba);
            pontos = Vetorizador.vetorizar(mRgba);

        if (Utils.matToBitmap(mRgba, bmp)) {
//            Mat mat = new Mat(pontos., GONE, GONE, null)
            if (pontos != null) {
                System.out.println("Pontos: " + pontos.size());
            }
            for (int i = 0; pontos != null && i < pontos.size() - 1; i++) {
                Ponto2D ponto = pontos.get(i);
                Ponto2D proxPonto = pontos.get(i + 1);
                linhaToBmp((int) ponto.getX(), (int) ponto.getY(), (int) proxPonto.getX(), (int) proxPonto.getY(), bmp);
            }
            return bmp;
        }


        bmp.recycle();
        return null;
    }

    private byte sinal(int x) {
        return (byte) ((x < 0) ? -1 : ((x > 0) ? 1 : 0));
    }//macro que retorna o sinal de um número

    private void linhaToBmp(int x1, int y1, int x2, int y2, Bitmap bmp) {
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
                bmp.setPixel(px, py, Color.RED);
            }
        } else /*
         * a linha é mais vertical que horizontal
         */ {
            inclinacao = (float) dx / (float) dy;
            for (i = 0; i != dy; i += sdy) {
                px = (int) (inclinacao * i + x1);
                py = i + y1;
                bmp.setPixel(px, py, Color.RED);
            }
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

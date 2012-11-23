/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aula.carrinho;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.aula.carrinho.v1.Analizador;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author manchini
 */
public class CarrinhoView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private static final String TAG = "CarrinhoView";
    //    
    private final Context context;
    private final TelaActivity tela;
    private final SurfaceHolder mHolder;
    // Paramatros CameraTamanho da Captura de Camera
    private Camera mCamera;
    private int mCameraWidth = 480;
    private int mCameraHeight = 320;
    //
    private byte[] mFrame; // Preview Camera
    private boolean mThreadRun;
    //
    private Mat imagemCamera;

    public CarrinhoView(Context context) {
        super(context);
        this.context = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
        Log.i(TAG, "Instanciado new " + this.getClass());
        //
        this.tela = (TelaActivity) context;
        Analizador.setTela(tela);
    }

    /**
     * Executa a Primeira Vez
     *
     * @param holder
     */
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "surfaceCreated");
        mCamera = Camera.open();
        if (ParametrosActivity.comFlash) {
            mCamera.getParameters().setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        }
        mCamera.getParameters().setPreviewFormat(ImageFormat.RGB_565);
        mCamera.getParameters().setPreviewSize(480, 320);

        mCamera.setParameters(mCamera.getParameters());
        mCamera.startPreview();
        mCamera.setPreviewCallback(new Camera.PreviewCallback() {

            public void onPreviewFrame(byte[] data, Camera camera) {
                synchronized (CarrinhoView.this) {
                    mFrame = data;
                    CarrinhoView.this.notify();
                }
            }
        });
        (new Thread(this)).start();
    }

    /**
     * Isso é chamado imediatamente após qualquer alteração estrutural (formato
     * ou           Tamanho *) têm sido feitas para a superfície
     *
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "surfaceCreated");



        ////
        synchronized (this) {
            // initialize Mats before usage // Escala de Cinza
            imagemCamera = new Mat(mCameraWidth, mCameraHeight, CvType.CV_8UC1);

        }




    }

    /**
     * Chama-se imediatamente antes de uma superfície está a ser destruída
     *
     * @param holder
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "surfaceDestroyed");
        mThreadRun = false;
        if (mCamera != null) {
            synchronized (this) {
                mCamera.stopPreview();
                mCamera.setPreviewCallback(null);
                mCamera.release();
                mCamera = null;
            }
        }
    }

    /**
     * Thread RUN MASTER
     */
    public void run() {
        mThreadRun = true;
        Log.i(TAG, "Starting processing thread");
        while (mThreadRun) {
            Bitmap bmp = null;

            synchronized (this) {
                try {
                    this.wait();
                    bmp = processFrame(mFrame);
                } catch (InterruptedException e) {
                    Log.d("CARRINHO", e.getMessage(), e);
                }
            }

            //Pinta na tela o Bitmat 
            if (bmp != null) {
                Canvas canvas = mHolder.lockCanvas();
                if (canvas != null) {
                    canvas.drawBitmap(bmp, (canvas.getWidth() - mCameraWidth) / 2, (canvas.getHeight() - mCameraHeight) / 2, null);
                    mHolder.unlockCanvasAndPost(canvas);
                }
                bmp.recycle();
            }

            synchronized (this) {
                // Explicitly deallocate Mats
                if (imagemCamera != null) {
                    imagemCamera.release();
                }

            }

        }
    }

    private Bitmap processFrame(byte[] data) {
        imagemCamera.put(0, 0, data);
        //Redimenciona
        Imgproc.resize(imagemCamera, imagemCamera, new Size(mCameraWidth, mCameraHeight));
        //Monocromatico
        Imgproc.threshold(imagemCamera, imagemCamera, 128d, 255d, Imgproc.THRESH_BINARY);
        // BITMAP
        Bitmap bmp = Bitmap.createBitmap(mCameraWidth, mCameraHeight, Bitmap.Config.ARGB_8888);

        Utils.matToBitmap(imagemCamera, bmp);

        return bmp;

    }
}

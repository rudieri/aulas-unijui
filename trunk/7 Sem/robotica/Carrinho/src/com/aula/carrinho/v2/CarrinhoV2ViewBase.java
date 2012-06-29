package com.aula.carrinho.v2;

import android.app.Activity;
import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.hardware.*;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.aula.carrinho.ParametrosActivity;

public abstract class CarrinhoV2ViewBase extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    protected static final String TAG = "Carrinho::SurfaceView";
    private Camera mCamera;
    private SurfaceHolder mHolder;
    private int mFrameWidth;
    private int mFrameHeight;
    private byte[] mFrame;
    private boolean mThreadRun;

    public CarrinhoV2ViewBase(Context context) {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    public int getCameraFrameWidth() {
        return mFrameWidth;
    }

    public int getCameraFrameHeight() {
        return mFrameHeight;
    }


    public void surfaceChanged(SurfaceHolder _holder, int format, int width, int height) {
        Log.i(TAG, "surfaceCreated");
        if (mCamera != null) {
            Camera.Parameters params = mCamera.getParameters();
            if (ParametrosActivity.comFlash) {
                params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            }
            List<Camera.Size> sizes = params.getSupportedPreviewSizes();
            mFrameWidth = width;
            mFrameHeight = height;

            // selecting optimal camera preview size
            {
                double minDiff = Double.MAX_VALUE;
                for (Camera.Size size : sizes) {
                    if (Math.abs(size.height - height) < minDiff) {
                        mFrameWidth = size.width;
                        mFrameHeight = size.height;
                        minDiff = Math.abs(size.height - height);
                    }
                }
            }

            params.setPreviewSize(getCameraFrameWidth(), getCameraFrameHeight());
            mCamera.setParameters(params);
            try {
                mCamera.setPreviewDisplay(null);
            } catch (IOException e) {
                Log.e(TAG, "mCamera.setPreviewDisplay fails: " + e);
            }
            mCamera.startPreview();
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "surfaceCreated");
        mCamera = Camera.open();
        if (ParametrosActivity.comFlash) {
            mCamera.getParameters().setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        }
        mCamera.getParameters().setPreviewFormat(ImageFormat.RGB_565);
        mCamera.setParameters(mCamera.getParameters());
       
        mCamera.setPreviewCallback(new PreviewCallback() {

            public void onPreviewFrame(byte[] data, Camera camera) {
                synchronized (CarrinhoV2ViewBase.this) {
                    mFrame = data;
                    CarrinhoV2ViewBase.this.notify();
                }
            }
        });
        (new Thread(this)).start();
    }

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

    protected abstract Bitmap processFrame(byte[] data);

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

            if (bmp != null) {
                Canvas canvas = mHolder.lockCanvas();
                if (canvas != null) {
                    canvas.drawBitmap(bmp, (canvas.getWidth() - getCameraFrameWidth()) / 2, (canvas.getHeight() - getCameraFrameHeight()) / 2, null);
                    mHolder.unlockCanvasAndPost(canvas);
                }
                bmp.recycle();
            }
        }
    }
}
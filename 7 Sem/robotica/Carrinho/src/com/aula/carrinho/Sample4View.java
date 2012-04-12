package com.aula.carrinho;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.imgproc.Imgproc;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.SurfaceHolder;

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

    @Override
    protected Bitmap processFrame(byte[] data) {
        mYuv.put(0, 0, data);


        //Modo Canny
        Imgproc.Canny(mGraySubmat, mIntermediateMat, 80, 100);
        Imgproc.cvtColor(mIntermediateMat, mRgba, Imgproc.COLOR_GRAY2BGR, 4);


        Bitmap bmp = Bitmap.createBitmap(getFrameWidth(), getFrameHeight(), Bitmap.Config.ARGB_8888);

//        for (int r = 0; r < mRgba.rows(); r++) {
//            for (int c = 0; c < mRgba.cols(); c++) {
//                double[] get = mRgba.get(r, c);
//                String aux = "";
//                for (int i = 0; i < get.length; i++) {
//                   aux+=get[i]+" ; ";           
//                }
//                 Log.i("MATRIX",aux);        
//            }
//            
//        }
        
        if (Utils.matToBitmap(mRgba, bmp)) {
            return bmp;
        }
                
     
        bmp.recycle();
        return null;
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

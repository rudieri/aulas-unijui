/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aula.carrinho.v2;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.IOException;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author manchini
 */
public class ReconhecerPessoa {

    public static void run(final Context context, final Mat imagemCamera) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    processar(context, imagemCamera);
                } catch (IOException ex) {
                    Log.e(null, "Erro", ex);
                }
            }
        }).start();
    }

    public static void processar(Context context, Mat imagemCamera) throws IOException {
        AssetFileDescriptor openFd = context.getAssets().openFd("gfx/pessoapalito.jpg");
        Bitmap bitmap = BitmapFactory.decodeStream(openFd.createInputStream());
        Mat template = new Mat();
        Utils.bitmapToMat(bitmap, template);

        Mat mResult = new Mat(imagemCamera.cols(), imagemCamera.rows(), CvType.CV_8U); 
        Imgproc.matchTemplate(imagemCamera, template, mResult, Imgproc.TM_CCORR_NORMED);
        Core.MinMaxLocResult result = Core.minMaxLoc(mResult);
        Double maxVal = result.maxVal;
        Log.i("PESSIA", maxVal.toString());
    }
}

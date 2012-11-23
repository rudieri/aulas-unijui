/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aula.carrinho.v3;

import android.view.SurfaceView;
import android.view.View;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.NativeCameraView;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 *
 * @author rudieri
 */
public class CarrinhoV3View  implements CameraBridgeViewBase.CvCameraViewListener {

    private Mat mgray;
    private Mat mRgba;
    

    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        
    }

    public void onCameraViewStopped() {
        mRgba.release();
    }

    public Mat onCameraFrame(Mat mat) {
        
        return mat;
    }
}

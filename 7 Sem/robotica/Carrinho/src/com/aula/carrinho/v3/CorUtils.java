/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aula.carrinho.v3;

import org.opencv.core.Mat;

/**
 *
 * @author manchini
 */
public class CorUtils {
    
    public static boolean temVermelho(Mat mat){
        
        for (int c = 0; c < mat.cols(); c+=15) {
            for (int r = 0; r < mat.rows(); r+=15) {
                double[] pixel = mat.get(r, c);
                //Acho que é vermelho
                if(pixel[0]==200){
                    return true;
                }                
            }
            
        }
        
        
        return false;
    }
    
     public static boolean temVerde(Mat mat){
        
        for (int c = 0; c < mat.cols(); c+=15) {
            for (int r = 0; r < mat.rows(); r+=15) {
                double[] corPixel = mat.get(r, c);
                //Acho que é verde
                if(corPixel[1]>150 && corPixel[0] < 100 && corPixel[2]<100){
                    return true;
                }
                if(corPixel[1]>200 && corPixel[0] < 120 && corPixel[2]<120){
                    return true;
                }
                if(corPixel[1]>240 && corPixel[0] < 170 && corPixel[2]<170){
                    return true;
                }
                if (corPixel[1]>100 && corPixel[0] < 30 && corPixel[2]<50) {
                    return true;
                }
            }
            
        }
        
        
        return false;
    }
    
}

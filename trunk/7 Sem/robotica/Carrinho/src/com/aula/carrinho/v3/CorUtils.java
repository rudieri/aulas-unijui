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
    
}

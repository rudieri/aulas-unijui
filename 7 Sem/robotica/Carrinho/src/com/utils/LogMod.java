/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import android.graphics.Bitmap;
import android.util.Log;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manchini
 */
public class LogMod {
    
    static ServidorSocket servidorSocket;
    
    
    public static void init(){
        if(servidorSocket!=null){
            return;
        }
        try {
            servidorSocket = new ServidorSocket();
            new Thread(new Runnable() {

                public void run() {
                    servidorSocket.run();
                }
            }).start();
            
        } catch (IOException ex) {
            Log.e("CARRINHO", "ERRO FATAL", ex);
        }
    }
    
    
    public static void i(String tag,String txt){
        Log.i(tag, txt);
        servidorSocket.mandaComando(tag+"&$&"+txt);
    }
    public static void i(Object obj){
        servidorSocket.mandaComando(obj);
    }
    
    public static void e(String tag,String txt,Exception ex){
        Log.e(tag, txt,ex);
        servidorSocket.mandaComando(tag+"&$&"+txt);
        servidorSocket.mandaComando(ex);
    }
    
    
}

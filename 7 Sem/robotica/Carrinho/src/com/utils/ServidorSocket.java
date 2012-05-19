/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import android.util.Log;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author manchini
 */
public class ServidorSocket {

    private static ServerSocket ss;
    private boolean on = true;
    private ArrayList<TrataCliente> clientes = new ArrayList<TrataCliente>();

   
    public ServidorSocket() throws IOException{
        ss = new ServerSocket(6666);       
       Log.i("CARRINHO", "IP = "+ss.getInetAddress().getHostAddress());
       System.out.println("Servidor ouvindo na porta:" + ss.getLocalPort());

    }

    public void run() {
        try {
            while (on) {
                final Socket cliente = ss.accept();
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            TrataCliente trataCliente = new TrataCliente(ServidorSocket.this,cliente);
                            clientes.add(trataCliente);
                        } catch (IOException ex) {
                            Log.e("CARRINHO", "ERRO ao CONECTAR CLIENTE", ex);
                        }
                    }
                }).start();

            }
            close();

        } catch (Exception e) {
             Log.e("CARRINHO", "Eroo Fatals", e);
        }
    }

    public Socket getAccept() throws IOException {
        return ss.accept();
    }
    
    public void close() throws IOException{
        ss.close();
    }
    
    public void removerCliente(TrataCliente trataCliente){
        clientes.remove(trataCliente);
    }
    
    public void mandaComando(Object obj){
        for (int i = 0; i < clientes.size(); i++) {
            TrataCliente trataCliente = clientes.get(i);
            trataCliente.mandaComando(obj);
        }
    }

}

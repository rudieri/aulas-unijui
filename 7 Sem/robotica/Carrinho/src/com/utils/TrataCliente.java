/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

class TrataCliente {

    private Socket cliente;
    private ServidorSocket servidor;
    

    public TrataCliente(ServidorSocket servidor,Socket cliente) throws IOException {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    public void mandaComando(Object obj) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());            
            out.writeObject(obj);
            out.flush();

        } catch (Exception e) {
            System.out.println("NULLL");
        }
    }
    
    
    public void close() throws IOException{
        cliente.close();
        servidor.removerCliente(this);
    }
}

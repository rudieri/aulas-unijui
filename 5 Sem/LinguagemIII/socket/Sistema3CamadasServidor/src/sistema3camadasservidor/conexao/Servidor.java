/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema3camadasservidor.conexao;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manchini
 */
public class Servidor implements Runnable {

    private static ServerSocket ss;
    private boolean on = true;

    public Servidor() throws IOException {
        ss = new ServerSocket(4445);
        System.out.println("Servidor ouvindo na porta:" + ss.getLocalPort());

    }

    public void run() {
        try {
            while (on) {
                final Socket cliente = ss.accept();
                new Thread(new Runnable() {

                    public void run() {
                        try {
                            TrataCliente trataCliente = new TrataCliente(cliente);
                        } catch (IOException ex) {
                            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }).start();

            }

        } catch (Exception e) {
            System.exit(1);
        }
    }

    public Socket getAccept() throws IOException {
        return ss.accept();
    }

    public boolean isOn() {
        return on;
    }

    public static void main(String[] args) {
        try {
            new Thread(
                    new Servidor()).start();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

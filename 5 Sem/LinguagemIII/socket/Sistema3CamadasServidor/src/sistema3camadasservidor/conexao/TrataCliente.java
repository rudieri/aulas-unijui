package sistema3camadasservidor.conexao;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class TrataCliente extends Thread {

    

    private Socket cliente;
    private Servidor servidor;


    public TrataCliente(Servidor servidor) throws IOException {
        this.servidor = servidor;
        cliente = servidor.getAccept();
        start();
    }

    public void run() {
        try {
            OutputStream out = cliente.getOutputStream();
            InputStream in = cliente.getInputStream();
            while(servidor.isOn()){

                


            }
            out.close();
            cliente.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    
}

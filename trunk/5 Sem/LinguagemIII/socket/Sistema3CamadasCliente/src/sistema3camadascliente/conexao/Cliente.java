/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema3camadascliente.conexao;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import sistema3camadasbase.conexao.Mensagem;

/**
 *
 * @author manchini
 */
public class Cliente {

    private static String ip = "localhost";
    private static int porta = 4445;

    public static Serializable comando(final int tipo, final Serializable obj) throws Exception {
        try {

            Socket echoSocket = new Socket(getIp(), getPorta());
            ObjectOutputStream out = new ObjectOutputStream(echoSocket.getOutputStream());
            ObjectInputStream inp = new ObjectInputStream(echoSocket.getInputStream());
            Mensagem envio = new Mensagem();
            Mensagem retorno = null;

            envio.setTipo(tipo);
            envio.setObjeto(obj);
            out.writeObject(envio);
            retorno = (Mensagem) inp.readObject();

            return retorno;

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * @return the ip
     */
    public static String getIp() {
        return ip;
    }

    /**
     * @param aIp the ip to set
     */
    public static void setIp(String aIp) {
        ip = aIp;
    }

    /**
     * @return the porta
     */
    public static int getPorta() {
        return porta;
    }

    /**
     * @param aPorta the porta to set
     */
    public static void setPorta(int aPorta) {
        porta = aPorta;
    }
}

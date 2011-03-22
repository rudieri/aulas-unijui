/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema3camadascliente.conexao;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
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
            switch (tipo) {
                case Mensagem.TIPO_INCLUIR:
                    envio.setTipo(Mensagem.TIPO_INCLUIR);
                    envio.setObjeto(obj);
                    out.writeObject(envio);
                    retorno = (Mensagem) inp.readObject();
                    break;
                case Mensagem.TIPO_EXCLUIR:
                    envio.setTipo(Mensagem.TIPO_EXCLUIR);
                    envio.setObjeto(obj);
                    out.writeObject(envio);
                    retorno = (Mensagem) inp.readObject();
                    break;
                case Mensagem.TIPO_LISTAR:
                    envio.setTipo(Mensagem.TIPO_LISTAR);
                    envio.setObjeto(obj);
                    out.writeObject(envio);
                    retorno = (Mensagem) inp.readObject();
                    break;
                case Mensagem.TIPO_CARREGAR:
                    envio.setTipo(Mensagem.TIPO_CARREGAR);
                    envio.setObjeto(obj);
                    out.writeObject(envio);
                    retorno = (Mensagem) inp.readObject();
                    break;
            }


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

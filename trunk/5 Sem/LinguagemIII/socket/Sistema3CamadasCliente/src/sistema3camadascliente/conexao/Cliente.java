/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema3camadascliente.conexao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import sistema3camadasbase.conexao.Mensagem;
import sistema3camadasbase.conexao.Montador;

/**
 *
 * @author manchini
 */
public class Cliente {

    public static String comando(final int tipo, final Object obj) throws Exception {
        try {

            Socket echoSocket = new Socket("localhost", 4445);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            String retorno = "";
            String readLine;
            Thread.sleep(1000);

            switch (tipo) {
                case Mensagem.TIPO_INCLUIR:
                    out.println(Mensagem.TIPO_INCLUIR + "&" + obj.toString());
                    readLine = reader.readLine();
                    retorno = readLine.substring(readLine.indexOf("&") + 1);
                    break;
                case Mensagem.TIPO_EXCLUIR:
                    out.println(Mensagem.TIPO_EXCLUIR + "&" + obj.toString());
                    readLine = reader.readLine();
                    retorno = readLine.substring(readLine.indexOf("&") + 1);
                    break;
                case Mensagem.TIPO_LISTAR:
                    out.println(Mensagem.TIPO_LISTAR + "&" + obj.toString());
                    readLine = reader.readLine();
                    retorno = readLine.substring(readLine.indexOf("&") + 1);
                    break;
                    case Mensagem.TIPO_CARREGAR:
                    out.println(Mensagem.TIPO_CARREGAR + "&" + obj.toString());
                    readLine = reader.readLine();
                    retorno = readLine.substring(readLine.indexOf("&") + 1);
                    break;
            }


            return retorno;






        } catch (Exception ex) {
            throw ex;
        }
    }

    public static ArrayList toArrayList(String lista) {

        ArrayList arrayList = new ArrayList();
        String[] split = lista.split(";");
        for (String string : split) {
            if (!string.trim().equals("")) {
                arrayList.add(Montador.Montador(string));
                
            }
        }


        return arrayList;
    }
}

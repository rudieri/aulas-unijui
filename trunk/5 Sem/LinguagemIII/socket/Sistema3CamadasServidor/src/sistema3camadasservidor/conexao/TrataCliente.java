package sistema3camadasservidor.conexao;

import sistema3camadasbase.conexao.Montador;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import sistema3camadasbase.conexao.Lista;
import sistema3camadasbase.conexao.Mensagem;
import sistema3camadasbase.musica.Musica;
import sistema3camadasbase.musica.album.Album;
import sistema3camadasbase.musica.artista.Artista;
import sistema3camadasbase.musica.genero.Genero;
import sistema3camadasservidor.banco.Transacao;

class TrataCliente extends Thread {

    private Socket cliente;

    public TrataCliente(Socket cliente) throws IOException {
        this.cliente = cliente;
        start();
    }

    public void run() {
        boolean semMsg = false;
        try {
//            org.apache.log4j.BasicConfigurator.configure();
            OutputStream out = cliente.getOutputStream();
            InputStream in = cliente.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            PrintWriter pr = new PrintWriter(cliente.getOutputStream(), true);
            String readLine = reader.readLine();
            try {
                int tipo = Integer.valueOf(readLine.substring(0, 1));
                System.out.println(readLine);
                Serializable obj = null;
                Transacao t = new Transacao(true);
                t.begin();
                obj = Montador.Montador(readLine);
                switch (tipo) {
                    case Mensagem.TIPO_INCLUIR:
                        t.saveOrUpdate(obj);
                        break;

                    case Mensagem.TIPO_EXCLUIR:
                        t.delete(obj);
                        break;

                    case Mensagem.TIPO_LISTAR:
                        Lista lista = new Lista();
                        if (obj instanceof Album) {
                            lista.addAll(t.listar("Album", "WHERE nome like '" + ((Album) obj).getNome() + "%' "));
                        }
                        if (obj instanceof Artista) {
                            lista.addAll(t.listar("Artista", "WHERE nome like '" + ((Artista) obj).getNome() + "%' "));
                        }
                        if (obj instanceof Genero) {
                            lista.addAll(t.listar("Genero", "WHERE nome like '" + ((Genero) obj).getNome() + "%' "));
                        }
                        if (obj instanceof Musica) {
                            lista.addAll(t.listar("Musica", "WHERE nome like '" + ((Musica) obj).getNome() + "%' "));
                        }

                        pr.println(lista.toString());
                        break;
                    case Mensagem.TIPO_CARREGAR:
                        obj = (Serializable) t.load(obj.getClass(),Integer.valueOf(obj.getClass().getMethod("getId", null).invoke(obj, null).toString()));
                        semMsg = true;
                         pr.println(String.valueOf(obj.toString()));
                        break;
                }


                t.commit();
                if (!semMsg) {
                    pr.println(String.valueOf("3&OK"));
                    System.out.println(String.valueOf("3&OK"));
                }
            } catch (Exception ex) {
                pr.println(String.valueOf("3&" + ex.toString()));
                ex.printStackTrace();
            }


            reader.close();
            pr.close();
            out.close();
            in.close();
            cliente.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

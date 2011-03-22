package sistema3camadasservidor.conexao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        Mensagem retorno;
        try {
//            org.apache.log4j.BasicConfigurator.configure();
            ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream inp = new ObjectInputStream(cliente.getInputStream());
             Transacao t = new Transacao(true);
            try {
                Mensagem entrada = (Mensagem) inp.readObject();
                int tipo = entrada.getTipo();
                Serializable obj = (Serializable) entrada.getObjeto();
               
                t.begin();
                switch (tipo) {
                    case Mensagem.TIPO_INCLUIR:
                        t.saveOrUpdate(entrada.getObjeto());
                        break;

                    case Mensagem.TIPO_EXCLUIR:
                        t.delete(entrada.getObjeto());
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
                        retorno = new Mensagem();
                        retorno.setObjeto(lista);
                        out.writeObject(retorno);
                        break;
                    case Mensagem.TIPO_CARREGAR:
                        obj = (Serializable) t.load(obj.getClass(), Integer.valueOf(obj.getClass().getMethod("getId", null).invoke(obj, null).toString()));
                        semMsg = true;
                        retorno = new Mensagem();
                        retorno.setObjeto(obj);
                        out.writeObject(retorno);
                        break;
                }


                t.commit();
                if (!semMsg) {
                    retorno = new Mensagem();
                    retorno.setObjeto("OK");
                    out.writeObject(retorno);
                }
            } catch (Exception ex) {
                t.rollback();
                retorno = new Mensagem();
                retorno.setObjeto(ex);
                out.writeObject(retorno);
                ex.printStackTrace();
            }


            inp.close();
            out.close();
            cliente.close();
        } catch (Exception e) {
            System.out.println("NULLL");
        }
    }
}

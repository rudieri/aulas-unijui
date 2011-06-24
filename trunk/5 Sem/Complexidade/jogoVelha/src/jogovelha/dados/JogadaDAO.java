/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.dados;

import java.util.ArrayList;
import jogovelha.banco.Transacao;

/**
 *
 * @author manchini
 */
public class JogadaDAO {

    public static void mergeJogo(Estado estado, ArrayList<Jogada> jogadas) {
        Transacao t = new Transacao();
        t.begin();
        Jogo jogo = existeJogo(jogadas, t);
        if (jogo == null) {
            jogo = new Jogo();
            jogo.setDerrotas(0);
            jogo.setDesistencias(0);
            jogo.setEmpates(0);
            jogo.setVitorias(0);
            t.save(jogo);
            for (Jogada jogada : jogadas) {
                jogada.setJogo(jogo);
                inserirJogada(jogada, t);
            }
        } else {
            switch (estado) {
                case GANHO:
                    jogo.incrementaVitorias();
                    break;
                case EMPATADO:
                    jogo.incrementaEmpates();
                    break;
                case PERDIDO:
                    jogo.incrementaDerrotas();
                    break;
                case ABANDONADO:
                    jogo.incrementaDesistencias();
                default:
                    throw new AssertionError("Opção inválida para estado do jogo. Valor: " + estado);
            }
            t.saveOrUpdate(jogo);
        }
        t.commit();
    }

    private static void inserirJogada(Jogada jogada, Transacao t) {
        t.save(jogada);
    }

    public static Jogo existeJogo(ArrayList<Jogada> jogadas, Transacao t) {
        StringBuilder builder = new StringBuilder(" where 1=2 ");
        for (Jogada jogada : jogadas) {
            builder.append(" OR (localJogada=").append(jogada.getLocalJogada());
            builder.append(" AND ordemJogada=").append(jogada.getOrdemJogada());
            builder.append(" AND jogadorTipo=").append(jogada.getJogadorTipo());
            builder.append(" ) ");
        }
        ArrayList<Jogada> lista = (ArrayList<Jogada>) t.listar(Jogada.class.getName(), builder.toString());
        if (lista.isEmpty()) {
            return null;
        }
//        if (lista.size() != jogadas.size()) {
//            return null;
//        }
        return lista.get(0).getJogo();
    }


    public static ArrayList<Jogada> listarJogadas(JogadaFiltro filtro, Transacao t) {
        StringBuilder builder = new StringBuilder(" where 1=1 ");
        if (filtro != null) {
            if (filtro.local != null) {
                builder.append(" AND localJogada=").append(filtro.local);
            }
            if (filtro.ordem != null) {
                builder.append(" AND ordemJogada=").append(filtro.ordem);
            }
            if (filtro.jogo != null) {
                builder.append(" AND jogo=").append(filtro.jogo.getId());
            }
        }
        ArrayList<Jogada> lista = (ArrayList<Jogada>) t.listar(Jogada.class.getName(), builder.toString());
       
        return lista;
    }
    public static Jogo carregarJogo(Jogo  jogo,Transacao t){
        return (Jogo) t.load(Jogo.class, jogo.getId());
    }
    public static Jogo carregarJogo(Jogo  jogo){
        Transacao t = new Transacao();
        t.begin();
        Jogo jogo2 = carregarJogo(jogo, t);
        return jogo2;
    }

    public static Jogo existeJogo(ArrayList<Jogada> jogadas) {
        Transacao t = new Transacao();
        t.begin();
        Jogo jogo = existeJogo(jogadas, t);
        t.commit();
        return jogo;
    }

    public static ArrayList<Jogada> listarJogadas(JogadaFiltro filtro) {
        Transacao t = new Transacao();
        t.begin();
        ArrayList<Jogada> lista = listarJogadas(filtro, t);
        t.commit();
        return lista;

    } 
   
}

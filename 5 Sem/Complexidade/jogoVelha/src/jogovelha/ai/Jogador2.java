/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.ai;

import java.util.ArrayList;
import jogovelha.dados.Jogo;
import jogovelha.interfaces.Jogador;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jogovelha.dados.Estado;
import jogovelha.dados.Jogada;
import jogovelha.dados.JogadaDAO;
import jogovelha.dados.JogadaFiltro;
import jogovelha.dados.JogadorTipo;
import jogovelha.dados.Local;
import jogovelha.dados.Ordem;
import jogovelha.marcacao.Ponto;
import jogovelha.tabuleiro.Tabuleiro;

/**
 *
 * @author rudieri
 */
public class Jogador2 implements Jogador {

    private Tabuleiro tabuleiro;
    private static final byte eu = Tabuleiro.JOGADOR_COMPUTADOR;
    private final byte masqPadrao = 4;
    private final Ponto zero = new Ponto(0, 0);
    private byte conta = 0;
    private byte contaJogadas = 0;
    ArrayList<Jogada> jogadas;

    public Jogador2() {
        init();
    }

    private void init() {
    }

    @Override
    public void comecar() {
        try {
            Thread.sleep(500);

            contaJogadas = 0;
            addJogada(Ordem.J0, Local.J0, JogadorTipo.COMPUTADOR);
            contaJogadas++;
            pense(new Ponto(0, 0), masqPadrao);
        } catch (InterruptedException ex) {
            Logger.getLogger(Jogador2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void minhaVez(Ponto ponto) {
        conta = 0;
        addJogada(Ordem.values()[contaJogadas], Local.values()[ponto.toNumero()], JogadorTipo.HUMANO);
        contaJogadas++;
        Ponto tp = tabuleiro.verificarPossivelVencedor(Tabuleiro.COMPUATADOR_VENCER);
        if (tp != null) {
            jogue(tp);
            return;
        }
        tp = tabuleiro.verificarPossivelVencedor(Tabuleiro.HUMANO_VENCER);
        if (tp != null) {
            jogue(tp);
            return;
        }
        ArrayList<Ponto> pl = tabuleiro.getPosicoesLivres();
        if (pl.size() <= 2) {
            if (pl.isEmpty()) {
                return;
            }
            pense(pl.get(0), masqPadrao);
            return;
        }

        //   marcar(p, Tabuleiro.JOGADOR_HUMANO);
        //  p = euPossoGanhar();
        if (!ponto.isCenter()) {
//            byte dono = tabuleiro.getDonoDoMeio();
//            if (dono == eu) {
//                dono = 1;
//            }
//           dono=dono==eu?dono:tabuleiro.getDonoDoPonto(ponto);
            if (!ponto.isCanto() && !ponto.isCenter()) {
                pense(1, 1, getVariante());
            } else {
                byte var = getVariante();
                if (var % 2 != 0) {
                    var = (byte) (var / 2 - 2);
                }
                pense(1, 1, 4 + var);
            }
        } else {

            pense(new Ponto(0, 0), masqPadrao);
        }

    }

    private void pense(int linha, int coluna, int masq) {
        pense(new Ponto(linha, coluna), (byte) masq);
    }

    private byte getVariante() {
        byte var = 0;
        byte[][] cloneMatriz = tabuleiro.getcloneMatriz();
        for (int i = 0; i < cloneMatriz.length; i++) {
            byte[] bs = cloneMatriz[i];
            for (int j = 0; j < bs.length; j++) {
                byte b = bs[j];
                if (b == Tabuleiro.JOGADOR_HUMANO) {
                    var = (byte) (-var + (i * 3 + j));
                }
            }
        }
        return var;
    }

    private void pense(Ponto ponto, byte masq) {
        if (!tabuleiro.existemCasas()) {
            return;
        }
        if (tabuleiro.estaLivre(ponto)) {
            jogue(ponto);
        } else {
            ponto.somar(masq);
            if (masq == 0) {
                masq = 4;
            }
            if (conta > 10) {
                conta = 0;
                masq--;
            }
            conta++;
            pense(ponto, masq);
            // marcar(ponto, Tabuleiro.JOGADOR_COMPUTADOR);
        }
    }

    private void pense(Ponto ponto) {
        pense(ponto, masqPadrao);
    }

    private void jogue(Ponto p) {
//        Jogo jogo = JogadaDAO.existeJogo(jogadas);
        JogadaFiltro filtro = new JogadaFiltro();
        filtro.local = Local.values()[p.toNumero()];
        filtro.ordem = Ordem.values()[contaJogadas];
        ArrayList<Jogada> listaJogadas = JogadaDAO.listarJogadas(filtro);
        Jogo maior = JogadaDAO.carregarJogo(listaJogadas.get(listaJogadas.size() - 1).getJogo());
        filtro = new JogadaFiltro();
        filtro.jogo = maior;
        listaJogadas = JogadaDAO.listarJogadas(filtro);
        maior=JogadaDAO.existeJogo(jogadas) ;

        if (maior == null) {
            addJogada(Ordem.values()[contaJogadas], Local.valueOf((int) p.toNumero()), JogadorTipo.COMPUTADOR);
            contaJogadas++;
            tabuleiro.jogar(eu, p.linha, p.coluna);
        } else {
            if (maior.getSaldo() <= 0 && maior.getEmpates() < maior.getDerrotas()) {
                addJogada(Ordem.values()[contaJogadas], Local.valueOf((int) p.toNumero()), JogadorTipo.COMPUTADOR);
                contaJogadas++;
                p.somar(1);
                pense(p, (byte) 1);
                return;
            } else {
                if (listaJogadas.size() <= contaJogadas) {
                    p.somar(1);
                    pense(p, (byte) 1);
                    return;
                }
                Ponto np = new Ponto(listaJogadas.get(contaJogadas).getLocalJogada().getId());
                addJogada(Ordem.values()[contaJogadas], Local.valueOf((int) np.toNumero()), JogadorTipo.COMPUTADOR);
                contaJogadas++;

                tabuleiro.jogar(eu, np.linha, np.coluna);
            }
        }

    }

    private void addJogada(Ordem ordem, Local local, JogadorTipo jogadorTipo) {
        Jogada jogada = new Jogada();
        jogada.setJogadorTipo(jogadorTipo);
        jogada.setLocalJogada(local);
        jogada.setOrdemJogada(ordem);
        jogadas.add(jogada);
    }

    @Override
    public void setTabuleiro(Tabuleiro tabuleiroReal) {
        this.tabuleiro = tabuleiroReal;
    }

    public void gameIsOver(byte vencedor) {
        if (vencedor == eu) {
            JogadaDAO.mergeJogo(Estado.GANHO, jogadas);
            JOptionPane.showMessageDialog(null, "MUHHUAHAHAHAHA!!!", "Computador diz...", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JogadaDAO.mergeJogo(Estado.PERDIDO, jogadas);
            JOptionPane.showMessageDialog(null, "Se aproveitam de minha nobreza...", "Computador diz...", JOptionPane.INFORMATION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/jogovelha/bitmaps/chapolin.png")));
        }
    }

    public void novoJogo() {
        jogadas = new ArrayList<Jogada>();
        contaJogadas = 0;
    }
}

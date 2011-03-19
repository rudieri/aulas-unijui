/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.marcacao;

/**
 *
 * @author rudieri
 */
public class Mensageiro {

    public final static int HORIZONTAL = 0;
    public final static int VERTICAL = 1;
    public final static int DIAGONAL_PRINCIPAL = 2;
    public final static int DIAGONAL_SECUNDARIA = -DIAGONAL_PRINCIPAL;
    private int direcao;
    public int idJogador;
    public Ponto cell1;
    public Ponto cell2;
    public Ponto cell3;

    public Mensageiro(int idJogador, Ponto cell1, Ponto cell2, Ponto cell3) {
        this.idJogador = idJogador;
        this.cell1 = cell1;
        this.cell2 = cell2;
        this.cell3 = cell3;
        if (cell1.linha == cell2.linha) {
            this.direcao = HORIZONTAL;
        }
        if (cell1.coluna == cell2.coluna) {
            this.direcao = VERTICAL;
        }
        if (cell1.coluna == cell1.linha && cell2.linha==cell2.coluna) {
            this.direcao = DIAGONAL_PRINCIPAL;
        }
        if ((cell1.linha) == cell3.coluna && cell1.coluna==cell3.linha) {
            this.direcao = DIAGONAL_SECUNDARIA;
        }

    }

    public int getDirecao() {
        return direcao;
    }
    
//    int colunaInicial;
//    int linhaInicial;
//    int colunaFinal;
//    int linhaFinal;
}

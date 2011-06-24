/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.tabuleiro;

/**
 *
 * @author rudieri
 */
public class NovoTabuleiro {

    private byte[][] matriz;
    private static final byte max = 3;

    public NovoTabuleiro() {
        matriz = new byte[max][max];
    }

    public void setNoLocal(byte linha, byte coluna, byte valor) {
        matriz[linha][coluna]=valor;
    }
    
    public byte toNumberFormat(byte linha, byte coluna){
        return (byte) (linha*max+coluna);
    }
}

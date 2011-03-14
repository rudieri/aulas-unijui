/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jogovelha.marcacao;

/**
 *
 * @author rudieri
 */
public class Ponto {
    public byte x;
    public byte y;

    public Ponto(byte x, byte y) {
        this.x = x;
        this.y = y;
    }
     public Ponto(int x, int y) {
        this.x = (byte) x;
        this.y = (byte) y;
    }
    
}

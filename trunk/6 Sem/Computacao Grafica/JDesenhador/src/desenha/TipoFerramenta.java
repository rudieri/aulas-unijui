/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desenha;

/**
 *
 * @author rudieri
 */
public class TipoFerramenta {

    public  static final byte POLIGONO_LIVRE = 0;
    public  static final byte RETANGULO = 1;
    public  static final byte CIRCULO = 2;
    public static final byte SELECAO=3;
    private byte ferramenta;

    public TipoFerramenta() {
        ferramenta=POLIGONO_LIVRE;
    }

    
    public void set(byte ferramenta) {
        this.ferramenta = ferramenta;
    }
    public byte get(){
        return ferramenta;
    }
}

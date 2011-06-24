/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.dados;

/**
 *
 * @author rudieri
 */
public enum JogadorTipo {
    HUMANO(-1),COMPUTADOR(1);
    
    private int id;

    private JogadorTipo(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.dados;

/**
 *
 * @author rudieri
 */
public enum Estado {
    GANHO(0),PERDIDO(1),EMPATADO(2),ABANDONADO(3);
    private int id;

    private Estado(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
}

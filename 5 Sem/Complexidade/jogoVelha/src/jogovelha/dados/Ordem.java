/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.dados;

/**
 *
 * @author rudieri
 */

public enum Ordem {
    J0(0),
    J1(1),
    J2(2),
    J3(3),
    J4(4),
    J5(5),
    J6(6),
    J7(7),
    J8(8);
    private int id;

    private Ordem(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    
}

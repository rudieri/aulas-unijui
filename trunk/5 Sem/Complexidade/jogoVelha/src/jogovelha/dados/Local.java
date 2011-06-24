/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.dados;

/**
 *
 * @author rudieri
 */
public enum Local {

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

    Local(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
    public int getId(){
        return id;
    }
    
    public static Local valueOf(Integer i){
        switch (i) {
            case 0:
                return J0;
            case 1:
                return J1;
            case 2:
                return J2;
            case 3:
                return J3;
            case 4:
                return J4;
            case 5:
                return J5;
            case 6:
                return J6;
            case 7:
                return J7;
            case 8:
                return J8;
            default:
                throw new AssertionError("A opção " + i + " esta fora da faixa: [0 , 8].");
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rudieri
 */
public class Teste {

    public static void main(String[] args) {
        boolean a = false, b = false, c = false, d = false;
        for (int i = 0; i < 16; i++) {
            System.out.println(to01(d) + "  " + to01(c) + "  " + to01(b) + "  " + to01(a) + "  = " + to01(calc(a, b, c, d)));
            d = c;
            c = b;
            b = a;
            a = !a;
        }
    }

    static int to01(boolean x) {
        return x ? 1 : 0;
    }

    static boolean calc(boolean a, boolean b, boolean c, boolean d) {
        return (!(a ^ b)) & !(a & !b | (!(!b | !d)) & c & !d | !(b & d)) | !a & b & !c & d;
    }
}

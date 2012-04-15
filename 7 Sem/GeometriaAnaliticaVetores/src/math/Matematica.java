package math;

import java.util.Scanner;
import utils.Ponto3D;
import utils.Vetor3D;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author rudieri
 */
public class Matematica {

    private Scanner scanner = new Scanner(System.in);

    public void bascara() throws Exception {
        System.out.print("\nA: ");
        int a = scanner.nextInt();
        System.out.print("\nB: ");
        int b = scanner.nextInt();
        System.out.print("\nC: ");
        int c = scanner.nextInt();
        double delta = Math.pow(b, 2) - (4 * a * c);
        if (delta < 0) {
            throw new Exception("Não tem raiz para delta: " + delta);
        }
        double rDelta = Math.pow(delta, 0.5);
        System.out.println("x': " + ((-b + rDelta) / (a * 2)));
        System.out.println("X'': " + ((-b - rDelta) / (a * 2)));
        System.out.println("= = = = = = = = =\n");
    }

    public static void main(String[] args) throws Exception {
       new Matematica().bascara();
       
        double escalar = 3;
        Ponto3D a = new Ponto3D(2, 1, -1);
        Ponto3D b = new Ponto3D(1, -1, 6);
//        Ponto3D c = new Ponto3D(-1, 2, 1);
        Vetor3D va = new Vetor3D(a);
        Vetor3D vb = new Vetor3D(b);
//        Vetor3D bc = new Vetor3D(b, c);
//        Vetor3D ac = new Vetor3D(a, c);
//        Vetor3D ca = new Vetor3D(c, a);
//        Vetor3D cb = new Vetor3D(c, b);
        System.out.println("^A "+Vetor3D.anguloInterno(vb, va));
//        System.out.println("^B "+Vetor3D.anguloInterno(ba, bc));
//        System.out.println("^C "+Vetor3D.anguloInterno(ca, cb));
        
    }
}

package com.geomanalitica.math;

import com.geomanalitica.utils.Formatacao;
import java.util.Scanner;
import com.geomanalitica.utils._3d.Ponto3D;
import com.geomanalitica.utils._3d.Vetor3D;

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
//       new Matematica().bascara();
//        if (true) {
//            System.exit(0);
//        }
        double escalar = 3;
//        Ponto3D a = new Ponto3D(1, 2, -1);
//        Ponto3D b = new Ponto3D(0, -1, 3);
//        Ponto3D c = new Ponto3D(-1, 2, 1);
//        Vetor3D va = new Vetor3D(Math.pow(3, 0.5)/4d, -1d/2d, 3d/4d);
//        Vetor3D vb = new Vetor3D(b);
        Vetor3D va = new Vetor3D(1, 4, -1);
        Vetor3D vb = new Vetor3D(3, 2, -1);
        Vetor3D vc = new Vetor3D(-1, 2, 1);
//        Vetor3D bc = new Vetor3D(b, c);
        System.out.println("ab: " + Vetor3D.anguloInternoPlanoReta(va, vb));
//        System.out.println("ac: " + vb);
//        System.out.println("bc: " + bc);
//        final double anguloInternoA = Vetor3D.anguloInterno(va, vb);
//        final double anguloInternoB = Vetor3D.anguloInterno(va.getInverso(), bc);
//        final double anguloInternoC = Vetor3D.anguloInterno(vb.getInverso(), bc.getInverso());
//        System.out.println("^A "+Formatacao.escreverGrausMinSeg(anguloInternoA));
//        System.out.println("^B "+Formatacao.escreverGrausMinSeg(anguloInternoB));
//        System.out.println("^C "+Formatacao.escreverGrausMinSeg(anguloInternoC));
        
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geomanalitica.utils;

/**
 *
 * @author rudieri
 */
public class Equacoes {

    private Equacoes() {
    }

    public static double[] segundoGrau(double a, double b, double c) throws Exception {
        double delta = Math.pow(b, 2) - (4 * a * c);
        if (delta < 0) {
            throw new Exception("Não tem raiz para delta: " + delta);
        }
        double rDelta = Math.pow(delta, 0.5);
        double[] raizes = new double[2];
        raizes[0] = ((-b + rDelta) / (a * 2));
        raizes[1] = ((-b - rDelta) / (a * 2));
        return raizes;
    }

    public static double primeiroGrau(double a, double b) {
        return -b / a;
    }
}

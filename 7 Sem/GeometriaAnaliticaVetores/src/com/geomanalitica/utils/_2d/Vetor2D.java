/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geomanalitica.utils._2d;

import com.geomanalitica.utils.MyMath;

/**
 *
 * @author rudieri
 */
public class Vetor2D {

    private double x;
    private double y;
    public static final Vetor2D VETOR_I = new Vetor2D(1, 0);
    public static final Vetor2D VETOR_J = new Vetor2D(0, 1);
    private final Ponto2D pa;
    private final Ponto2D pb;

    public Vetor2D(double x, double y) {
        this.x = x;
        this.y = y;
        pa = Ponto2D.ORIGEM;
        pb = new Ponto2D(x, y);
    }

    public Vetor2D(Ponto2D p) {
        this.x = p.x;
        this.y = p.y;
        pa = Ponto2D.ORIGEM;
        pb = p;
    }

    public Vetor2D(Ponto2D a, Ponto2D b) {
        this.x = b.x - a.x;
        this.y = b.y - a.y;
        pa = a;
        pb = b;
    }

    public double getModulo() {
        return Math.pow(Math.pow(x, 2) + Math.pow(y, 2), 0.5);
    }

    public Vetor2D getVersor() {
        return didivirEscalar(this, getModulo());
    }

    public double getAnguloDiretorX() {
        return MyMath.converterParaGraus(x / getModulo());
    }

    public double getAnguloDiretorY() {
        return MyMath.converterParaGraus(y / getModulo());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Ponto2D getPa() {
        return pa;
    }

    public Ponto2D getPb() {
        return pb;
    }
    

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    // static
    public static Vetor2D somar(Vetor2D a, Vetor2D b) {
        return new Vetor2D(a.x + b.x, a.y + b.y);
    }

    public static double produtoInternoOuEscalar(Vetor2D a, Vetor2D b) {
        return (a.x * b.x) + (a.y * b.y);
    }

    public static Vetor2D subtrair(Vetor2D a, Vetor2D b) {
        return new Vetor2D(a.x - b.x, a.y - b.y);
    }

    public static Vetor2D multiplicarEscalar(Vetor2D a, double escalar) {
        return new Vetor2D(a.x * escalar, a.y * escalar);
    }

    public static Vetor2D didivirEscalar(Vetor2D a, double escalar) {
        return new Vetor2D(a.x / escalar, a.y / escalar);
    }

    public static double anguloInterno(Vetor2D a, Vetor2D b) {
        return MyMath.converterParaGraus(Math.acos(produtoInternoOuEscalar(a, b) / (a.getModulo() * b.getModulo())));
    }

    public static boolean isParalelo(Vetor2D a, Vetor2D b) {
        return a.x / b.x == a.y / b.y;
    }

    public static boolean isOrtogonal(Vetor2D a, Vetor2D b) {
        return produtoInternoOuEscalar(a, b) == 0;
    }
}

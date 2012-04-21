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

    private float x;
    private float y;
    public static final Vetor2D VETOR_I = new Vetor2D(1, 0);
    public static final Vetor2D VETOR_J = new Vetor2D(0, 1);

    public Vetor2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vetor2D(Ponto2D p) {
        this.x = p.x;
        this.y = p.y;
    }

    public Vetor2D(Ponto2D a, Ponto2D b) {
        this.x = b.x - a.x;
        this.y = b.y - a.y;
    }

    public float getModulo() {
        return (float) Math.pow(Math.pow(x, 2) + Math.pow(y, 2), 0.5);
    }

    public Vetor2D getVersor() {
        return didivirEscalar(this, getModulo());
    }

    public float getAnguloDiretorX() {
        return (float) MyMath.converterParaGraus(x / getModulo());
    }

    public float getAnguloDiretorY() {
        return (float) MyMath.converterParaGraus(y / getModulo());
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }    

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    // static
    public static Vetor2D somar(Vetor2D a, Vetor2D b) {
        return new Vetor2D(a.x + b.x, a.y + b.y);
    }

    public static float produtoInternoOuEscalar(Vetor2D a, Vetor2D b) {
        return (a.x * b.x) + (a.y * b.y);
    }

    public static Vetor2D subtrair(Vetor2D a, Vetor2D b) {
        return new Vetor2D(a.x - b.x, a.y - b.y);
    }

    public static Vetor2D multiplicarEscalar(Vetor2D a, float escalar) {
        return new Vetor2D(a.x * escalar, a.y * escalar);
    }

    public static Vetor2D didivirEscalar(Vetor2D a, float escalar) {
        return new Vetor2D(a.x / escalar, a.y / escalar);
    }

    public static float anguloInterno(Vetor2D a, Vetor2D b) {
        return (float) MyMath.converterParaGraus(Math.acos(produtoInternoOuEscalar(a, b) / (a.getModulo() * b.getModulo())));
    }

    public static boolean isParalelo(Vetor2D a, Vetor2D b) {
        return a.x / b.x == a.y / b.y;
    }

    public static boolean isOrtogonal(Vetor2D a, Vetor2D b) {
        return produtoInternoOuEscalar(a, b) == 0;
    }
}

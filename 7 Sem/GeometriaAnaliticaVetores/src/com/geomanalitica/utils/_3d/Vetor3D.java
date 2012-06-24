/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geomanalitica.utils._3d;

/**
 *
 * @author rudieri
 */
public class Vetor3D {

    private static final Vetor3D CANONICAL_I = new Vetor3D(1, 0, 0);
    private static final Vetor3D CANONICAL_J = new Vetor3D(0, 1, 0);
    private static final Vetor3D CANONICAL_K = new Vetor3D(0, 0, 1);
    
    
    private double x;
    private double y;
    private double z;

    public Vetor3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vetor3D(Ponto3D p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    public Vetor3D(Ponto3D a, Ponto3D b) {
        this.x = b.x - a.x;
        this.y = b.y - a.y;
        this.z = b.z - a.z;
    }

    public double getModulo() {
        return Math.pow(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2), 0.5);
    }

    public Vetor3D getVersor() {
        return didivirEscalar(this, getModulo());
    }

    public Vetor3D getInverso() {
        return new Vetor3D(-x, -y, -z);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    // static
    public static Vetor3D somar(Vetor3D a, Vetor3D b) {
        return new Vetor3D(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public static double produtoInternoOuEscalar(Vetor3D a, Vetor3D b) {
        return (a.x * b.x) + (a.y * b.y) + (a.z * b.z);
    }

    public static double produtoMisto(Vetor3D a, Vetor3D b, Vetor3D c) {
        return produtoInternoOuEscalar(c, produtoVetorial(a, b));
    }
    public static Vetor3D produtoVetorial(Vetor3D a, Vetor3D b) {
        Vetor3D v1 = multiplicarEscalar(CANONICAL_I, a.y * b.z);
        Vetor3D v2 = multiplicarEscalar(CANONICAL_J, a.z * b.x);
        Vetor3D v3 = multiplicarEscalar(CANONICAL_K, a.x * b.y);
        Vetor3D diagonalPrincipal = somar(v3, somar(v1, v2));
        v1 = multiplicarEscalar(CANONICAL_K, a.y*b.x);
        v2 = multiplicarEscalar(CANONICAL_J, a.x*b.z);
        v3 = multiplicarEscalar(CANONICAL_I, a.z*b.y);
        Vetor3D diagonalSecundaria = somar(v3, somar(v1, v2));
//        return (a.y * b.z + a.z * b.x + a.x * b.y) - (a.y * b.x + a.z * b.y + a.x * b.z);
        return subtrair(diagonalPrincipal, diagonalSecundaria);
    }

    public static Vetor3D subtrair(Vetor3D a, Vetor3D b) {
        return new Vetor3D(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public static Vetor3D multiplicarEscalar(Vetor3D a, double escalar) {
        return new Vetor3D(a.x * escalar, a.y * escalar, a.z * escalar);
    }

    public static Vetor3D didivirEscalar(Vetor3D a, double escalar) {
        return new Vetor3D(a.x / escalar, a.y / escalar, a.z / escalar);
    }

    public static double anguloInterno(Vetor3D a, Vetor3D b) {
        return (Math.acos(produtoInternoOuEscalar(a, b) / (a.getModulo() * b.getModulo())) / Math.PI) * 180;
    }
    public static double anguloInternoPlanoReta(Vetor3D a, Vetor3D b) {
        return (Math.asin(produtoInternoOuEscalar(a, b) / (a.getModulo() * b.getModulo())) / Math.PI) * 180;
    }
}

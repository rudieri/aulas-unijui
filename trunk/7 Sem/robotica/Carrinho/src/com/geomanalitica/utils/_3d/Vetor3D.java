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

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    // static
    public static Vetor3D somar(Vetor3D a, Vetor3D b) {
        return new Vetor3D(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public static double produtoInternoOuEscalar(Vetor3D a, Vetor3D b) {
        return multiplicar(a, b);
    }

    public static double multiplicar(Vetor3D a, Vetor3D b) {
        return (a.x * b.x) + (a.y * b.y) + (a.z * b.z);
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
        return (Math.acos(multiplicar(a, b) / (a.getModulo() * b.getModulo()))/Math.PI)*180;
    }
}

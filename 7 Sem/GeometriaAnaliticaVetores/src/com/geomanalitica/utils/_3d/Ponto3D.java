/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geomanalitica.utils._3d;

/**
 *
 * @author rudieri
 */
public class Ponto3D {

    protected double x;
    protected double y;
    protected double z;

    public Ponto3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
    
    

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    public static Ponto3D multiplicarEscalar(Ponto3D p, double escalar) {
        return new Ponto3D(p.x * escalar, p.y * escalar, p.z * escalar);
    }
}

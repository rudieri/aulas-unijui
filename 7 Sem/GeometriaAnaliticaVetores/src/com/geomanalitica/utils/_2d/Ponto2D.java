/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geomanalitica.utils._2d;

/**
 *
 * @author rudieri
 */
public class Ponto2D {

    protected double x;
    protected double y;
    public static final Ponto2D ORIGEM = new Ponto2D(0, 0);
    
    public Ponto2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    public static Ponto2D multiplicarEscalar(Ponto2D p, double escalar) {
        return new Ponto2D(p.x * escalar, p.y * escalar);
    }

    public double getDistanciaEntreDoisPontos(Ponto2D pa, Ponto2D pb) {
        return Math.pow(Math.pow(pb.x - pa.y, 2) + Math.pow(pb.y - pa.y, 2), 0.5);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ponto2D) {
            return ((Ponto2D) obj).x == x && ((Ponto2D) obj).y == y;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }
    public Ponto2D criarNovaInstancia(){
        return new Ponto2D(x, y);
    }
}

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

    protected float x;
    protected float y;
    public static final Ponto2D ORIGEM = new Ponto2D(0, 0);
    
    public Ponto2D(float x, float y) {
        this.x = x;
        this.y = y;
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

    public static Ponto2D multiplicarEscalar(Ponto2D p, float escalar) {
        return new Ponto2D(p.x * escalar, p.y * escalar);
    }

    public float getDistanciaEntreDoisPontos(Ponto2D pa, Ponto2D pb) {
        return (float) Math.pow(Math.pow(pb.x - pa.y, 2) + Math.pow(pb.y - pa.y, 2), 0.5);
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
        hash = 13 * hash + Float.floatToIntBits(this.x);
        hash = 13 * hash + Float.floatToIntBits(this.y);
        return hash;
    }

    public Ponto2D criarNovaInstancia(){
        return new Ponto2D(x, y);
    }
}

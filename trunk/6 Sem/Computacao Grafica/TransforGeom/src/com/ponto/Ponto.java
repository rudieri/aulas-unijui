/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ponto;

/**
 *
 * @author rudieri
 */
public class Ponto {

    public int x;
    public int y;

    public Ponto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void subtrair(Ponto p) {
        this.x -= p.x;
        this.y -= p.y;
    }
    public void adicionar(Ponto p) {
        this.x += p.x;
        this.y += p.y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}

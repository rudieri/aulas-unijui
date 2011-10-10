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

    @Override
    public String toString() {
        return "["+x+", "+y+"]";
    }
    
}

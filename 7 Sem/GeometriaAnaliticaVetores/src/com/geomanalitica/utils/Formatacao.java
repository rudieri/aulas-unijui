/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geomanalitica.utils;

/**
 *
 * @author rudieri
 */
public class Formatacao {

    public static String escreverGrausMinSeg(double graus) {
        String gr = String.valueOf((long) graus);
        double minutos = MyMath.getMinutos(graus);
        String min = String.valueOf((long) minutos);
        String seg = String.valueOf((long) MyMath.getSegundos(minutos));
        return gr + "º " + min + "' " + seg + "'' ";
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geomanalitica.utils;

/**
 *
 * @author rudieri
 */
public class MyMath {

    private static long[] DECIMAIS;

    static {
        DECIMAIS = new long[10];
        for (int i = 0; i < 10; i++) {
            DECIMAIS[i] = (long) Math.pow(10, i);
        }
    }

    public static double converterParaGraus(double piRad) {
        return ((piRad / Math.PI) * 180);
    }

    public static double getMinutos(double graus) {
        return (graus - (long) graus) * 60;
    }
    public static double getMinutos(double graus, int casasDecimais) {
        return arredondar((graus - (long) graus) * 60, casasDecimais);
    }
    
    public static double getSegundos(double minutos) {
        return (minutos - (long) minutos) * 60;
    }
    public static double getSegundos(double minutos, int casasDecimais) {
        return arredondar((minutos - (long) minutos) * 60, casasDecimais);
    }

    public static double arredondar(double valor, int casasDecimais) {
        if (casasDecimais < DECIMAIS.length && casasDecimais >= 0) {
            return Math.round(valor * DECIMAIS[casasDecimais]);
        } else {
            throw new IllegalStateException("Número de casas deciamis deve estar entre [0 e 10].");
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aula.pid;

/**
 *
 * @author rogerio
 */
public class ExemploPID3 {

    public ExemploPID3(double p_factor, double i_factor, double d_factor) {
        p = p_factor;
        i = i_factor;
        d = d_factor;
        sumErrors = 0;
        previousValue = 0;
        System.out.println("STARTEO PID: P:" + p_factor + " I:" + i_factor + " D:" + d_factor);
    }

    public double controller(double setPoint, double processValue) {
        System.out.println("**************Calculo Maluco*************");
        double error = setPoint - processValue;
        System.out.println("error: " + error);
        System.out.println("sumErrors: " + sumErrors);
        double ret = p * (error) + i * sumErrors + d * (previousValue - processValue);

        previousValue = processValue;
        sumErrors += error;

        System.out.println("**************FIM  Calculo Maluco*************");

        return ret;
    }
    private volatile double p;
    private volatile double i;
    private volatile double d;
    private volatile double previousValue;
    private volatile double sumErrors;
}

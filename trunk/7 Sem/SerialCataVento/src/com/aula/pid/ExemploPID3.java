/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aula.pid;

import com.aula.catavento.JPID;

/**
 *
 * @author rogerio
 */
public class ExemploPID3 {
    private double error;
    private JPID jpid;
    private Double ideal = 512d;
    private boolean inercia = true;

    public ExemploPID3(double p_factor, double i_factor, double d_factor) {
        p = p_factor;
        i = i_factor;
        d = d_factor;
        sumErrors = 0;
        valorAnterior = 0;
        System.out.println("STARTEO PID: P:" + p_factor + " I:" + i_factor + " D:" + d_factor);
    }

    public double controller(double valorLido) {
        System.out.println("**************Calculo Maluco*************");
        error = ideal - valorLido;
        System.out.println("error: " + error);
        System.out.println("sumErrors: " + sumErrors);
        double auxP = p;
        if(inercia && error<0 ){
            auxP = 0;
        }
        
        double ret = auxP * (error) + i * sumErrors + d * (valorAnterior - valorLido);

        valorAnterior = valorLido;
        sumErrors += error;

        System.out.println("**************FIM  Calculo Maluco*************");
        
        //Atualiza Valores TELA
        if(jpid!=null){
            jpid.atualizaValores(valorLido,error,sumErrors,ret);
            jpid.addLog("PID: {p: "+p+" ,\n"
                    + "i: "+i+" ,\n"
                    + "d: "+d+" ,\n"
                    + "ideal: "+ideal+" ,\n"
                    + "Valor Lido: "+valorLido+" ,\n"
                    + "Soma Erros: "+sumErrors+" ,\n"
                    + "Calc: "+auxP+" * ("+error+") + "+i+" * "+sumErrors+" + "+d+" * ("+valorAnterior+" - "+valorLido+") ,\n"
                    + "Res: "+ret+"}");
        }

        return ret;
    }
    private volatile double p = 0.01;
    private volatile double i = 0.05;
    private volatile double d = 0.0;
    private volatile double valorAnterior;
    private volatile double sumErrors;

    public void setP(double p) {
        this.p = p;
    }

    public void setD(double d) {
        this.d = d;
    }

    public void setI(double i) {
        this.i = i;
    }

    public double getP() {
        return p;
    }

    public double getI() {
        return i;
    }

    public double getD() {
        return d;
    }
    
    public void setTela(JPID jpid){
        this.jpid = jpid;
    }

    public double getIdeal() {
        return ideal;
    }
    public void setIdeal(Double ideal) {
       this.ideal = ideal;
    }

    public void setInercia(boolean inercia) {
        this.inercia = inercia;
    }
    
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aula.catavento;

import com.aula.pid.ExemploPID3;

/**
 *
 * @author manchini
 */
public class Principal implements EventoListener {

    int ideal = 512;
    boolean temComando = false;
    String comando = "";
    int valor = 1500;
    ExemploPID3 pid3 = new ExemploPID3(1.0, 0.05, 1.0);
    private JPID jpiD;
    private Serial serial;

    @Override
    public void leuRetorno(String ret) {
        System.out.println("Veio " + ret);
        //[618]<1001>
        //[1000]<1002>
        String[] valores = ret.split("<");
        int posicap = new Integer(valores[0].substring(1, valores[0].length() - 1));
        System.out.println("Leu? " + posicap);
//        if(posicap>ideal){
//            valor-=2;
//        }else if(posicap<ideal){
//            valor+=2;
//        }
        valor = (int) pid3.controller(ideal, posicap);

    }

    @Override
    public boolean tenhoQueEscrever() {
        return temComando;
    }

    @Override
    public String comando() {

        temComando = false;
        return comando;
    }

    private void mostrarTelaParametros() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                jpiD = new JPID(pid3, serial);
                jpiD.setVisible(true);
            }
        }).start();
    }

    private void loop() {
        mostrarTelaParametros();
        if (serial == null) {
            serial = new Serial();
        }
        serial.start(this);


        while (true) {
            try {
                comando = "{" + valor + "}";
                temComando = true;

                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Principal().loop();
    }
}

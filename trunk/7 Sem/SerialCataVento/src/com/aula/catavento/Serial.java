package com.aula.catavento;

import com.aula.catavento.EscolhaPorta;
import com.aula.catavento.EventoListener;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Serial {

    public void start(final EventoListener evento) {
        try {
            EscolhaPorta escolhaPorta = new EscolhaPorta(null, true);
            escolhaPorta.setVisible(true);
            CommPortIdentifier porta = escolhaPorta.portId;
            CommPort commPort = porta.open("", 2000);

            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                InputStream in = serialPort.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                final BufferedReader br = new BufferedReader(reader);
                //
                OutputStream out = serialPort.getOutputStream();
                OutputStreamWriter write = new OutputStreamWriter(out);
                final BufferedWriter bw = new BufferedWriter(write);

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while (true) {
                            try {
                                if (evento.tenhoQueEscrever()) {
                                    String comando = evento.comando();
                                    System.out.println("Foi: " + comando);
                                    bw.write(comando);
                                    bw.flush();
                                }


                            } catch (Exception ex) {
                                Logger.getLogger(Serial.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }).start();


                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        StringBuilder aux = new StringBuilder(10);
                        boolean gravar = false;
                        while (true) {
                            try {
                                char c = (char) br.read();

                                if (c == '[') {
                                    gravar = true;
                                } else if (!gravar) {
                                    System.out.println(c);
                                }
                                if (c != '>' && gravar) {
                                    aux.append(c);
                                } else if(gravar){
                                    System.out.println(aux);
                                    evento.leuRetorno(aux.toString());
                                    aux.delete(0, aux.length());
                                    gravar = false;
                                }


                            } catch (Exception ex) {
                                Logger.getLogger(Serial.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }).start();



            }
        } catch (Exception ex) {
            Logger.getLogger(Serial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

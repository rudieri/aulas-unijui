package com.aula.professor;

import com.aula.catavento.Serial;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        try {
            EscolhaPorta escolhaPorta = new EscolhaPorta(null, true);
            escolhaPorta.setVisible(true);
            CommPortIdentifier porta= escolhaPorta.portId;
            CommPort commPort = porta.open("", 2000);

                if (commPort instanceof SerialPort) {
                    SerialPort serialPort = (SerialPort) commPort;
                    serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                    InputStream in =  serialPort.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    final BufferedReader br = new BufferedReader(reader);
                    //
                    OutputStream out = serialPort.getOutputStream();
                    OutputStreamWriter write = new OutputStreamWriter(out);
                    final BufferedWriter bw = new BufferedWriter(write);
                    
                    new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while(true){
                            try {
                                bw.write("a");
                            } catch (IOException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }).start();
                    
                    
                    new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while(true){
                            try {
                                System.out.println((char)br.read());
                                
                            } catch (IOException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }).start();
                    
                
            
        }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}

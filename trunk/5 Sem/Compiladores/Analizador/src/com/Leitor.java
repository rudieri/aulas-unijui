/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.FileUtils;

/**
 *
 * @author labinf
 */
public class Leitor {

    private int estadoInicial;
    private int numEstados;
    private int numEstadosFinal;
    private boolean[] mtEnd;
    private int esteEstado = -1;
    

    public Leitor() {
        try {
            StringBuffer leArquivo = FileUtils.leArquivo(new File("definicao.txt"));

            String[] linhas = leArquivo.toString().split("\n");
            estadoInicial = Integer.valueOf(linhas[0].trim());
            numEstados = Integer.valueOf(linhas[1].trim());
            int[][] mt = new int[numEstados][256];
            mtEnd = new boolean[numEstados];


            int i = 0;
            while (i + 2 < numEstados + 2) {
                String[] letras=linhas[i+2].split(" ");
                int l1=Integer.valueOf(letras[0]);
                char l2=letras[1].charAt(0);
                int l3 = Integer.valueOf(letras[2].trim());
                mt[l1][l2] = l3;
                i++;
            }
            int numEstadosFinais=Integer.valueOf(linhas[i + 2].trim());
            i++;
            int aux=i+2;
            while (i+2<aux+numEstadosFinais) {
                mtEnd[Integer.valueOf(linhas[i+2].trim())]=true;
                i++;
            }
            numEstadosFinal = linhas[numEstados + 1].charAt(0);



            Scanner scanner = new Scanner(System.in);
            char ch;
            esteEstado=estadoInicial;
            String lido;
            while (!(lido = scanner.next()).equals("ok")) {
                ch=lido.trim().charAt(0);
                esteEstado = mt[esteEstado][ch];
                //System.out.println((int)ch);

            }
            if (mtEnd[esteEstado]) {
                System.out.println("Uahhaajjja");
            } else {
                System.out.println("WTF?");
            }

        } catch (Exception ex) {
            Logger.getLogger(Leitor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void main(String[] args) {
        Leitor leitor = new Leitor();
    }
}

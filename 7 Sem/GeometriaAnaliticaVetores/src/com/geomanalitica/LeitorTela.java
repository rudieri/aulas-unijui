package com.geomanalitica;


import com.geomanalitica.utils.Vetorizador;
import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import com.geomanalitica.utils._2d.Ponto2D;
import com.geomanalitica.utils._2d.Vetor2D;
import com.geomanalitica.utils._3d.Ponto3D;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author rudieri
 */
public class LeitorTela {

    static final JFileChooser jFileChooser = new JFileChooser();
    static final JFrame jFrame = new JFrame("...");
    private static String linha;
    private static int linhaAtual;

    public static void desenharRota(ArrayList<Ponto2D> pontos) {
        //        }
                for (int index = 0; index < pontos.size()-1; index++) {
                    Ponto2D ponto2D = pontos.get(index);
        //            System.out.println(ponto2D);
        //            if (i - 2 == pontos.size()) {
        //                return;
        //            }
                    Ponto2D proxPonto = pontos.get(index+1);
                    final Graphics graphics = jFrame.getGraphics();
                    graphics.setColor(Color.RED);
                    graphics.drawLine((int) (ponto2D.getX() * escala), (int) (ponto2D.getY() * escala), (int) (proxPonto.getX() * escala), (int) (proxPonto.getY() * escala));
        //            graphics.drawArc((int) (ponto2D.getX() * escala), (int) (ponto2D.getY() * escala), (int) (proxPonto.getX() * escala), (int) (proxPonto.getY() * escala), 1, (int)new Vetor2D(ponto2D, proxPonto).getModulo());
                }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
//        int r = jFileChooser.showOpenDialog(null);
//        if (r != JFileChooser.APPROVE_OPTION) {
//            System.exit(0);
//        }
        jFrame.setSize(640, 500);
        jFrame.setLocation(500, 0);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
//        File file = jFileChooser.getSelectedFile();
        File file = new File("/home/rudieri/Área de trabalho/my_matrix");
//        File file = new File("/home/rudieri/Área de trabalho/matrix_o_1_v2");
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        int rows = 0;
        int cols;
        byte[][] tela = null;
        Thread.sleep(1000);
        while ((linha = bufferedReader.readLine()) != null) {
            if (linha.charAt(0) == '#') {
                System.out.println("it's #");
                String[] split = linha.split(": ");
                if (linha.contains("Rows")) {
                    rows = Integer.parseInt(split[1]);
                    tela = new byte[rows][];
                } else {
                    cols = Integer.parseInt(split[1]);
                }
            } else {
                String[] colunas = linha.split(";");
                System.out.println("Cols: " + colunas.length + " row: " + linhaAtual);
                tela[linhaAtual] = new byte[colunas.length];
                if (colunas.length == 0) {
                    throw new IllegalStateException("Sem colunas... na linha: " + linhaAtual);
                }
                for (int i = 0; i < colunas.length; i++) {
                    tela[linhaAtual][i] = Byte.parseByte(colunas[i]);
                }
                if (linhaAtual == rows - 1) {
                    System.out.println("++");
                    new RunnableImpl(tela).run();
                    Thread.sleep(14000);
                    linhaAtual = 0;
                } else {
                    linhaAtual++;
                }
            }
        }
        System.out.println("EOF");
        System.exit(0);
    }

    private static void pintarTela(final byte[][] vetor) {
        new Thread(new RunnableImpl(vetor)).start();
    }

    static class RunnableImpl implements Runnable {

        private final byte[][] vetor;
        static boolean estaEmUso = false;

        public RunnableImpl(byte[][] vetor) {
            this.vetor = vetor;
        }

        @Override
        public void run() {
            try {
                if (estaEmUso) {
                    System.out.println("Função ocupada, comandos ignorados...");
                    return;
                }
                estaEmUso = true;
                final Graphics graphics = jFrame.getGraphics();
                graphics.setColor(Color.lightGray);
                graphics.drawRect(0, 0, jFrame.getWidth(), jFrame.getHeight());
                for (int i = 0; i < vetor.length; i++) {
                    byte[] linhas = vetor[i];
                    if (linhas == null) {
                        continue;
                    }
                    for (int j = 0; j < linhas.length; j++) {
                        byte cor = linhas[j];
                        graphics.setColor(cor == 0 ? Color.black : Color.WHITE);
                        final int tamanho = escala < 1 ? 1 : (int) escala;
                        graphics.fillRect((int) (j * escala), (int) (i * escala), tamanho, tamanho);
                    }
                }
                estaEmUso = false;
                Thread.sleep(500);
                desenharRota(Vetorizador.vetorizar(vetor));
//                encontrarLinhasRun(vetor);
            } catch (InterruptedException ex) {
                Logger.getLogger(LeitorTela.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static Ponto2D encontrarLinhaProxima(byte[][] vetor, int i, int j, Ponto2D pFinal) {
        if (vetor[0] == null) {
            return pFinal;
        }
        final int width = vetor[0].length;
        int iAv = i + 1;
        if (iAv < vetor.length) {
            for (int k = j - 1; k < j + 2; k++) {
                if (k >= 0 && k < width) {
                    if (vetor[iAv][k] == 1) {
                        pFinal = new Ponto2D(k, iAv);
                        return encontrarLinhaProxima(vetor, iAv, k, pFinal);
                    }
                }
            }
            return pFinal;
        } else {
            return pFinal;
        }
    }
//    public static void encontrarLinhas(final byte[][] vetor) {
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                encontrarLinhasRun(vetor);
//            }
//        }).start();
//    }
    private static final double escala = 10;

    public static void encontrarLinhasRun(byte[][] vetor) {
        ArrayList<Ponto2D> finais = new ArrayList<>();
        for (int i = 0; i < vetor.length; i++) {
            byte[] linhaVet = vetor[i];
            if (linhaVet == null) {
                continue;
            }
//            ArrayList<Ponto2D> ve
            for (int j = 0; j < linhaVet.length; j++) {
                byte dado = linhaVet[j];
                if (dado == 1) {
                    Ponto2D pfinal = null;
                    Ponto2D pinicial = new Ponto2D(j, i);
                    pfinal = encontrarLinhaProxima(vetor, i, j, pfinal);
                    if (pfinal == null) {
                        continue;
                    }
                    Vetor2D vd = new Vetor2D(pfinal, pinicial);
                    if (vd.getModulo() > 10 && !finais.contains(pfinal)) {
                        final Graphics graphics = jFrame.getGraphics();
                        graphics.setColor(Color.orange);
                        graphics.drawLine((int) (pinicial.getX() * escala), (int) (pinicial.getY() * escala), (int) (pfinal.getX() * escala), (int) (pfinal.getY() * escala));
//                        int angulo = 0;

                        //                        graphics.drawArc((int) (pinicial.getX() * escala), (int) (pinicial.getY() * escala), (int) (pfinal.getX() * escala), (int) (pfinal.getY() * escala), 0, 0);
//                        System.out.println(vd.toString() + " Mod: " + vd.getModulo());
                        System.out.println("P inicio: " + pinicial);
                        System.out.println("P fim: " + pfinal);
                        finais.add(pfinal);
                    }
//                    if (Ponto2D) {
//                        
//                    }
                }
            }
        }
    }


}
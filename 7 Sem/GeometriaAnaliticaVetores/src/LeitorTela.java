
import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

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

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
//        int r = jFileChooser.showOpenDialog(null);
//        if (r != JFileChooser.APPROVE_OPTION) {
//            System.exit(0);
//        }
        jFrame.setSize(640, 500);
        jFrame.setLocation(500, 0);
        jFrame.setVisible(true);
//        File file = jFileChooser.getSelectedFile();
        File file = new File("/home/rudieri/Área de trabalho/matrix_o_1_v2");
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String linha;
        int rows = 0;
        int cols;
        byte[][] tela = null;
        int linhaAtual = 0;
        while ((linha = bufferedReader.readLine()) != null) {
            if (linha.charAt(0) == '#') {
                String[] split = linha.split(": ");
                if (linha.contains("Rows")) {
                    rows = Integer.parseInt(split[1]);
                    tela = new byte[rows][];
                } else {
                    cols = Integer.parseInt(split[1]);
                }
            } else {
                String[] colunas = linha.split(";");
                tela[linhaAtual] = new byte[colunas.length];
                if (colunas.length==0) {
                    throw new IllegalStateException("Sem colunas... na linha: " + linhaAtual);
                }
                for (int i = 0; i < colunas.length; i++) {
                    tela[linhaAtual][i] = Byte.parseByte(colunas[i]);
                }
                if (linhaAtual == rows - 1) {
                    System.out.println("++");
                    pintarTela(tela);
                    Thread.sleep(4000);
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
            if (estaEmUso) {
                return ;
            }
            estaEmUso = true;
            final Graphics graphics = jFrame.getGraphics();
            graphics.setColor(Color.lightGray);
            graphics.drawRect(0, 0, jFrame.getWidth(), jFrame.getHeight());
            for (int i = 0; i < vetor.length; i++) {
                byte[] linhas = vetor[i];
                if (linhas ==null) {
                    continue;
                }
                for (int j = 0; j < linhas.length; j++) {
                    byte cor = linhas[j];
                    graphics.setColor(cor == 0 ? Color.black : Color.WHITE);
                    graphics.drawRect(j, i, 1, 1);
                }
            }
            estaEmUso = false;
        }
    }
}

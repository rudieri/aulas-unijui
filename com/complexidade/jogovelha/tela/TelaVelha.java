package com.complexidade.jogovelha.tela;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import com.complexidade.jogovelha.ai.Jogador2;
import com.complexidade.jogovelha.marcacao.Marca;
import com.complexidade.jogovelha.marcacao.Mensageiro;
import com.complexidade.jogovelha.marcacao.Ponto;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TelaVelha.java
 *
 * Created on 05/03/2011, 08:58:22
 */
/**
 *
 * @author rudieri
 */
public class TelaVelha extends javax.swing.JDialog {

    private ImageIcon xis;
    private ImageIcon bolinha;
    private Marca pxis;
    private Marca pbol;
    public static final int JOGADOR_HUMANO = -1;
    public static final int JOGADOR_COMPUTADOR = 1;
    private int vezDeJogar;
    private Jogador2 jogadorPc;
    private int[][] tabuleiro;
    private int casasRestantes;

    /** Creates new form TelaVelha */
    public TelaVelha(java.awt.Frame parent, boolean modal) throws IOException {
        super(parent, modal);
        init();
    }

    public TelaVelha() throws IOException {
        init();
    }

    private void init() throws IOException {
        initComponents();
        BufferedImage fbx = ImageIO.read(getClass().getResource("../bitmaps/xis.png"));
        BufferedImage fbb = ImageIO.read(getClass().getResource("../bitmaps/bolinha.png"));
        int w = jTable1.getCellRect(0, 0, false).width;
        int h = jTable1.getCellRect(0, 0, false).height;
        xis = new ImageIcon(fbx.getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH));
        bolinha = new ImageIcon(fbb.getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH));
        jTable1.setDefaultRenderer(Object.class, new Render());
        pxis = new Marca(xis);
        pbol = new Marca(bolinha);
        vezDeJogar = JOGADOR_HUMANO;
        jogadorPc = new Jogador2(this);
        tabuleiro = new int[3][3];

        setVisible(true);
    }

    private void inicializaMatriz() {
        tabuleiro = new int[3][3];
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                tabuleiro[i][j] = 0;
            }
        }
        casasRestantes = 9;
    }

    public boolean jogar(int jogador, int linha, int coluna) {
        if (!estaLivre(linha, coluna)) {
            return false;
        }
        if (jogador == this.vezDeJogar) {
            if (jogador == JOGADOR_HUMANO) {
                setValue(pbol, linha, coluna);
                //  this.vezDeJogar = JOGADOR_COMPUTADOR;

            } else {
                setValue(pxis, linha, coluna);
                // this.vezDeJogar = JOGADOR_HUMANO;
            }
        }
        if (isGameOver()) {
            return true;
        }
        this.vezDeJogar = -this.vezDeJogar;
        jogadorPc.foiMarcado(new Ponto((byte) linha, (byte) coluna), -1 * this.vezDeJogar);
        return true;
    }

    public void setValue(Marca v, int linha, int coluna) {
        tabuleiro[linha][coluna] = this.vezDeJogar;
        jTable1.setValueAt(v, linha, coluna);
    }

    public boolean estaLivre(int linha, int coluna) {
        return tabuleiro[linha][coluna] == 0;
        //return jTable1.getValueAt(linha, coluna) == null;
    }

    private boolean  isGameOver() {

        int linhas;
        int colunas;

        int somaDiagonalPrincipal = 0;
        int somaDiagonalSecundaria = 0;
        int somaCols = 0;
        int somaLinhas = 0;
        Mensageiro m = null;
        for (linhas = 0; linhas < tabuleiro.length; linhas++) {
            somaCols = 0;
            somaLinhas = 0;
            somaDiagonalPrincipal+= tabuleiro[linhas][linhas];
            somaDiagonalSecundaria += tabuleiro[linhas][2 - linhas];
            for (colunas = 0; colunas < tabuleiro[linhas].length; colunas++) {
                somaCols += tabuleiro[linhas][colunas];
                somaLinhas += tabuleiro[colunas][linhas];
            }
            if (Math.abs(somaCols) == 3) {
                m = new Mensageiro(vezDeJogar, new Ponto(linhas, 0), new Ponto(linhas, 1), new Ponto(linhas, 2));
                break;
            }
            if (Math.abs(somaLinhas) == 3) {
                m = new Mensageiro(vezDeJogar, new Ponto(0, linhas), new Ponto(1, linhas), new Ponto(2, linhas));
                break;
            }
            if (Math.abs(somaDiagonalPrincipal)==3) {
                m= new Mensageiro(vezDeJogar, new Ponto(0,0), new Ponto(1,1), new Ponto(2,2));
                break;
            }
              if (Math.abs(somaDiagonalSecundaria)==3) {
              m= new Mensageiro(vezDeJogar, new Ponto(0,2), new Ponto(1,1), new Ponto(2,0));
                break;
            }
        }
        if (m != null) {
            gameOver(m);
            return true;
        }

        return false;
    }

    private void gameOver(Mensageiro elFin) {
        Rectangle cellRect = jTable1.getCellRect(elFin.cell1.linha, elFin.cell1.coluna, false);
        Rectangle cellRectF = jTable1.getCellRect(elFin.cell3.linha, elFin.cell3.coluna, false);
        pintarLinha(cellRect, cellRectF, elFin.getDirecao());
    }

    private void pintarLinha(Rectangle rectI, Rectangle rectF, int direcao) {
        int x1;
        int y1;
        int x2;
        int y2;
        switch (direcao) {
            case Mensageiro.HORIZONTAL:
                y1 = rectI.y + rectI.height / 2;
                x1 = rectI.x;
                x2 = rectF.x + rectF.width;
                y2 = y1;
                break;
            case Mensageiro.VERTICAL:
                x1 = rectI.x + rectI.width / 2;
                y1 = rectI.y;
                y2 = rectF.y + rectF.height;
                x2 = x1;
                break;
            case Mensageiro.DIAGONAL_PRINCIPAL:
                x1 = 0;
                y1 = 0;
                y2 = rectF.y + rectF.height;
                x2 = rectF.x + rectF.width;
                break;
            case Mensageiro.DIAGONAL_SECUNDARIA:
                x1 = rectI.x + rectI.width;
                y1 = 0;
                y2 = rectF.y + rectF.height;
                x2 = 0;
                break;
            default:
                throw new AssertionError();
        }
        jTable1.getGraphics().drawLine(x1, y1, x2, y2);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "", "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setRowHeight(48);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setResizable(false);
        jTable1.getColumnModel().getColumn(1).setResizable(false);
        jTable1.getColumnModel().getColumn(2).setResizable(false);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-242)/2, (screenSize.height-192)/2, 242, 192);
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int row = jTable1.rowAtPoint(evt.getPoint());
        int col = jTable1.columnAtPoint(evt.getPoint());
        jogar(JOGADOR_HUMANO, row, col);

    }//GEN-LAST:event_jTable1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    TelaVelha dialog = new TelaVelha(new javax.swing.JFrame(), true);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                        public void windowClosing(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    dialog.setVisible(true);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Não foi possível carregar as imagens!!!");
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

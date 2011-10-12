
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Fern.java
 *
 * Created on 12/10/2011, 13:58:00
 */
/**
 *
 * @author rudieri
 */
public class Fern extends javax.swing.JFrame {

    private int X;
    private int Y;
    private int difX;
    private int difY;
    private float escala;
    private static final float a1 = 0f;
    private static final float b1 = 0.16f;
    private static final float a2 = 0.2f;
    private static final float b2 = -0.26f;
    private static final float c2 = 0f;
    private static final float d2 = 0.23f;
    private static final float e2 = 0.22f;
    private static final float f2 = 1.6f;
    private static final float a3 = -0.15f;
    private static final float b3 = 0.28f;
    private static final float c3 = 0f;
    private static final float d3 = 0.26f;
    private static final float e3 = 0.25f;
    private static final float f3 = 0.44f;
    private static final float a4 = 0.85f;
    private static final float b4 = 0.04f;
    private static final float c4 = 0f;
    private static final float d4 = -0.04f;
    private static final float e4 = 0.85f;
    private static final float f4 = 1.6f;
    // Porcentagens
    private static final int perc1 = 1;
    private static final int perc2 = 8;
    private static final int perc3 = 15;
    private static final int perc4 = 85;
    private byte amplScroll = 2;
    private Long numIter;

    /** Creates new form Fern */
    public Fern() {
        initComponents();
        escala = 100;
        numIter = 500000l;
        X = getWidth() / 2;
        Y = getHeight();
        initListeners();
    }

    private void initListeners() {
        this.addWindowStateListener(new WindowAdapter() {

            @Override
            public void windowStateChanged(WindowEvent e) {
                super.windowStateChanged(e);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Fern.class.getName()).log(Level.SEVERE, null, ex);
                }
                X = getWidth() / 2;
                Y = getHeight();
                repaint();
            }
        });
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                difX = X - e.getX();
                difY = Y - e.getY();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    jPopupMenu1.show(Fern.this, e.getX(), e.getY());
                }
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {

            private int count;

            @Override
            public void mouseDragged(MouseEvent e) {
                X = e.getX() + difX;
                Y = e.getY() + difY;
                count++;
                super.mouseDragged(e);
                if (count > 5) {
                    count = 0;
                    repaint();
                }
            }
        });
        this.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation()<0) {
                    escala*=amplScroll;
                }else{
                    escala/=amplScroll;
                }
//                escala -= e.getWheelRotation() * amplScroll;
                repaint();
            }
        });
        jPopupMenu1.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                repaint();
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
    }

    @Override
    public void paint(Graphics g) {
//        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        geraFern(g, numIter.intValue(), X, Y, escala, Color.green);
    }

    private void geraFern(Graphics g, long iIterations, int xInicio, int xFim, float dScale, Color cor) {

        int random;
        float x = 0;
        float y = 0;
        float novoX = 0;
        float novoY = 0;
//        g.setColor(Color.green);
        for (long i = 0; i < iIterations; i++) {

            // porcentagens de vezes que certa fórmula é usada
            random = (int) (Math.random() * 100);
            if (random < perc1) {
                novoX = a1;
                novoY = b1 * y;
                g.setColor(Color.white);

            } else if (random < perc2) {
                novoX = (a2 * x) + (b2 * y) + c2;
                novoY = (d2 * x) + (e2 * y) + f2;
                g.setColor(Color.PINK);
            } else if (random < perc3) {
                novoX = (a3 * x) + (b3 * y) + c3;
                novoY = (d3 * x) + (e3 * y) + f3;
                g.setColor(Color.red);
            } else {
                novoX = (a4 * x) + (b4 * y) + c4;
                novoY = (d4 * x) + (e4 * y) + f4;
                g.setColor(cor);
            }
            x = novoX;
            y = novoY;
            g.fillRect(Math.round(xInicio + (novoX * dScale)), Math.round(xFim - (novoY * dScale)), 1, 1);
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem_Iteracoes = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jRadioButtonMenuItem_2X = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem_4X = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem_8X = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem_16X = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem_32X = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem_64X = new javax.swing.JRadioButtonMenuItem();

        jMenuItem_Iteracoes.setText("Iteracoes");
        jMenuItem_Iteracoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_IteracoesActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem_Iteracoes);

        jMenu1.setText("Amplificacao x Scroll");

        buttonGroup1.add(jRadioButtonMenuItem_2X);
        jRadioButtonMenuItem_2X.setText("2X");
        jRadioButtonMenuItem_2X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem_2XActionPerformed(evt);
            }
        });
        jMenu1.add(jRadioButtonMenuItem_2X);

        buttonGroup1.add(jRadioButtonMenuItem_4X);
        jRadioButtonMenuItem_4X.setText("4X");
        jRadioButtonMenuItem_4X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem_4XActionPerformed(evt);
            }
        });
        jMenu1.add(jRadioButtonMenuItem_4X);

        buttonGroup1.add(jRadioButtonMenuItem_8X);
        jRadioButtonMenuItem_8X.setText("8X");
        jRadioButtonMenuItem_8X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem_8XActionPerformed(evt);
            }
        });
        jMenu1.add(jRadioButtonMenuItem_8X);

        buttonGroup1.add(jRadioButtonMenuItem_16X);
        jRadioButtonMenuItem_16X.setText("16X");
        jRadioButtonMenuItem_16X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem_16XActionPerformed(evt);
            }
        });
        jMenu1.add(jRadioButtonMenuItem_16X);

        buttonGroup1.add(jRadioButtonMenuItem_32X);
        jRadioButtonMenuItem_32X.setText("32X");
        jRadioButtonMenuItem_32X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem_32XActionPerformed(evt);
            }
        });
        jMenu1.add(jRadioButtonMenuItem_32X);

        buttonGroup1.add(jRadioButtonMenuItem_64X);
        jRadioButtonMenuItem_64X.setText("64X");
        jRadioButtonMenuItem_64X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem_64XActionPerformed(evt);
            }
        });
        jMenu1.add(jRadioButtonMenuItem_64X);

        jPopupMenu1.add(jMenu1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButtonMenuItem_2XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem_2XActionPerformed
        amplScroll = 2;
    }//GEN-LAST:event_jRadioButtonMenuItem_2XActionPerformed

    private void jRadioButtonMenuItem_4XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem_4XActionPerformed
        amplScroll = 4;
    }//GEN-LAST:event_jRadioButtonMenuItem_4XActionPerformed

    private void jRadioButtonMenuItem_8XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem_8XActionPerformed
        amplScroll = 8;
    }//GEN-LAST:event_jRadioButtonMenuItem_8XActionPerformed

    private void jRadioButtonMenuItem_16XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem_16XActionPerformed
        amplScroll = 16;
    }//GEN-LAST:event_jRadioButtonMenuItem_16XActionPerformed

    private void jRadioButtonMenuItem_32XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem_32XActionPerformed
        amplScroll = 32;
    }//GEN-LAST:event_jRadioButtonMenuItem_32XActionPerformed

    private void jRadioButtonMenuItem_64XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem_64XActionPerformed
        amplScroll = 64;
    }//GEN-LAST:event_jRadioButtonMenuItem_64XActionPerformed

    private void jMenuItem_IteracoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_IteracoesActionPerformed
        numIter = Long.valueOf(JOptionPane.showInputDialog(this, "Informe o número de iterações que serão executadas", numIter));
        repaint();
    }//GEN-LAST:event_jMenuItem_IteracoesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Fern.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fern.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fern.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fern.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Fern().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem_Iteracoes;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem_16X;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem_2X;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem_32X;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem_4X;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem_64X;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem_8X;
    // End of variables declaration//GEN-END:variables
}


import java.awt.Color;
import java.awt.Cursor;
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
    private float amplScroll = 2;
    private Long numIter;
    private boolean autoRepaint;
    private boolean estaMovendo;
    private final Cursor moveCursor;
    private final Cursor waitCursor;
    private final Cursor defaultCursor;

    /** Creates new form Fern */
    public Fern() {
        moveCursor = new Cursor(Cursor.MOVE_CURSOR);
        waitCursor = new Cursor(Cursor.WAIT_CURSOR);
        defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        initComponents();
        autoRepaint = false;
        estaMovendo=false;
        jCheckBoxMenuItem_AutoRepaint.setSelected(autoRepaint);
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
            public void mouseReleased(MouseEvent e) {
                if (estaMovendo) {
                    estaMovendo=false;
                    repaint();
                }
            }
            

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    setCursor(defaultCursor);
                    jPopupMenu1.show(Fern.this, e.getX(), e.getY());
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    repaint();
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
                    estaMovendo = true;
                    count = 0;
                    repaint();
                }
            }
        });
        this.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    escala *= amplScroll;
                } else {
                    escala /= amplScroll;
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
                if (autoRepaint) {
                    repaint();
                }
                setCursor(moveCursor);
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        this.setCursor(waitCursor);
        if (estaMovendo) {
            geraFern(g, 9000);
        } else {
            geraFern(g, numIter.intValue());
        }
        this.setCursor(moveCursor);
    }

    private void geraFern(Graphics g, long iteracoes) {

        float x = 0;
        float y = 0;
        float novoX = 0;
        float novoY = 0;

        for (long i = 0; i < iteracoes; i++) {

            // porcentagens de vezes que certa fórmula é usada
            byte random = (byte) (Math.random() * 100);
            if (random < 1) {
                //Caule 0 - 1
                novoX = 0f;
                novoY = 0.16f * y;
                g.setColor(Color.white);
            } else if (random < 8) {
                //Lado esquero 1 - 8 
                novoX = (0.2f * x) + (-0.26f * y);
                novoY = (0.23f * x) + (0.22f * y) + 1.6f;
                g.setColor(Color.PINK);
            } else if (random < 15) {
                // Lado direito 8 - 15
                novoX = (-0.15f * x) + (0.28f * y);
                novoY = (0.26f * x) + (0.24f * y) + 0.44f;
                g.setColor(Color.red);
            } else {
                // Toda a parte verde 15 - 100
                novoX = (0.85f * x) + (0.04f * y);
                novoY = (-0.004f * x) + (0.85f * y) + 1.6f;
                g.setColor(Color.green);
            }
            x = novoX;
            y = novoY;
            g.fillRect((int) (X + (novoX * escala)), (int) (Y - (novoY * escala)), 1, 1);
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
        jCheckBoxMenuItem_AutoRepaint = new javax.swing.JCheckBoxMenuItem();
        jMenuItem_Iteracoes = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jRadioButtonMenuItem_2X = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem_4X = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem_8X = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem_1_01X = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem_1_1X = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem_1_4X = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem_1_8X = new javax.swing.JRadioButtonMenuItem();

        jCheckBoxMenuItem_AutoRepaint.setSelected(true);
        jCheckBoxMenuItem_AutoRepaint.setText("Repaint ao selecionar itens dos Menus");
        jCheckBoxMenuItem_AutoRepaint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem_AutoRepaintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jCheckBoxMenuItem_AutoRepaint);

        jMenuItem_Iteracoes.setText("Iteracoes");
        jMenuItem_Iteracoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_IteracoesActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem_Iteracoes);

        jMenu1.setText("Amplificacao x Scroll");

        buttonGroup1.add(jRadioButtonMenuItem_2X);
        jRadioButtonMenuItem_2X.setSelected(true);
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

        buttonGroup1.add(jRadioButtonMenuItem_1_01X);
        jRadioButtonMenuItem_1_01X.setText("0,01X");
        jRadioButtonMenuItem_1_01X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem_1_01XActionPerformed(evt);
            }
        });
        jMenu1.add(jRadioButtonMenuItem_1_01X);

        buttonGroup1.add(jRadioButtonMenuItem_1_1X);
        jRadioButtonMenuItem_1_1X.setText("1,1X");
        jRadioButtonMenuItem_1_1X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem_1_1XActionPerformed(evt);
            }
        });
        jMenu1.add(jRadioButtonMenuItem_1_1X);

        buttonGroup1.add(jRadioButtonMenuItem_1_4X);
        jRadioButtonMenuItem_1_4X.setText("1,4X");
        jRadioButtonMenuItem_1_4X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem_1_4XActionPerformed(evt);
            }
        });
        jMenu1.add(jRadioButtonMenuItem_1_4X);

        buttonGroup1.add(jRadioButtonMenuItem_1_8X);
        jRadioButtonMenuItem_1_8X.setText("1,8X");
        jRadioButtonMenuItem_1_8X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem_1_8XActionPerformed(evt);
            }
        });
        jMenu1.add(jRadioButtonMenuItem_1_8X);

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

    private void jRadioButtonMenuItem_1_1XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem_1_1XActionPerformed
        amplScroll = 1.1f;
    }//GEN-LAST:event_jRadioButtonMenuItem_1_1XActionPerformed

    private void jRadioButtonMenuItem_1_4XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem_1_4XActionPerformed
        amplScroll = 1.4f;
    }//GEN-LAST:event_jRadioButtonMenuItem_1_4XActionPerformed

    private void jRadioButtonMenuItem_1_8XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem_1_8XActionPerformed
        amplScroll = 1.8f;
    }//GEN-LAST:event_jRadioButtonMenuItem_1_8XActionPerformed

    private void jMenuItem_IteracoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_IteracoesActionPerformed
        numIter = Long.valueOf(JOptionPane.showInputDialog(this, "Informe o número de iterações que serão executadas", numIter));
        repaint();
    }//GEN-LAST:event_jMenuItem_IteracoesActionPerformed

    private void jRadioButtonMenuItem_1_01XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem_1_01XActionPerformed
        amplScroll = 1.01f;
    }//GEN-LAST:event_jRadioButtonMenuItem_1_01XActionPerformed

    private void jCheckBoxMenuItem_AutoRepaintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem_AutoRepaintActionPerformed
        autoRepaint = jCheckBoxMenuItem_AutoRepaint.isSelected();
    }//GEN-LAST:event_jCheckBoxMenuItem_AutoRepaintActionPerformed

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
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem_AutoRepaint;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem_Iteracoes;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem_1_01X;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem_1_1X;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem_1_4X;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem_1_8X;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem_2X;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem_4X;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem_8X;
    // End of variables declaration//GEN-END:variables
}

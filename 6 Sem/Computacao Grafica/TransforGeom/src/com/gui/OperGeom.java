/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OperGeom.java
 *
 * Created on 07/10/2011, 19:59:30
 */
package com.gui;

import com.ferramentas.FerramentaListener;
import com.ferramentas.TipoFerramenta;
import com.ponto.Ponto;
import com.ponto.Pontos;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 *
 * @author rudieri
 */
public class OperGeom extends javax.swing.JDialog {

    private Pontos selecionado;
    private ArrayList<Pontos> container;
    private TipoFerramenta tipoFerramenta;
    private int oldX;
    private int oldY;
    private int difX;
    private int difY;

    /** Creates new form OperGeom */
    public OperGeom(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        container = new ArrayList<Pontos>();
        Pontos desenho = new Pontos(100, 200);
        desenho.addPoint(-50, -50);
        desenho.addPoint(-50, 50);
        desenho.addPoint(50, 50);
        container.add(desenho);
        Pontos desenho2 = (Pontos) desenho.clone();
        desenho2.setLocation(300, 200);
        container.add(desenho2);
        tipoFerramenta = new TipoFerramenta();
        jPopupMenu1.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                //nada
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                repaint();
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                repaint();
            }
        });
        initListeners();
    }

    //<editor-fold defaultstate="collapsed" desc="Inicializa os ouvintes... Tem código aqui... Não apague!!!">
    private void initListeners() {
        MouseAdapter adapter = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
                

                switch (e.getButton()) {
                    case 1:
                        switch (tipoFerramenta.get()) {
                            case TipoFerramenta.POLIGONO_LIVRE:
                                poligonoLivrePressed(e.getX(), e.getY());
                            case TipoFerramenta.CIRCULO:
                                //                        circloPressed(e.getX(), e.getY());
                                break;
                            case TipoFerramenta.RETANGULO:
                                //                        retanguloPressed(e.getX(), e.getY());
                                break;
                            case TipoFerramenta.SELECAO:

                                boolean sucesso = selecionar(e);
                                if (sucesso) {
                                    desenhoFoiSelecionado();
                                    setControlesEnabled(true);
                                    difX=selecionado.getLocation().x-e.getX();
                                    difY=selecionado.getLocation().y-e.getY();
                                } else {
                                    setControlesEnabled(false);
                                }
                                break;
                        }
                        break;
                    case 2:
                        switch (tipoFerramenta.get()) {
                            case TipoFerramenta.SELECAO:
                                boolean sucesso = selecionar(e);
                                if (sucesso) {
                                    desenhoFoiSelecionado();
                                    setControlesEnabled(true);
                                    selecionado.centralizar();
                                } else {
                                    setControlesEnabled(false);
                                }
                                break;
                        }
                        break;
                    case 3:

                        break;
                }

            }

            private boolean selecionar(MouseEvent e) {
                if (selecionado != null) {
                    selecionado.setSelecionado(false);
                }
                selecionado = getDesenhoAtPoin(e.getPoint());
                if (selecionado != null) {
                    selecionado.setSelecionado(true);
                    repaint();
                    return true;
                }
                return false;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                switch (e.getButton()) {
                    case 1:
                        switch (tipoFerramenta.get()) {
                            case TipoFerramenta.POLIGONO_LIVRE:
                                poligonoLivreReleased(e.getX(), e.getY());
                                break;
                            case TipoFerramenta.CIRCULO:
                                //                        circloReleased(e.getX(), e.getY());
                                break;
                            case TipoFerramenta.RETANGULO:
                                //                        retanguloReleased(e.getX(), e.getY());
                                break;
                            case TipoFerramenta.SELECAO:
                                //                        selecaoReleased(e);
                                break;
                        }
                        break;
                    case 2:

                        break;
                    case 3:
                        jPopupMenu1.show(OperGeom.this, e.getX(), e.getY());
                        break;
                }
            }
        };

        MouseMotionAdapter mma = new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                switch (tipoFerramenta.get()) {
                    case TipoFerramenta.POLIGONO_LIVRE:
                        break;
                    case TipoFerramenta.CIRCULO:
                        break;
                    case TipoFerramenta.RETANGULO:
                        break;
                    case TipoFerramenta.SELECAO:
                        //                        selecaoMoved(e);
                        break;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                switch (e.getButton()) {
                    case 0:
                    case 1:
                        switch (tipoFerramenta.get()) {
                            case TipoFerramenta.POLIGONO_LIVRE:
                                break;
                            case TipoFerramenta.CIRCULO:
                                break;
                            case TipoFerramenta.RETANGULO:
                                break;
                            case TipoFerramenta.SELECAO:
//                System.out.println("DRAG: "+ e.getButton());
                                if (selecionado != null) {
                                    selecionado.setLocation( e.getX()+difX,e.getY()+difY);
                                    repaint();
                                }
                                break;
                        }
                        break;
                    case 2:
                        switch (tipoFerramenta.get()) {
                            case TipoFerramenta.SELECAO:

                                break;
                        }
                        break;
                    case 3:

                        break;
                }

            }
        };
        addMouseMotionListener(mma);

        FerramentaListener ferrList = new FerramentaListener() {

            @Override
            public void ferramentaMudou(byte antiga, byte nova) {
                removerSelecao();
                String nome = null;
                switch (nova) {
                    case TipoFerramenta.SELECAO:
                        nome = "Seleção";
                        break;
                    case TipoFerramenta.CIRCULO:
                        nome = "Circulo";
                        break;
                    case TipoFerramenta.POLIGONO_LIVRE:
                        nome = "Poligono";
                        break;
                    case TipoFerramenta.RETANGULO:
                        nome = "Retângulo";
                        break;
                    case TipoFerramenta.SUB_SELECAO:
                        nome = "Mudar Centro";
                        break;
                }
                jLabel_Ferramenta.setText(nome);
            }
        };
        tipoFerramenta.addListener(ferrList);
        addMouseListener(adapter);
    }
    //</editor-fold>

    public void removerSelecao() {
        if (selecionado != null) {
            selecionado.setSelecionado(false);
            selecionado = null;
        }
        repaint();
    }

    public void setControlesEnabled(boolean b) {
        jSpinner_Rotacao.setEnabled(b);
        jSpinner_X.setEnabled(b);
        jSpinner_Y.setEnabled(b);
        jSpinner_Escala.setEnabled(b);
    }

    private void desenhoFoiSelecionado() {
        jSpinner_Rotacao.setValue(selecionado.getRotacao());
        jSpinner_X.setValue(selecionado.getTransacao().x);
        jSpinner_Y.setValue(selecionado.getTransacao().y);
        jSpinner_Escala.setValue(selecionado.getEscala());
    }

    public void poligonoLivrePressed(int x, int y) {
        if (selecionado == null) {
            selecionado = new Pontos(x, y);
        }
    }

    public void poligonoLivreReleased(int x, int y) {
        //Verifica se não foi arrastado (3 pixels de erro, na vdd são 6 para x e 6 para y)
        Ponto local = selecionado.getLocation();
        if (Math.abs(x - oldX) < 4 && Math.abs(y - oldY) < 4) {
            getGraphics().fillRect(x, y, 2, 2);
            Ponto ult = selecionado.getUltimo();
            if (ult != null) {
                getGraphics().drawLine(ult.x + local.x, ult.y + local.y, x, y);
            }
            selecionado.addPoint(x - local.x, y - local.y);
//            selecionado.addPoint(x, y);
        }
        if (selecionado.isEqualsStart(x - local.x, y - local.y)) {
            container.add(selecionado);
            removerSelecao();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < container.size(); i++) {
            g.setColor(Color.red);
            Pontos d = container.get(i);
            if (d.getSize() >= 0) {
                g.drawPolygon(d.getXs(), d.getYs(), d.getSize());
            }
            if (d.isSelecionado()) {
                g.setColor(Color.white);
                g.fillOval(d.getCentro().x - 2, d.getCentro().y - 2, 4, 4);
                g.setColor(Color.black);
                g.drawOval(d.getCentro().x - 3, d.getCentro().y - 3, 6, 6);
            }

        }
    }

    public Pontos getDesenhoAtPoin(Point p) {
        for (int i = 0; i < container.size(); i++) {
            Pontos pontos1 = container.get(i);
            if (pontos1.hit(p)) {
                return pontos1;
            }
        }
        return null;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenu_Ferramentas = new javax.swing.JMenu();
        jMenuItem_FerSelecao = new javax.swing.JMenuItem();
        jMenuItem_FerPoligono = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSpinner_X = new javax.swing.JSpinner();
        jSpinner_Y = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSpinner_Rotacao = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSpinner_Escala = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel_Ferramenta = new javax.swing.JLabel();

        jMenu_Ferramentas.setText("Ferramentas");

        jMenuItem_FerSelecao.setText("Seleção");
        jMenuItem_FerSelecao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_FerSelecaoActionPerformed(evt);
            }
        });
        jMenu_Ferramentas.add(jMenuItem_FerSelecao);

        jMenuItem_FerPoligono.setText("Poligono");
        jMenuItem_FerPoligono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_FerPoligonoActionPerformed(evt);
            }
        });
        jMenu_Ferramentas.add(jMenuItem_FerPoligono);

        jPopupMenu1.add(jMenu_Ferramentas);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        getContentPane().setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setText("Translação: ");
        jPanel1.add(jLabel1);

        jSpinner_X.setModel(new javax.swing.SpinnerNumberModel());
        jSpinner_X.setPreferredSize(new java.awt.Dimension(70, 28));
        jSpinner_X.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner_XStateChanged(evt);
            }
        });
        jPanel1.add(jSpinner_X);

        jSpinner_Y.setModel(new javax.swing.SpinnerNumberModel());
        jSpinner_Y.setPreferredSize(new java.awt.Dimension(70, 28));
        jSpinner_Y.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner_YStateChanged(evt);
            }
        });
        jPanel1.add(jSpinner_Y);

        jLabel7.setPreferredSize(new java.awt.Dimension(20, 10));
        jPanel1.add(jLabel7);

        jLabel2.setText("Rotação:");
        jPanel1.add(jLabel2);

        jSpinner_Rotacao.setModel(new javax.swing.SpinnerNumberModel());
        jSpinner_Rotacao.setPreferredSize(new java.awt.Dimension(60, 28));
        jSpinner_Rotacao.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner_RotacaoStateChanged(evt);
            }
        });
        jPanel1.add(jSpinner_Rotacao);

        jLabel6.setPreferredSize(new java.awt.Dimension(20, 10));
        jPanel1.add(jLabel6);

        jLabel4.setText("Escala:");
        jPanel1.add(jLabel4);

        jSpinner_Escala.setModel(new javax.swing.SpinnerNumberModel(100, -800, 800, 1));
        jSpinner_Escala.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner_EscalaStateChanged(evt);
            }
        });
        jPanel1.add(jSpinner_Escala);

        jLabel5.setPreferredSize(new java.awt.Dimension(20, 10));
        jPanel1.add(jLabel5);

        jLabel3.setText("Ferramenta: ");
        jPanel1.add(jLabel3);

        jLabel_Ferramenta.setFont(new java.awt.Font("Ubuntu", 1, 15));
        jLabel_Ferramenta.setText("Seleção");
        jLabel_Ferramenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_FerramentaMouseClicked(evt);
            }
        });
        jPanel1.add(jLabel_Ferramenta);

        getContentPane().add(jPanel1);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-800)/2, (screenSize.height-600)/2, 800, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void jSpinner_XStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_XStateChanged
        if (selecionado == null) {
            return;
        }
        selecionado.setTranslacao((Integer) jSpinner_X.getValue(), (Integer) jSpinner_Y.getValue());
        this.repaint();
    }//GEN-LAST:event_jSpinner_XStateChanged

    private void jSpinner_YStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_YStateChanged
        if (selecionado == null) {
            return;
        }
        selecionado.setTranslacao((Integer) jSpinner_X.getValue(), (Integer) jSpinner_Y.getValue());
        this.repaint();
    }//GEN-LAST:event_jSpinner_YStateChanged

    private void jSpinner_RotacaoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_RotacaoStateChanged
        if (selecionado == null) {
            return;
        }
        int value = (Integer) jSpinner_Rotacao.getValue();
        if (value < 0) {
            value = 360 + (value % 360);
            jSpinner_Rotacao.setValue(value);
        }
        if (value > 359) {
            value = value % 360;
            jSpinner_Rotacao.setValue(value);
        }
        selecionado.setRotacao(value);
        this.repaint();
    }//GEN-LAST:event_jSpinner_RotacaoStateChanged

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        //TODO faça algo
    }//GEN-LAST:event_formMouseReleased

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
//        seleconado = getDesenhoAtPoin(evt.getPoint());
    }//GEN-LAST:event_formMousePressed

    private void jMenuItem_FerSelecaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_FerSelecaoActionPerformed
        tipoFerramenta.set(TipoFerramenta.SELECAO);
    }//GEN-LAST:event_jMenuItem_FerSelecaoActionPerformed

    private void jMenuItem_FerPoligonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_FerPoligonoActionPerformed
        tipoFerramenta.set(TipoFerramenta.POLIGONO_LIVRE);
    }//GEN-LAST:event_jMenuItem_FerPoligonoActionPerformed

    private void jSpinner_EscalaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_EscalaStateChanged
        if (selecionado == null) {
            return;
        }
        selecionado.setEscala((Integer) jSpinner_Escala.getValue());
        this.repaint();
    }//GEN-LAST:event_jSpinner_EscalaStateChanged

    private void jLabel_FerramentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_FerramentaMouseClicked
        jPopupMenu1.show(this, jLabel_Ferramenta.getX() + jLabel_Ferramenta.getWidth() / 2, jLabel_Ferramenta.getY() + jLabel_Ferramenta.getHeight() + 30);
    }//GEN-LAST:event_jLabel_FerramentaMouseClicked

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
            java.util.logging.Logger.getLogger(OperGeom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OperGeom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OperGeom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OperGeom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                OperGeom dialog = new OperGeom(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel_Ferramenta;
    private javax.swing.JMenuItem jMenuItem_FerPoligono;
    private javax.swing.JMenuItem jMenuItem_FerSelecao;
    private javax.swing.JMenu jMenu_Ferramentas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSpinner jSpinner_Escala;
    private javax.swing.JSpinner jSpinner_Rotacao;
    private javax.swing.JSpinner jSpinner_X;
    private javax.swing.JSpinner jSpinner_Y;
    // End of variables declaration//GEN-END:variables
}

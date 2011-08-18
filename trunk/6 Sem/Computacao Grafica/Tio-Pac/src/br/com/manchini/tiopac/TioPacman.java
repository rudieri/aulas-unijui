/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.manchini.tiopac;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Date;
import javax.swing.JPanel;

/**
 *
 * @author manchini
 */
public class TioPacman extends JPanel {

    boolean fechado = true;
    IndoPara indoPara = IndoPara.ESQUERDA;

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        paintS(grphcs);
    }

    public synchronized void paintS(Graphics grphcs) {
        grphcs.setColor(Color.black);
//        grphcs.clearRect(0, 0, this.getWidth(), this.getHeight());
        fechado = new Date().getTime() / 500 % 2 == 0;
        if (fechado) {
//            desenarAberto(grphcs,Color.black);
            desenarFechado(grphcs, Color.yellow);
        } else {
//             desenarFechado(grphcs,Color.black);
            desenarAberto(grphcs, Color.yellow);
        }
    }

    private void desenarAberto(Graphics g, Color cor) {
        g.setColor(cor);
        if (indoPara == IndoPara.DIREITA) {
            g.fillArc(0, 0, 50, 50, 30, 300);
        } else if (indoPara == IndoPara.ESQUERDA) {
            g.fillArc(0, 0, 50, 50, 210, 300);
        } else if (indoPara == IndoPara.BAIXO) {
            g.fillArc(0, 0, 50, 50, 300, 300);
        } else if (indoPara == IndoPara.CIMA) {
            g.fillArc(0, 0, 50, 50, 120, 300);
        }
    }

    private void desenarFechado(Graphics g, Color cor) {
        g.setColor(cor);
        g.fillArc(0, 0, 50, 50, 0, 360);
    }

    public void setIndoPara(IndoPara indoPara) {
        this.indoPara = indoPara;
    }
    
    
}

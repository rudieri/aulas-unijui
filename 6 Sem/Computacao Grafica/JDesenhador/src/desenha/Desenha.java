/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desenha;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Shape;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

/**
 *
 * @author rudieri
 */
public class Desenha extends JFrame {

    private Graphics g;
    private final Pontos pontos;
    private int oldX;
    private int oldY;
    private final TipoFerramenta tipoFerramenta;

    public Desenha() throws HeadlessException {
//        setPreferredSize(new Dimension(400, 400));
        setSize(500, 500);
        pontos = new Pontos();
        tipoFerramenta = new TipoFerramenta();
        setVisible(true);
    }

    public void preparar() {
        g = this.getGraphics();

        MouseAdapter adapter = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                switch (tipoFerramenta.get()) {
                    case TipoFerramenta.POLIGONO_LIVRE:
                        poligonoLivrePressed(e.getX(), e.getY());
                    case TipoFerramenta.CIRCULO:
                        circloPressed(e.getX(), e.getY());
                        break;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                switch (tipoFerramenta.get()) {
                    case TipoFerramenta.POLIGONO_LIVRE:
                        poligonoLivreReleased(e.getX(), e.getY());
                        break;
                    case TipoFerramenta.CIRCULO:
                        circloReleased(e.getX(), e.getY());
                        break;
                }
            }
        };
        KeyAdapter keyAdapter = new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case '1':
                        tipoFerramenta.set(TipoFerramenta.RETANGULO);
                        break;
                    case '2':
                        tipoFerramenta.set(TipoFerramenta.POLIGONO_LIVRE);
                        System.out.println("yeah");
                        break;
                    case '3':
                        tipoFerramenta.set(TipoFerramenta.CIRCULO);
                        break;
                }
            }
        };
        addMouseListener(adapter);
        addKeyListener(keyAdapter);
    }

    public static void main(String[] args) {
        Desenha desenha = new Desenha();
        desenha.preparar();
    }

    @Override
    public void paint(Graphics g) {
//        super.paint(g);
    }

    public void poligonoLivrePressed(int x, int y) {
        oldX = x;
        oldY = y;
    }

    public void poligonoLivreReleased(int x, int y) {
        if (Math.abs(x - oldX) < 2 && Math.abs(y - oldY) < 2) {
            pontos.addPoint(x, y);
            g.fillRect(x, y, 2, 2);
        }
        if (pontos.isEqualsStart(x, y)) {
            g.fillPolygon(pontos.getPointX(), pontos.getPointY(), pontos.getSize());
            pontos.clear();
        }
    }

    public void circloPressed(int x, int y) {
        oldX = x;
        oldY = y;
    }

    public void circloReleased(int x, int y) {
        int aux=oldX;
        if (x>oldX) {
            oldX=x;
            x=aux;
        }
        aux=oldY;
        if (y>oldY) {
            oldY=x;
            y=aux;
        }
        g.fillOval(oldX, oldY, oldX-x, oldY-y);
    }
}

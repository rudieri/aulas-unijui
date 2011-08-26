/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desenha;

import desenha.ponto.Ponto;
import desenha.ponto.Pontos;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 *
 * @author rudieri
 */
public class Desenha extends Canvas {

    private Graphics2D g;
    private Graphics2D back;
    private final Pontos pontos;
    private int oldX;
    private int oldY;
    private boolean mover;
    private final TipoFerramenta tipoFerramenta;
    private final Container parent;
    private Point originalLocation;

    public Desenha(Container parent) {
//        setPreferredSize(new Dimension(400, 400));
        setSize(500, 500);
        pontos = new Pontos();
        tipoFerramenta = new TipoFerramenta();
        setVisible(true);
        mover = false;
        this.parent = parent;
//        setOpaque(true);
        setBackground(Color.red);
//        setForeground(Color.red);
    }

    public void preparar() {
        g = (Graphics2D) this.getGraphics();
        setBackground(Color.white);
//        this.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });
        MouseAdapter adapter = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                switch (tipoFerramenta.get()) {
                    case TipoFerramenta.POLIGONO_LIVRE:
                        poligonoLivrePressed(e.getX(), e.getY());
                    case TipoFerramenta.CIRCULO:
                        circloPressed(e.getX(), e.getY());
                        break;
                    case TipoFerramenta.RETANGULO:
                        retanguloPressed(e.getX(), e.getY());
                        break;
                    case TipoFerramenta.SELECAO:
//                        selecaoPressed(e);
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
                    case TipoFerramenta.RETANGULO:
                        retanguloReleased(e.getX(), e.getY());
                        break;
                    case TipoFerramenta.SELECAO:
//                        selecaoReleased(e);
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
                switch (tipoFerramenta.get()) {
                    case TipoFerramenta.POLIGONO_LIVRE:
                        break;
                    case TipoFerramenta.CIRCULO:
                        break;
                    case TipoFerramenta.RETANGULO:
                        break;
//                    case TipoFerramenta.SELECAO:
//                        selecaoDraged(e);
//                        break;
                }
            }
        };
        addMouseMotionListener(mma);
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

    @Override
    public void paint(Graphics g) {
//        super.paint(g);
    }

    public void poligonoLivrePressed(int x, int y) {
        oldX = x;
        oldY = y;
    }

    public void poligonoLivreReleased(int x, int y) {
        //Verifica se não foi arrastado (3 pixels de erro, na vdd são 6 para x e 6 para y)
        if (Math.abs(x - oldX) < 4 && Math.abs(y - oldY) < 4) {
            g.fillRect(x, y, 2, 2);
            Ponto ult = pontos.getUltimo();
            if (ult != null) {
                g.drawLine(ult.x, ult.y, x, y);
            }
            pontos.addPoint(x, y);
        }
        if (pontos.isEqualsStart(x, y)) {
            int[][] toArray = pontos.toArray();
            g.fillPolygon(toArray[0], toArray[1], pontos.getSize());
            pontos.clear();
        }
    }

    public void circloPressed(int x, int y) {
        oldX = x;
        oldY = y;
    }

    public void retanguloPressed(int x, int y) {
        oldX = x;
        oldY = y;
    }

    public void circloReleased(int x, int y) {
        int aux = oldX;
        if (x < oldX) {
            oldX = x;
            x = aux;
        }
        aux = oldY;
        if (y < oldY) {
            oldY = y;
            y = aux;
        }
        g.fillOval(oldX, oldY, x - oldX, y - oldY);

    }

    public void retanguloReleased(int x, int y) {
        int aux = oldX;
        if (x < oldX) {
            oldX = x;
            x = aux;
        }
        aux = oldY;
        if (y < oldY) {
            oldY = y;
            y = aux;
        }
        g.fillRect(oldX, oldY, x - oldX, y - oldY);

    }

    public void setTipoFerramenta(byte tipo) {
        tipoFerramenta.set(tipo);
    }

    public byte getTipoFerramenta() {
        return tipoFerramenta.get();
    }
    //
    //<editor-fold defaultstate="collapsed" desc="Inativo...">
    //    public void selecaoPressed(MouseEvent evt) {
    //        if (originalLocation == null) {
    //            originalLocation = getLocationOnScreen();
    //        }
    //        back = (Graphics2D) g.create();
    //        oldX = evt.getX();
    //        oldY = evt.getY();
    //        mover = true;
    //    }
    //    public void selecaoReleased(MouseEvent evt) {
    //        mover = false;
    //        g = back;
    //    }
    //    public void selecaoMoved(MouseEvent evt) {
    //    }
    //    public void selecaoDraged(MouseEvent evt) {
    //        if (mover) {
    //            Point p = evt.getLocationOnScreen();
    //            int mx = p.x - oldX - originalLocation.x;
    //            int my = p.y - oldY - originalLocation.y;
    //            System.out.println("[" + mx + "," + my + "]");
    //            setLocation(mx, my);
    //            System.out.println("ttt");
    //        }
    //    }
    //</editor-fold>
}


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rudieri
 */
public class QuadradosConcenticos extends JFrame {

    private int center;

    public QuadradosConcenticos() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
//        super.paint(g);
    }

    public void start() {
        final Graphics g = getGraphics();
        //Espaço em branco
        int borda = 100;
        //Centro da tela
        center = getHeight() / 2;
        //Tamanho da linha
        int M = center - borda / 2;
        int N = center - borda / 2;
        int color = 0x000001;
        g.setColor(new Color(color));
        /*
        Não sei se era pra desenhar um de cada vez ou se dava pra fazer assim...
        Mas funciona :D
        */
        
        for (int i = 0; i < 10; i++) {
            
            // certer + tamanho = posição.
            
            //Cria o quadrado1
            int xp[] = {center + -N, center + N, center + N, center + -N};
            int yp[] = {center + M, center + M, center + -M, center + -M};
            g.drawPolygon(xp, yp, 4);
            //cria o quadrado2
            int xpl[] = {center, center + N, center, center + -N};
            int ypl[] = {center + -M, center, center + M, center};
            g.drawPolygon(xpl, ypl, 4);
            //Diminui o tamanho 
            M /= 2;
            N /= 2;

        }
    }

    public static void main(String[] args) {
        final QuadradosConcenticos t = new QuadradosConcenticos();
        int size=750;
        t.setSize(new Dimension(size, size));
        t.setPreferredSize(new Dimension(size, size));
        t.setResizable(false);
        t.setIgnoreRepaint(true);
        t.setVisible(true);
        t.start();
    }
}

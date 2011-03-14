
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import javax.imageio.ImageIO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rudieri
 */
public class Captura implements Runnable {

    private int x;
    private int y;
    private int w;
    private int h;
    private int tempo;
    private boolean ligado;
    private Thread thread;

    /**
     @param x Posição X na tela
     @param y Posição Y na tela
     @param w Largura da foto
     @param h Altura da foto
     @param tempo em milissegundos entre uma foto e outra
     *
     */
    public Captura(int x, int y, int w, int h, int tempo) {
       setAll(x, y, w, h, tempo);
    }

    public void setAll(int x, int y, int w, int h, int tempo){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        if (tempo<100) {
            System.out.println("Tempo ajustado para 1000 ms");
            tempo=1000;
        }
        this.tempo = tempo;
    }
    public void para() {
        ligado = false;
    }

    public void comeca() {
        if (isLigado()) {
            return ;
        }
        ligado = true;
        thread = new Thread(this);
        thread.start();
       
    }

    public void run() {
        while (isLigado()) {
            try {
                Thread.sleep(getTempo());
                Robot robot = new Robot();
                // Captura a tela na àrea definida pelo retângulo
                BufferedImage bi = robot.createScreenCapture(new Rectangle(x, y, w, h));
                // Salva a imagem
                ImageIO.write(bi, "jpg", new File(new Date().getTime()+".jpg"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @param w the w to set
     */
    public void setW(int w) {
        this.w = w;
    }

    /**
     * @param h the h to set
     */
    public void setH(int h) {
        this.h = h;
    }

    /**
     * @param tempo the tempo to set
     */
    public void setTempo(int tempo) throws Exception {
        if(tempo<100){
            throw new Exception("Não coloque um valor tão baixo, seu disco pode ficar triste :(");
        }
        this.tempo = tempo;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @return the w
     */
    public int getW() {
        return w;
    }

    /**
     * @return the h
     */
    public int getH() {
        return h;
    }

    /**
     * @return the tempo
     */
    public int getTempo() {
        return tempo;
    }

    /**
     * @return the ligado
     */
    public boolean isLigado() {
        return ligado;
    }

}

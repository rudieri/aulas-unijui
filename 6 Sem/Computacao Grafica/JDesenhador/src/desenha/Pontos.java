/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desenha;

/**
 *
 * @author rudieri
 */
public class Pontos {

    private int[] pointX;
    private int[] pointY;
    private int size;

    public Pontos() {
        pointX = new int[512];
        pointY = new int[512];
        size = 0;
    }

    public void clear() {
        size = 0;
    }

    public void addPoint(int x, int y) {
        pointX[size] = x;
        pointY[size] = y;
        size++;
    }

    public int[] getStart() {
        if (size > 0) {
            int[] re = {pointX[0], pointY[0]};
            return re;
        }
        return null;
    }

    public int[] getPointX() {
        return pointX;
    }

    public int[] getPointY() {
        return pointY;
    }

    public int getSize() {
        return size;
    }

    public boolean isEqualsStart(int x, int y) {
        if (size > 2) {
            return Math.abs(pointX[0] - x) < 5 && Math.abs(pointY[0] - y) < 5;
        }
        return false;
    }
}

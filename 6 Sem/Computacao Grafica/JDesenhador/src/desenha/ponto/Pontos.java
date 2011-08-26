/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desenha.ponto;

/**
 *
 * @author rudieri
 */
public class Pontos {

//    private int[] pointX;
//    private int[] pointY;
    private Ponto[] pontos;
    private int size;

    public Pontos() {
        pontos = new Ponto[2];
        size = 0;
    }

    public void clear() {
        size = 0;
    }

    public void aumentarCapacidade() {
        if (pontos.length == size) {
            Ponto[] novo = new Ponto[(size * 3) / 2];
            System.arraycopy(pontos, 0, novo, 0, size);
            pontos = novo;
        }
    }

    public void addPoint(int x, int y) {
        aumentarCapacidade();
        pontos[size] = new Ponto(x, y);
        size++;
    }

    public Ponto getStart() {
        if (size > 0) {
            return pontos[0];
        }
        return null;
    }

    public int getSize() {
        return size;
    }

    public boolean isEqualsStart(int x, int y) {
        if (size > 2) {
            return Math.abs(pontos[0].x - x) < 5 && Math.abs(pontos[0].y - y) < 5;
        }
        return false;
    }

    public Ponto getUltimo() {
        if (size > 0) {
            return pontos[size - 1];
        }
        return null;
    }

    public int[][] toArray() {
        int[][] ar = new int[2][size + 1];
        for (int i = 0; i < size; i++) {
            ar[0][i] = pontos[i].x;
            ar[1][i] = pontos[i].y;
        }
        ar[0][size] = pontos[0].x;
        ar[1][size] = pontos[0].y;
        return ar;
    }
}

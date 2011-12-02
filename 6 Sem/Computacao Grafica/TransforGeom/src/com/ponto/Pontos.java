/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ponto;

import java.awt.Point;

/**
 *
 * @author rudieri
 */
public class Pontos implements Cloneable {

//    private int[] pointX;
//    private int[] pointY;
    private Ponto[] pontos;
    private int size;
    private int X;
    private int Y;
    private int menorX;
    private int menorY;
    private int maiorX;
    private int maiorY;
    private int translacaoX;
    private int translacaoY;
    private double cos;
    private double sen;
    private int graus;
    private float escala;
    private boolean selecionado;

    public Pontos(int x, int y) {
        this.X = x;
        this.Y = y;
        init();
    }

    private void init() {
        pontos = new Ponto[2];
        size = 0;
        translacaoX = 0;
        translacaoY = 0;
        escala=1;
        setRotacao(0);

    }

    public void clear() {
        size = 0;
    }

    private void aumentarCapacidade() {
        if (pontos.length == size) {
            Ponto[] novo = new Ponto[(size * 3) / 2];
            System.arraycopy(pontos, 0, novo, 0, size);
            pontos = novo;
        }
    }

//    public void translate(int x, int y){
//        for (int i = 0; i < size; i++) {
//            pontos[i].x+=x;
//            pontos[i].y+=y;
//        }
//    }
    public void setRotacao(int graus) {
        this.graus = graus;
        float rad = (float) Math.toRadians(graus);
        cos = Math.cos(rad);
        sen = Math.sin(rad);
    }

    public int getRotacao() {
        return this.graus;
    }

    public void setTranslacao(Ponto ponto) {
        setTranslacao(ponto.x, ponto.y);
    }

    public void setTranslacao(int x, int y) {
        int difX=x-translacaoX;
        int difY=y-translacaoY;
        for (int i = 0; i < pontos.length; i++) {
            if ( pontos[i]==null) {
                continue;
            }
            pontos[i].x+=difX;
            pontos[i].y+=difY;
        }
        translacaoX = x;
        translacaoY = y;
    }

    public Ponto getTransacao() {
        return new Ponto(translacaoX, translacaoY);
    }

    public void addPoint(int x, int y) {
        aumentarCapacidade();
        if (x > maiorX) {
            maiorX = x;
        }
        if (x < menorX || size == 0) {
            menorX = x;
        }
        if (y > maiorY) {
            maiorY = y;
        }
        if (y < menorY || size == 0) {
            menorY = y;
        }
        pontos[size] = new Ponto(x, y);
        size++;
    }

    public Ponto getLocation() {
        return new Ponto(X, Y);
    }

    public void setLocation(Ponto p) {
        setLocation(p.x, p.y);
    }

    public void setLocation(int x, int y) {
        X = x;
        Y = y;
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
            System.out.println(Math.abs(pontos[0].x - x) + " " + Math.abs(pontos[0].y - y));
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

    public int[] getXs() {
        int[] saida = new int[size];
        for (int i = 0; i < size; i++) {
            float x = pontos[i].x;
            float y = pontos[i].y;
            int novoX = ((int) ((x * cos) + y * -sen));
            novoX*=escala;
            if (novoX > maiorX || i == 0) {
                maiorX = novoX;
            }
            if (novoX < menorX || i == 0) {
                menorX = novoX;
            }
            saida[i] = X + novoX;
        }
        return saida;
    }

    public int[] getYs() {
        int[] saida = new int[size];
        for (int i = 0; i < size; i++) {
            float x = pontos[i].x;
            float y = pontos[i].y;
            int novoY = ((int) ((x * sen) + y * cos));
            novoY*=escala;
            if (novoY > maiorY || i == 0) {
                maiorY = novoY;
            }
            if (novoY < menorY || i == 0) {
                menorY = novoY;
            }
            saida[i] = Y + novoY;
        }
        return saida;
    }

    public int getWidth() {
        return Math.abs(maiorX - menorX);
    }

    public int getHeight() {
        return Math.abs(maiorY - menorY);
    }
    
    public Ponto getCentroMassa(){
        return new Ponto(menorX + getWidth() / 2, menorY + getHeight() / 2);  
    }

    public Ponto getCentro() {
        return new Ponto(X, Y);
    }
    
    public void centralizar(){
        Ponto centroMassa = getCentroMassa();
        System.out.println("Centro Massa: " + centroMassa);
        for (int i = 0; i < pontos.length; i++) {
            if (pontos[i]==null) {
                continue;
            }
            pontos[i].x-=centroMassa.x;
            pontos[i].y-=centroMassa.y;
            translacaoX=0;
            translacaoY=0;
        }
    }

    public boolean hit(int x, int y) {
        System.out.println("=============== Inicio dos Testes ===================");
        System.out.println("1 - hitOriginal(" + x + ", " + y + ")");
        x -= X;
        y -= Y;
        System.out.println("2 - hit(" + x + ", " + y + ")");
        boolean saida = menorX <= x && maiorX >= x && menorY <= y && maiorY >= y;
        System.out.println(" = [" + menorX + " ~ " + maiorX + ", " + menorY + " ~ " + maiorY + "] => " + saida);
        return saida;
    }
    public boolean hit(Ponto p) {
        return hit(p.x, p.y);
    }

    public boolean hit(Point p) {
        return hit(p.x, p.y);
    }

    @Override
    public Object clone() {
        Pontos p = new Pontos(X, Y);
        for (int i = 0; i < size; i++) {
            p.addPoint(pontos[i].x, pontos[i].y);
        }
        return p;
    }

    /** Muda a escala do desenho, 100 = normal*/
    public void setEscala(Integer escala) {
        this.escala=((float)escala)/100;
    }

    public Integer getEscala() {
        return Math.round(escala*100);
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado=selecionado;
    }

    public boolean isSelecionado() {
        return selecionado;
    }
    
    
    
}

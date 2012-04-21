/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geomanalitica.utils.expressoes;

/**
 *
 * @author rudieri
 */
public class Variavel {
    private char nome;
    private double valor;
    private boolean inicializada;

    public Variavel(char nome) {
        this.nome = nome;
        this.inicializada = false;
    }
    public Variavel(char nome, double valor) {
        this.nome = nome;
        this.valor = valor;
        this.inicializada = true;
    }
    
    public boolean estaInicializada(){
        return inicializada;
    }

    public void setValor(double valor) {
        this.valor = valor;
        inicializada = true;
    }

    public char getNome() {
        return nome;
    }

    public double getValor() {
        if (inicializada) {
            return valor;
        }else{
            throw new IllegalStateException("Variavel não contém um valor válido.");
        }
    }
    
    
    
    
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.hci.geradorprova.bancofake;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author rudieri
 */
public class BancoAssuntos {
    private static final HashMap<String, ArrayList<String>> assuntos = new HashMap<>();
    
    static {
        adicionarAssunto("Matemática", "Expressões");
        adicionarAssunto("Matemática", "Frações");
        adicionarAssunto("Matemática", "Equação 1º Grau");
        adicionarAssunto("Matemática", "Equação 2º Grau");
        adicionarAssunto("Química", "Funções Orgânicas");
        adicionarAssunto("Química", "Funções Inorgânicas");
        adicionarAssunto("Filosofia", "O que se estuda nisso?");
        adicionarAssunto("Filosofia", "WTF?");
    }
    
    public static void adicionarAssunto(String materia, String assunto) {
        ArrayList<String> listaAssuntosMateria = assuntos.get(materia);
        if (listaAssuntosMateria == null) {
            listaAssuntosMateria = new ArrayList<>();
            assuntos.put(materia, listaAssuntosMateria);
        }
        listaAssuntosMateria.add(assunto);
    }
    
    public static ArrayList<String> listarAssuntos(String materia){
        return assuntos.get(materia);
    }
    
}

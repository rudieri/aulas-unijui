/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.hci.geradorprova.bancofake;

import java.util.ArrayList;

/**
 *
 * @author rudieri
 */
public class BancoMaterias {
    private static final ArrayList<String> materias = new ArrayList<>();
    
    static {
        materias.add("Matemática");
        materias.add("Português");
        materias.add("Física");
        materias.add("Química");
        materias.add("Geografia");
        materias.add("História");
        materias.add("Biologia");
        materias.add("Filosofia");
    }
    
    public static ArrayList<String> getMaterias(){
        return materias;
    }
    
    public static void adicionarMateira(String Mateira){
        materias.add(Mateira);
    }
}

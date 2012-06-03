/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teste;

import com.db.BD4O;
import com.db.PersistenciaInterface;
import com.objetos.Componente;
import com.objetos.Computador;
import com.objetos.Marca;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author manchini
 */
public class Teste {
    
    public static void main(String[] args) {
        
        long tempoInicial = new Date().getTime();
        int tam = 1000;
        
        
        PersistenciaInterface persistenciaInterface = new BD4O();
        //Cria Instancia
        
        persistenciaInterface.conectar();
        long tempoInicarConectar = new Date().getTime();
        
        int i = 0;
        while(i<=tam){
            Marca marca = new Marca();
            marca.setNome("marca "+i);
            persistenciaInterface.insert(marca);
            i++;
        }
        long tempoIncluirMarca = new Date().getTime();
        i=0;
        while(i<=tam){
            Computador c = new Computador();
            c.setNome("Computador "+i);            
            for (int j = 0; j < 10; j++) {
               Componente comp = new Componente();
               comp.setNome("Computador "+i+" - Componente "+j );
               comp.setMarca(persistenciaInterface.getMarcaAleatoria(tam));
               c.addComponente(comp);
            }
            persistenciaInterface.insert(c);
            i++;
        }
        long tempoIncluirComp = new Date().getTime();
        
        ///Teste Listar
        ArrayList<Marca> listaMarca =  persistenciaInterface.listarTudo(Marca.class);
        long tempolistarMarca = new Date().getTime();
        
        ArrayList<Componente> listaComponente =persistenciaInterface.listarTudo(Componente.class);
        long tempolistarComponente = new Date().getTime();
        
        ArrayList<Computador> listaComputador = persistenciaInterface.listarTudo(Computador.class);
        long tempolistarComputador = new Date().getTime();
        
        
        ////////Apagar Tdudo
        for (int j = 0; j < listaComponente.size(); j++) {
            Componente componente = listaComponente.get(j);
//            System.out.println(componente.getNome());
            persistenciaInterface.delete(componente);
        }
        long tempoParaDeletarComponentes = new Date().getTime();
        
        for (int j = 0; j < listaComputador.size(); j++) {
            Computador computador = listaComputador.get(j);
            persistenciaInterface.delete(computador);
        }
         long tempodeletarComputador = new Date().getTime();
        
         for (int j = 0; j < listaMarca.size(); j++) {
            Marca marca = listaMarca.get(j);
//            System.out.println(marca.getNome());
            persistenciaInterface.delete(marca);
        }
        long tempodeletarMarca = new Date().getTime();
        
        persistenciaInterface.desconectar();
        long tempoDesconectar = new Date().getTime();
        
        
        //Tempos
        
        System.out.println("Tempo Pra Iniciar banco: "+(tempoInicarConectar-tempoInicial));
        System.out.println("Tempo Pra Incluindo "+tam+" Marcas: "+(tempoIncluirMarca-tempoInicarConectar));
        System.out.println("Tempo Pra Incluindo "+tam+" Computadores+Comp: "+(tempoIncluirComp-tempoIncluirMarca));
        //
        //Tempo para Listar
        System.out.println("Tempo Pra Listar "+tam+" Marcas : "+(tempolistarMarca-tempoIncluirComp));
        System.out.println("Tempo Pra Listar "+tam+" Componentes : "+(tempolistarComponente-tempolistarMarca));
        System.out.println("Tempo Pra Listar "+tam+" Computadores : "+(tempolistarComputador-tempolistarComponente));
        //
        //Tempo para Excluir
        System.out.println("Tempo Pra Excluir "+tam+" Componentes : "+(tempoParaDeletarComponentes-tempolistarComputador));
        System.out.println("Tempo Pra Excluir "+tam+" Computador : "+(tempodeletarComputador-tempoParaDeletarComponentes));
        System.out.println("Tempo Pra Excluir "+tam+" Marca : "+(tempodeletarMarca-tempodeletarComputador));
        //
        //Tempo para Desconenctar
        System.out.println("Desconectar : "+(tempoDesconectar-tempodeletarMarca));
        System.out.println("Tempo Total :"+(tempoDesconectar-tempoInicial));
        
        
    }
    
}

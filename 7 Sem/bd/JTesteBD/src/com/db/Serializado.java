/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.db;

import com.objetos.Componente;
import com.objetos.Computador;
import com.objetos.Marca;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author manchini
 */
public class Serializado implements PersistenciaInterface {

    private ArrayList<Computador> listaComputadores = new ArrayList<Computador>(1000);
    private ArrayList<Componente> listaComponentes = new ArrayList<Componente>(1000);
    private ArrayList<Marca> listaMarca = new ArrayList<Marca>(1000);

    @Override
    public void insert(Object obj) {
        if (obj instanceof Marca) {
            Marca marca = (Marca) obj;
            listaMarca.remove(marca);
            listaMarca.add(marca);
        }
        if (obj instanceof Computador) {
            Computador comp = (Computador) obj;
            listaComputadores.remove(comp);
            listaComputadores.add(comp);
            ArrayList<Componente> componentes = comp.getComponentes();
            for (int i = 0; i < componentes.size(); i++) {
                Componente componente = componentes.get(i);
                listaComponentes.remove(componente);
                listaComponentes.add(componente);
            }

        }
        salvar();
    }

    @Override
    public void update(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Object obj) {
        if (obj instanceof Marca) {
            Marca marca = (Marca) obj;
            listaMarca.remove(marca);
        }
        if (obj instanceof Computador) {
            Computador comp = (Computador) obj;
            listaComputadores.remove(comp);
        }
        if (obj instanceof Componente) {
            Componente comp = (Componente) obj;
            listaComponentes.remove(comp);
        }
        salvar();
    }

    @Override
    public void carregar(Object id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList listarTudo(Class classe) {
        if (classe.equals(Marca.class)) {
            return listaMarca;
        }
        if (classe.equals(Componente.class)) {
            return listaComponentes;
        }
        if (classe.equals(Computador.class)) {
            return listaComputadores;
        }
        return null;

    }

    @Override
    public void conectar() {
        try {
            leArquivo("marca.txt");
            leArquivo("componentes.txt");
            leArquivo("computadore.txt");
        } catch (Exception ex) {
            System.out.println("não achou os arquivos");
        }
    }

    @Override
    public void desconectar() {
        salvar();
    }

    public void salvar() {
        try {
            gravar(listaMarca, "marca.txt");
            gravar(listaComponentes, "componentes.txt");
            gravar(listaComputadores, "computadore.txt");

            //
            leArquivo("marca.txt");
            leArquivo("componentes.txt");
            leArquivo("computadore.txt");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void leArquivo(String nome) throws Exception {
        FileInputStream leitor = new FileInputStream(nome);
        ObjectInputStream in = new ObjectInputStream(leitor);
        Object readObject = in.readObject();
        if (readObject instanceof ArrayList && !((ArrayList) readObject).isEmpty()) {
            Object aux = ((ArrayList) readObject).get(0);
            if (aux instanceof Marca) {
                listaMarca = (ArrayList<Marca>) readObject;
            }
            if (aux instanceof Componente) {
                listaComponentes = (ArrayList<Componente>) readObject;
            }
            if (aux instanceof Computador) {
                listaComputadores = (ArrayList<Computador>) readObject;
            }
        }
        in.close();
        leitor.close();
    }

    private void gravar(Object obj, String nome) throws Exception {
        FileOutputStream arquivo = new FileOutputStream(nome);
        ObjectOutputStream out = new ObjectOutputStream(arquivo);
        out.writeObject(obj);
        out.close();
        arquivo.close();
    }

   @Override
    public Marca getMarcaAleatoria(int tam) {
        Random r = new Random();
        Marca marca = new Marca(r.nextInt(tam));
        return marca;
    }
}

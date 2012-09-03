/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.hci.geradorprova.cadastro;

import br.hci.geradorprova.utils.constantes.TipoQuestao;
import br.hci.geradorprova.utils.constantes.TipoQuestaoObjetiva;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author rudieri
 */
public class CadastroQuestoes implements Serializable {

    private ArrayList<String> materias = new ArrayList<>();
    private TipoQuestao tipoQuestao;
    private TipoQuestaoObjetiva tipoQuestaoObjetiva;
    private String materiaSelecionada;
    private HashMap<String, ArrayList<String>> assuntos = new HashMap<>();

    public CadastroQuestoes() {
        materias.add("Matemática");
        materias.add("Português");
        materias.add("Física");
        materias.add("Química");
        materias.add("Geografia");
        materias.add("História");
        materias.add("Biologia");
        materias.add("Filosofia");
        adicionarAssunto("Matemática", "Expressões");
        adicionarAssunto("Matemática", "Frações");
        adicionarAssunto("Matemática", "Equação 1º Grau");
        adicionarAssunto("Matemática", "Equação 2º Grau");
        adicionarAssunto("Química", "Funções Orgânicas");
        adicionarAssunto("Química", "Funções Inorgânicas");
        adicionarAssunto("Filosofia", "O que se estuda nisso?");
        adicionarAssunto("Filosofia", "WTF?");

    }

    private void adicionarAssunto(String materia, String assunto) {
        ArrayList<String> listaAssuntosMateria = assuntos.get(materia);
        if (listaAssuntosMateria == null) {
            listaAssuntosMateria = new ArrayList<>();
            assuntos.put(materia, listaAssuntosMateria);
        }
        listaAssuntosMateria.add(assunto);
    }

    public ArrayList<String> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<String> materias) {
        this.materias = materias;
    }

    public ArrayList<String> getAssuntos() {
        return assuntos.get(materiaSelecionada);
    }

    public void setAssuntos(ArrayList<String> assuntos) {
        this.assuntos.put(materiaSelecionada, assuntos);
    }

    public String getMateriaSelecionada() {
        return materiaSelecionada;
    }

    public void setMateriaSelecionada(String materiaSelecionada) {
        System.out.println("WTF? " + materiaSelecionada);
        this.materiaSelecionada = materiaSelecionada;
    }

    public String getTipoQuestao() {
        return tipoQuestao == null ? "" : tipoQuestao.getNome();
    }

    public void setTipoQuestao(String tipoQuestao) {
        this.tipoQuestao = TipoQuestao.getPorNome(tipoQuestao);
    }

    public ArrayList<String> listarTipoQuestao() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < TipoQuestao.values().length; i++) {
            list.add(TipoQuestao.values()[i].getNome());
        }
        return list;
    }

    public String getTipoQuestaoObjetiva() {
        return tipoQuestaoObjetiva == null ? "" : tipoQuestaoObjetiva.getNome();
    }

    public void setTipoQuestaoObjetiva(String tipoQuestaoObjetiva) {
        this.tipoQuestaoObjetiva = TipoQuestaoObjetiva.getPorNome(tipoQuestaoObjetiva);
    }

    public ArrayList<String> listarTipoQuestaoObjetiva() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < TipoQuestaoObjetiva.values().length; i++) {
            list.add(TipoQuestaoObjetiva.values()[i].getNome());
        }
        return list;
    }

    public boolean questoeEhObjetiva() {
        return tipoQuestao != null && tipoQuestao == TipoQuestao.OBJETIVA;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.hci.geradorprova.cadastro;

import br.hci.geradorprova.bancofake.BancoMaterias;
import br.hci.questoes.QuestaoRelacional;
import br.hci.questoes.TipoQuestao;
import br.hci.questoes.TipoQuestaoObjetiva;
import br.hci.questoes.TipoQuestaoRelacional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author rudieri
 */
public class CadastroQuestoes implements Serializable {

    
    private TipoQuestao tipoQuestao;
    private TipoQuestaoObjetiva tipoQuestaoObjetiva;
    private TipoQuestaoRelacional tipoQuestaoRelacional;
    private HashMap<String, ArrayList<String>> assuntos = new HashMap<>();
    private ArrayList<String> questoesVF = new ArrayList<>();
    private String perguntaVF;
    private ArrayList<QuestaoRelacional> questoesRelacionais = new ArrayList<>();
    private String perguntaRelacionalEsquerda;
    private String perguntaRelacionalDireita;
    private QuestaoRelacional questaoRelacional;

    public CadastroQuestoes() {

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
        if (this.tipoQuestaoObjetiva != TipoQuestaoObjetiva.VERDADEIRO_FALSO) {
            questoesVF.clear();
        }
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

    public ArrayList<String> getQuestoesVF() {
        return questoesVF;
    }

    public void setQuestoesVF(ArrayList<String> questoesVF) {
        this.questoesVF = questoesVF;
    }

    public String getPerguntaVF() {
        return perguntaVF;
    }

    public void setPerguntaVF(String perguntaVF) {
        questoesVF.add(perguntaVF);
        this.perguntaVF = null;
    }

    public String getTipoQuestaoRelacional() {
        return tipoQuestaoRelacional == null ? "" : tipoQuestaoRelacional.getNome();
    }

    public void setTipoQuestaoRelacional(String tipoQuestaoRelacional) {
        this.tipoQuestaoRelacional = TipoQuestaoRelacional.getPorNome(tipoQuestaoRelacional);
    }

    public ArrayList<String> listarTipoQuestaoRelacional() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < TipoQuestaoRelacional.values().length; i++) {
            list.add(TipoQuestaoRelacional.values()[i].getNome());
        }
        return list;
    }

    public String getPerguntaRelacionalEsquerda() {
        return perguntaRelacionalEsquerda;
    }

    public void setPerguntaRelacionalEsquerda(String perguntaRelacionalEsquerda) {
        System.out.println(perguntaRelacionalEsquerda);
        if (questaoRelacional == null) {
            questaoRelacional = new QuestaoRelacional();
            questaoRelacional.setTipoQuestaoRelacional(tipoQuestaoRelacional);
            questaoRelacional.setTextoEsquerda(perguntaRelacionalEsquerda);
        } else {
            questaoRelacional.setTextoEsquerda(perguntaRelacionalEsquerda);
            questoesRelacionais.add(questaoRelacional);
            questaoRelacional = null;
        }
        System.out.println(questoesRelacionais.size());
    }

    public String getPerguntaRelacionalDireita() {
        return perguntaRelacionalDireita;
    }

    public void setPerguntaRelacionalDireita(String perguntaRelacionalDireita) {
        System.out.println(perguntaRelacionalDireita);
        if (questaoRelacional == null) {
            questaoRelacional = new QuestaoRelacional();
            questaoRelacional.setTipoQuestaoRelacional(tipoQuestaoRelacional);
            questaoRelacional.setTextoDireito(perguntaRelacionalDireita);
        } else {
            questaoRelacional.setTextoDireito(perguntaRelacionalDireita);
            questoesRelacionais.add(questaoRelacional);
            questaoRelacional = null;
        }
        System.out.println(questoesRelacionais.size());
    }

    public ArrayList<QuestaoRelacional> getQuestoesRelacionais() {
        return questoesRelacionais;
    }

    public void setQuestoesRelacionais(ArrayList<QuestaoRelacional> questoesRelacionais) {
        this.questoesRelacionais = questoesRelacionais;
    }
}

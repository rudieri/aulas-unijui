/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.hci.questoes;

/**
 *
 * @author rudieri
 */
public class QuestaoRelacional {
    private String textoEsquerda;
    private String textoDireito;
    private TipoQuestaoRelacional tipoQuestaoRelacional;

    public QuestaoRelacional() {
    }

    public String getTextoEsquerda() {
        return textoEsquerda;
    }

    public void setTextoEsquerda(String textoEsquerda) {
        this.textoEsquerda = textoEsquerda;
    }

    public String getTextoDireito() {
        return textoDireito;
    }

    public void setTextoDireito(String textoDireito) {
        this.textoDireito = textoDireito;
    }

    public TipoQuestaoRelacional getTipoQuestaoRelacional() {
        return tipoQuestaoRelacional;
    }

    public void setTipoQuestaoRelacional(TipoQuestaoRelacional tipoQuestaoRelacional) {
        this.tipoQuestaoRelacional = tipoQuestaoRelacional;
    }

    @Override
    public String toString() {
        return textoEsquerda + " - " + textoDireito;
    }
    
}

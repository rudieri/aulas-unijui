/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.dados;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author manchini
 */
@Entity
//@Table(uniqueConstraints = {
//    @UniqueConstraint(columnNames = {"localJogada", "ordemJogada", "jogadorTipo", "Jogo"})})
public class Jogada implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Local localJogada;
    private Ordem ordemJogada;
    private JogadorTipo jogadorTipo;
    @ManyToOne
    private Jogo jogo;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    public JogadorTipo getJogadorTipo() {
        return jogadorTipo;
    }

    public void setJogadorTipo(JogadorTipo jogadorTipo) {
        this.jogadorTipo = jogadorTipo;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Local getLocalJogada() {
        return localJogada;
    }

    public void setLocalJogada(Local localJogada) {
        this.localJogada = localJogada;
    }

    public Ordem getOrdemJogada() {
        return ordemJogada;
    }

    public void setOrdemJogada(Ordem ordemJogada) {
        this.ordemJogada = ordemJogada;
    }
}

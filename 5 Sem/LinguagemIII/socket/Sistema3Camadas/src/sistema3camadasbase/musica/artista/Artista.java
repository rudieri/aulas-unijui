/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema3camadasbase.musica.artista;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import sistema3camadasbase.util.Nomeavel;
import sistema3camadasbase.util.Replace;

/**
 *
 * @author manchini
 */
@Entity
@Table(name = "artista")
public class Artista extends Nomeavel implements Serializable {

    public Artista() {
    }

    public Artista(String st) {
        try {
            id = new Integer(st);
        } catch (Exception ex) {
            System.out.print("Erro ao Montar Artista");
        }
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "nome", nullable = false)
    private String nome;

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    @Override
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    @Override
    public boolean  setNome(String nome) {
        if (nome == null || nome.equals("")) {
            this.nome = "";
            return false;
        } else {
            this.nome = nome;
            return true;
        }
    }

    @Override
    public String toString() {
        return "Artista["
                + "id=" + getId() + ","
                + "nome=" + Replace.clear(getNome()) + "]";
    }
}

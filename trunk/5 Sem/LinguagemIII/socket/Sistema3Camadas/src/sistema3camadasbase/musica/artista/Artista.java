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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author manchini
 */
@Entity
@Table(name="artista")
public class Artista implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    @Column(name="nome",nullable=false)
    private int nome;

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

    /**
     * @return the nome
     */
    public int getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(int nome) {
        this.nome = nome;
    }


}

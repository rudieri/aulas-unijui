/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema3camadasbase.musica.genero;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import sistema3camadasbase.util.Replace;

/**
 *
 * @author manchini
 */
@Entity
@Table(name="genero")
public class Genero implements Serializable {

    public Genero() {
    }

     public Genero(String st) {
        try {
            id = new Integer(st);
        } catch (Exception ex) {
            System.out.print("Erro ao Montar Artista");
        }
    }



    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name="nome",nullable=false)
    private String nome;

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
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    @Override
    public String toString(){
        return "Genero["
                +"id=" + getId() + ","
                +"nome="+Replace.clear(getNome())
                +"]";
    }

}

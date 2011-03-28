/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema3camadasbase.util;

import java.io.Serializable;

/**
 *
 * @author rudieri
 */
public class Nomeavel implements Serializable{
    private String nome;
    private Integer id;

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public boolean  setNome(String nome) {
       if (nome == null || nome.equals("")) {
            this.nome = "";
            return false;
        } else {
            this.nome = nome;
            return true;
        }
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.dados;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author rudieri
 */
@Entity
public class Jogo implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private Integer empates;
    private Integer vitorias;
    private Integer derrotas;
    private Integer desistencias;

    public Jogo() {
        empates=0;
        vitorias=0;
        derrotas=0;
        desistencias=0;
    }

    public Jogo(Integer id) {
        this();
        this.id = id;
    }
    
    
    public int getSaldo(){
        return vitorias-derrotas;
    }
    
    public void incrementaVitorias(){
        vitorias++;
    }
    public void incrementaDerrotas(){
        derrotas++;
    }
    public void incrementaDesistencias(){
        desistencias++;
    }
    public void incrementaEmpates(){
        empates++;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(Integer derrotas) {
        this.derrotas = derrotas;
    }

    public Integer getEmpates() {
        return empates;
    }

    public void setEmpates(Integer empatres) {
        this.empates = empatres;
    }

    public Integer getVitorias() {
        return vitorias;
    }

    public void setVitorias(Integer vitorias) {
        this.vitorias = vitorias;
    }

    public Integer getDesistencias() {
        return desistencias;
    }

    public void setDesistencias(Integer desistencias) {
        this.desistencias = desistencias;
    }
    
}

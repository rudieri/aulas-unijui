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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author manchini
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"p1", "p2", "p3", "p4", "p5", "p6", "p7", "p8", "p9"})})
public class Jogada implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private Peca p1 = Peca.NULO;
    private Peca p2 = Peca.NULO;
    private Peca p3 = Peca.NULO;
    private Peca p4 = Peca.NULO;
    private Peca p5 = Peca.NULO;
    private Peca p6 = Peca.NULO;
    private Peca p7 = Peca.NULO;
    private Peca p8 = Peca.NULO;
    private Peca p9 = Peca.NULO;
    private int vitorias;
    private int derrotas;
    private int empate;

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
     * @return the p1
     */
    public Peca getP1() {
        return p1;
    }

    /**
     * @param p1 the p1 to set
     */
    public void setP1(Peca p1) {
        this.p1 = p1;
    }

    /**
     * @return the p2
     */
    public Peca getP2() {
        return p2;
    }

    /**
     * @param p2 the p2 to set
     */
    public void setP2(Peca p2) {
        this.p2 = p2;
    }

    /**
     * @return the p3
     */
    public Peca getP3() {
        return p3;
    }

    /**
     * @param p3 the p3 to set
     */
    public void setP3(Peca p3) {
        this.p3 = p3;
    }

    /**
     * @return the p4
     */
    public Peca getP4() {
        return p4;
    }

    /**
     * @param p4 the p4 to set
     */
    public void setP4(Peca p4) {
        this.p4 = p4;
    }

    /**
     * @return the p5
     */
    public Peca getP5() {
        return p5;
    }

    /**
     * @param p5 the p5 to set
     */
    public void setP5(Peca p5) {
        this.p5 = p5;
    }

    /**
     * @return the p6
     */
    public Peca getP6() {
        return p6;
    }

    /**
     * @param p6 the p6 to set
     */
    public void setP6(Peca p6) {
        this.p6 = p6;
    }

    /**
     * @return the p7
     */
    public Peca getP7() {
        return p7;
    }

    /**
     * @param p7 the p7 to set
     */
    public void setP7(Peca p7) {
        this.p7 = p7;
    }

    /**
     * @return the p8
     */
    public Peca getP8() {
        return p8;
    }

    /**
     * @param p8 the p8 to set
     */
    public void setP8(Peca p8) {
        this.p8 = p8;
    }

    /**
     * @return the p9
     */
    public Peca getP9() {
        return p9;
    }

    /**
     * @param p9 the p9 to set
     */
    public void setP9(Peca p9) {
        this.p9 = p9;
    }

    /**
     * @return the vitorias
     */
    public int getVitorias() {
        return vitorias;
    }

    /**
     * @param vitorias the vitorias to set
     */
    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }

    /**
     * @return the derrotas
     */
    public int getDerrotas() {
        return derrotas;
    }

    /**
     * @param derrotas the derrotas to set
     */
    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    /**
     * @return the empate
     */
    public int getEmpate() {
        return empate;
    }

    /**
     * @param empate the empate to set
     */
    public void setEmpate(int empate) {
        this.empate = empate;
    }
}

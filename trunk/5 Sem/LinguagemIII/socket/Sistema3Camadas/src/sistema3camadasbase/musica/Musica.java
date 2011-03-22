/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema3camadasbase.musica;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import sistema3camadasbase.musica.album.Album;
import sistema3camadasbase.musica.artista.Artista;
import sistema3camadasbase.musica.genero.Genero;
import sistema3camadasbase.util.Replace;

/**
 *
 * @author manchini
 */
@Entity
@Table(name = "musica")
public class Musica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;
    @ManyToOne
    private Artista autor;
    @ManyToOne
    private Genero genero;
    @ManyToOne
    private Album Album;

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public boolean setNome(String nome) {
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

    /**
     * @return the autor
     */
    public Artista getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(Artista autor) {
        this.autor = autor;
    }

    /**
     * @return the genero
     */
    public Genero getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    /**
     * @return the Album
     */
    public Album getAlbum() {
        return Album;
    }

    /**
     * @param Album the Album to set
     */
    public void setAlbum(Album Album) {
        this.Album = Album;
    }

    @Override
    public String toString() {
        String st = "Musica[";
        st += getId()==null?"":("id=" + getId() + ",");
        st += "nome=" + Replace.clear(getNome());
        st += getAutor() == null ? "" : (",autor=" + getAutor().getId() + ",");
        st += getGenero() == null ? "" : (",genero=" + getGenero().getId());
        st += getAlbum() == null ? "" : (",album=" + getAlbum().getId());
        st += "]";
        return st;
    }
}

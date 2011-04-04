/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema3camadasbase.musica.capas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.swing.ImageIcon;
import sistema3camadasbase.util.Nomeavel;

/**
 *
 * @author rudieri
 */
@Entity
@Table(name="capa")
public class Capa extends Nomeavel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "nome", nullable = false)
    private String nome;
   // @Column(name="imagem",length=99999)
//    private ImageIcon image;

    public Capa() {
    }

  
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
    public void setId(Integer id) {
        this.id=id;
    }

    @Override
    public String getNome() {
        return nome;
    }

//    public void setImage(ImageIcon image) {
//        this.image = image;
//    }
//
//    public ImageIcon getImage() {
//        return image;
//    }

   

    @Override
    public Integer getId() {
        return id;
    }

   


}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema3camadasbase.conexao;

import java.io.Serializable;
import sistema3camadasbase.musica.album.Album;
import sistema3camadasbase.musica.artista.Artista;

/**
 *
 * @author manchini
 */
public class Montador {



    public static Serializable Montador(String s){
        if(s.trim().equals(""))
            return null;
        String classe = "";
        if(s.substring(0, 2).indexOf("&") > -1)
            classe = s.substring(2, s.indexOf("["));
        else
            classe = s.substring(0, s.indexOf("["));

        if(classe.equals("Album")){
            Album album = new Album();
            if(s.indexOf("id") != -1){
                album.setId(Integer.valueOf(aux("id",s)));
            }
            if(s.indexOf("nome") != -1){
                album.setNome(aux("nome",s));
            }

            return album;
        }

        if(classe.equals("Artista")){
            Artista artista = new Artista();
            if(s.indexOf("id") != -1){
                artista.setId(Integer.valueOf(aux("id",s)));
            }
            if(s.indexOf("nome") != -1){
                artista.setNome(aux("nome",s));
            }

            return artista;
        }


        return null;
        
    }


    private static String aux(String campo, String tripa){

        int tamanhoCamp = campo.length()+1;
        int posicao = tripa.indexOf(campo)+tamanhoCamp;
        int posicaoVirgula = tripa.indexOf(",",posicao);

        if(posicaoVirgula ==-1){
            return tripa.substring(posicao, tripa.length()-1);
        }else{
             return tripa.substring(posicao, posicaoVirgula);
        }


    }



}

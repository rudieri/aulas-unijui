/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema3camadasbase.conexao;

import java.util.ArrayList;

/**
 *
 * @author manchini
 */
public class Lista extends ArrayList<Object> {


    @Override
    public String toString(){
        String retorno = "[";

        for(int i = 0; i < this.size();i++){
            retorno+=this.get(i).toString()+",";
        }
        retorno+="]";
        return retorno;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema3camadasbase.conexao;

import java.io.Serializable;

/**
 *
 * @author manchini
 */
public class Mensagem implements Serializable {

    public static final int TIPO_INCLUIR = 0;
    public static final int TIPO_EXCLUIR = 1;
    public static final int TIPO_LISTAR = 2;
    public static final int TIPO_RETORNO = 3;
    public static final int TIPO_CARREGAR = 4;
    private int tipo;
    private Serializable objeto;

    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the objeto
     */
    public Object getObjeto() {
        return objeto;
    }

    /**
     * @param objeto the objeto to set
     */
    public void setObjeto(Serializable objeto) {
        this.objeto = objeto;
    }
}

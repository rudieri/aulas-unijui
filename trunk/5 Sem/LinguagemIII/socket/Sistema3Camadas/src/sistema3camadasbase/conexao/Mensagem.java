/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema3camadasbase.conexao;

/**
 *
 * @author manchini
 */
public class Mensagem {

    public static final int TIPO_INCLUIR = 0;
    public static final int TIPO_EXCLUIR = 1;
    public static final int TIPO_LISTAR= 2;
    public static final int TIPO_RETORNO= 3;

    private int tipo;
    private Object objeto;

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
    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }


}

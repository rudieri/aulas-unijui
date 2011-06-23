/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha.dados;

import java.util.List;
import jogovelha.banco.Transacao;

/**
 *
 * @author manchini
 */
public class JogadaDAO {

    public static Jogada getJogadaBanco(Jogada j) {
        Transacao t = new Transacao();
        t.begin();

        String filtro = "WHERE ";
        filtro += "p1 = " + (j.getP1() == null ? null : j.getP1().getId()) + " ";
        filtro += "AND p2= " + (j.getP2() == null ? null : j.getP2().getId()) + " ";
        filtro += "AND p3= " + (j.getP3() == null ? null : j.getP3().getId()) + " ";
        filtro += "AND p4= " + (j.getP4() == null ? null : j.getP4().getId()) + " ";
        filtro += "AND p5= " + (j.getP5() == null ? null : j.getP5().getId()) + " ";
        filtro += "AND p6= " + (j.getP6() == null ? null : j.getP6().getId()) + " ";
        filtro += "AND p7= " + (j.getP7() == null ? null : j.getP7().getId()) + " ";
        filtro += "AND p8= " + (j.getP8() == null ? null : j.getP8().getId()) + " ";
        filtro += "AND p9= " + (j.getP9() == null ? null : j.getP9().getId()) + " ";
        List lista = t.listar("Jogada", filtro);
        t.commit();

        if (lista.size() == 1) {
            return (Jogada) lista.get(0);
        } else {
            return null;
        }


    }
}

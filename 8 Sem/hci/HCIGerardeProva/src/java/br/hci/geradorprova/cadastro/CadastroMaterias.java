/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.hci.geradorprova.cadastro;

import br.hci.geradorprova.bancofake.BancoMaterias;
import java.util.ArrayList;

/**
 *
 * @author rudieri
 */
public class CadastroMaterias {
    private String materia;

    public void salvarMateria(){
        BancoMaterias.adicionarMateira(materia);
        materia = null;
    }
    
    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }
    
    public ArrayList<String> listarMaterias(){
        return BancoMaterias.getMaterias();
    }

    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.FileUtils;

/**
 *
 * @author labinf
 */
public class Leitor {

    public Leitor() {
        try {
            StringBuffer leArquivo = FileUtils.leArquivo(new File(getClass().getResource("definicao.txt").getFile()));
            
        } catch (Exception ex) {
            Logger.getLogger(Leitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rogerio.pid;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author rogerio
 */
public class Main extends JFrame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Main main = new Main();
        main.getContentPane().setLayout(new BorderLayout());
        main.getContentPane().add(new PIDPainel(), BorderLayout.CENTER);
        main.setSize(900, 470);
        main.setTitle(" -=: PID :=- ");
        main.setVisible(true);
    }

}

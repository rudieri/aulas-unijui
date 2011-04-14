package xxx;

import javax.swing.*;

/**
 * 
 * @author manchini
 */
public class BarraDeProgresso extends Thread {

    private int quantidadeDeSegundos;

    /**
     * 
     * @param segundos
     */
    public BarraDeProgresso(int segundos) {
        quantidadeDeSegundos = segundos;
        start();
    }

    /**
     * 
     */
    @Override
    public void run() {
        try {
            for (int i = 0; i < quantidadeDeSegundos; i++) {
                Thread.sleep(1000);
                System.out.print(".");
            }
            JOptionPane.showMessageDialog(null, "Tarefa concluída.");
        } catch (InterruptedException e) {
        }
    }
}
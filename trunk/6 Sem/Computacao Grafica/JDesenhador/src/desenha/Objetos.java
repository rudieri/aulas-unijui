/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desenha;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author rudieri
 */
public class Objetos extends JPanel {

    private final JLabel[] labels;
    private final int[] zOrder;
    private static final int NUM_MAX_OBJETOS = 512;
    private int size;
    private int current;
    // Preguiça de continuar... não ta funcionando, apenas o básico funciona!
    public Objetos() {
        labels = new JLabel[NUM_MAX_OBJETOS];
        zOrder = new int[NUM_MAX_OBJETOS];
        size=0;
        setSize(500, 500);
        setLayout(null);
    }

    public int length() {
        return size;
    }

    public void add(JLabel label) {
        add((Component)label);
        zOrder[size] = getComponentZOrder(label);
        labels[size++] = label;
    }

    public JLabel select(int index) {
        if (index < size) {
            current = index;
            return labels[current];
        }
        return null;
    }

    public int select(JLabel index) {
        for (int i = 0; i < labels.length; i++) {
            if (labels[i].equals(index)) {
                current = i;
                return i;
            }
        }
        return -1;
    }

    public JLabel get() {
        return labels[current];
    }

    public int getZ() {
        return zOrder[current];
    }

    public void swap(int index1, int index2) {
        int aux = zOrder[index2];
        zOrder[index2] = zOrder[index1];
        zOrder[index1] = aux;
    }
}

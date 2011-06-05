package jogovelha.utils;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rudieri
 */
public class MyComboBoxModel implements ComboBoxModel {

    private String[] strings = {""};
    private short tamanho;
    private short selecioado = -1;
//    private ArrayList<ListDataListener> dataListener;

    public void setElements(String[] s) {
        strings = s;
        tamanho = (short) s.length;
    }

    public void addElementIfNotExist(String element) {
        for (String string : strings) {
            if (string.equals(element)) {
                return;
            }
        }
        if (tamanho >= strings.length) {
            String[] old = strings;
            strings = new String[tamanho + 1];
            System.arraycopy(old, 0, strings, 0, tamanho);
        }
        strings[tamanho] = element;
        tamanho++;
    }

    public int getSize() {
        return strings.length;
    }

    public Object getElementAt(int index) {
        return strings[index];
    }

    public short getIndexOfElement(Object obj) {
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            if (string.equals(obj)) {
                return (short) i;
            }
        }
        return -1;
    }

    public void setSelectedItem(Object anItem) {
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            if (string.equals(anItem)) {
                selecioado = (short) i;
                break;
            }
        }
    }

    public Object getSelectedItem() {
        if (selecioado == -1) {
            return null;
        }
        return strings[selecioado];
    }

    public void addListDataListener(ListDataListener l) {
//        dataListener.add(l);
        System.err.println("Listeners não implementados.");
    }

    public void removeListDataListener(ListDataListener l) {
//        dataListener.remove(l);
        System.err.println("Listeners não implementados.");
    }
}

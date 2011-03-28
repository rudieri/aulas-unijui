package sistema3camadasbase.util;


import javax.swing.AbstractListModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rudieri
 */
public class MyListModel extends AbstractListModel {

    private   String[] strings = {"lol"};

    public void setElements(String[] s) {
        strings = s;
    }

    public int getSize() {
        return strings.length;
    }

    public Object getElementAt(int index) {
        return strings[index];
    }
}

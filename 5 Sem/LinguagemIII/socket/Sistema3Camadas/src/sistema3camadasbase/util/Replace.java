/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema3camadasbase.util;

/**
 *
 * @author manchini
 */
public class Replace {

    public static String clear(String s) {
        if (s == null) {
            return "";
        } else {
            return s.replace("[", "").replace("]", "").replace(",", "");
        }

    }
}


import java.io.File;
import java.io.FileInputStream;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author manchini
 */
public class abrir {
    public static void main(String[] args) {
        try {
   
            FileInputStream st = new FileInputStream(new File((String)args[0]));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}

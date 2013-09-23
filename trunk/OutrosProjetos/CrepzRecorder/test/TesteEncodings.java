
import javax.sound.sampled.AudioFormat.Encoding;
import org.tritonus.share.sampled.Encodings;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rudieri
 */
public class TesteEncodings {
    public static void main(String[] args) {
        Encoding[] encodings = Encodings.getEncodings();
        for (int i = 0; i < encodings.length; i++) {
            Encoding encoding = encodings[i];
            System.out.println(encoding.toString());
        }
    }
   
}

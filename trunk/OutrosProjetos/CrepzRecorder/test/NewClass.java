
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author rudieri
 */
public class NewClass {

    public static void main(String[] args) {
//        ArrayDeque<Object> arrayDeque = new ArrayDeque<>(Integer.MAX_VALUE>>>4);
//        arrayDeque.add(args);
        HashMap<Integer, String> mapa = new HashMap<>();
        mapa.put(6, "teste1");
        mapa.put(7, "teste2");
        mapa.put(2, "teste3");
        mapa.put(5, "teste4");
        System.out.println(mapa.remove(6));
    }

    static class Bar {

        static {
            Baz.testAsserts();
// Will execute before Baz is initialized!
        }

        public static void oi() {
            System.out.println("oi");
        }
    }

    static class Baz extends Bar {

        static void testAsserts() {
            boolean enabled = false;
            assert enabled == true;
            System.out.println("Asserts " + (enabled ? "enabled" : "disabled"));
        }
    }
}


import java.util.*;

public class Entradadois {

    public String xexto() {
        return new Scanner(System.in).nextLine();
    }

    public static void main(String[] args) {
        System.out.println("Digite seu Primeiro nome");
        Entradadois e = new Entradadois();
        String n1 = e.xexto();
        System.out.println("Digite seu Segundo nome");
        String n2 = e.xexto();
        System.out.println("Seu nome ehh ");
        System.out.println(n1 + " " + n2);
    }
}

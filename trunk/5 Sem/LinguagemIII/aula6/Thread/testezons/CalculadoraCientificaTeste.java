

import xxx.CalculadoraCientifica;

public class CalculadoraCientificaTeste {
  public static void main(String[] args) {
    CalculadoraCientifica usp = new CalculadoraCientifica("USP", ".");
    CalculadoraCientifica unb = new CalculadoraCientifica("UNB", ",");
    try {
      usp.start();
      unb.start();
      System.out.println("Threads iniciadas.");
      //Espera ate a usp terminar. 
      usp.join();
      System.out.println("A USP terminou.");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
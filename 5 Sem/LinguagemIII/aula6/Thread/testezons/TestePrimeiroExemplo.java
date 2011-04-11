
import xxx.ExemploDeForRunnable;



public class TestePrimeiroExemplo {
  public static void main(String[] args) {
    ExemploDeForRunnable processo1 = new ExemploDeForRunnable();
    Thread minhaThread = new Thread(processo1);
    minhaThread.setName("Thread 1");

    minhaThread.setPriority(1);
    minhaThread.start();

    ExemploDeForRunnable processo2 = new ExemploDeForRunnable();
    minhaThread = new Thread(processo2);
    minhaThread.setName("Thread 2");

    minhaThread.setPriority(10);
    minhaThread.start();
  }
}
package xxx;

public class ExemploDeForRunnable implements Runnable {
  public void run() {
    try {
      for (int i = 0; i < 1000; i++) {
        System.out.println("Número sequencial: " + i);
        System.out.println("Nome da Thread: "
            + Thread.currentThread().getName());
        Thread.sleep(1000);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
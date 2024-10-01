import java.util.Random;
import java.util.Scanner;

public class ThreadMonitor {

    // Classe per il thread che conta fino a un certo valore X
    static class CountingThread extends Thread {
        private final int X;
        private int currentValue = 0;

        public CountingThread(int X) {
            this.X = X;
        }

        @Override
        public void run() {
            try {
                while (currentValue <= X) {
                    Thread.sleep(120);  // Aspetta 120 ms
                    currentValue++;     // Incrementa il contatore
                    System.out.println(Thread.currentThread().getName() + " stampa " + currentValue);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public int getCurrentValue() {
            return currentValue;
        }

        public boolean isCompleted() {
            return currentValue > X;
        }
    }

    public static void main(String[] args) {
        // Input dell'utente
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il numero di thread (T): ");
        int T = scanner.nextInt();
        System.out.print("Inserisci il valore massimo N: ");
        int N = scanner.nextInt();

        // Creazione dei thread
        CountingThread[] threads = new CountingThread[T];
        Random random = new Random();

        for (int i = 0; i < T; i++) {
            int X = random.nextInt(N + 1);  // Genera un numero casuale tra 0 e N
            threads[i] = new CountingThread(X);
            threads[i].start();  // Avvia il thread
            System.out.println("Thread " + i + " conta fino a " + X);
        }

        // Monitoraggio dei thread
        boolean allCompleted = false;
        while (!allCompleted) {
            try {
                Thread.sleep(1000);  // Attende 1 secondo prima di stampare lo stato

                allCompleted = true;
                
                System.out.println("---------------------------");
                for (int i = 0; i < T; i++) {
                    if (threads[i].isAlive()) {
                        System.out.println("Thread " + i + " è attivo, valore corrente: " + threads[i].getCurrentValue());
                        allCompleted = false;  // C'è ancora almeno un thread attivo
                    } else {
                        System.out.println("Thread " + i + " COMPLETATO");
                    }
                }
                System.out.println("---------------------------");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }//fine thread

        // Attende che tutti i thread terminino
        for (int i = 0; i < T; i++) {
            try {
                threads[i].join();  // Aspetta il completamento di ogni thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Messaggio finale
        System.out.println("TUTTI I THREAD COMPLETATI");
    }
}

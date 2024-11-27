// MultiThreadExample.java
class CalculationThread extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Calculating: " + (i + 1) * 2);
            try {
                Thread.sleep(1000); // Simulate time-consuming calculation
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class LoggingThread extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Logging data: " + (i + 1));
            try {
                Thread.sleep(500); // Simulate logging time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class MultiThreadExample {
    public static void main(String[] args) {
        CalculationThread calcThread = new CalculationThread();
        LoggingThread logThread = new LoggingThread();

        calcThread.start();
        try {
            calcThread.join(); // Wait for calcThread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logThread.start();
    }
}
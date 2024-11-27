import java.util.LinkedList;
import java.util.Queue;

class ProducerConsumer {
    private Queue<Integer> buffer = new LinkedList<>();
    private final int LIMIT = 5; // Buffer limit
    private final Object lock = new Object();

    public void produce() {
        int value = 0;
        while (true) {
            synchronized (lock) {
                while (buffer.size() == LIMIT) {
                    try {
                        lock.wait(); // Wait if buffer is full
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                buffer.add(value);
                System.out.println("Produced: " + value);
                value++;
                lock.notify(); // Notify consumer
            }
        }
    }

    public void consume() {
        while (true) {
            synchronized (lock) {
                while (buffer.isEmpty()) {
                    try {
                        lock.wait(); // Wait if buffer is empty
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                int value = buffer.poll();
                System.out.println("Consumed: " + value);
                lock.notify(); // Notify producer
            }
        }
    }

    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();
        Thread producerThread = new Thread(() -> pc.produce());
        Thread consumerThread = new Thread(() -> pc.consume());

        producerThread.start();
        consumerThread.start();
    }
}
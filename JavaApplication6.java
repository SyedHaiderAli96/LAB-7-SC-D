import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

class ProducerConsumerReentrantLock {
    private final Queue<Integer> buffer = new LinkedList<>();
    private final int LIMIT = 5; // Buffer limit
    private final Lock lock = new ReentrantLock(); // Lock for synchronization
    private final Condition notFull = lock.newCondition(); // Condition when buffer is not full
    private final Condition notEmpty = lock.newCondition(); // Condition when buffer is not empty

    // Method for producing items
    public void produce(int value) throws InterruptedException {
        lock.lock(); // Acquire the lock
        try {
            while (buffer.size() == LIMIT) {
                notFull.await(); // Wait if buffer is full
            }
            buffer.add(value);
            System.out.println("Produced: " + value);
            notEmpty.signal(); // Notify the consumer that the buffer is not empty
        } finally {
            lock.unlock(); // Ensure the lock is released
        }
    }

    // Method for consuming items
    public int consume() throws InterruptedException {
        lock.lock(); // Acquire the lock
        try {
            while (buffer.isEmpty()) {
                notEmpty.await(); // Wait if buffer is empty
            }
            int value = buffer.poll();
            System.out.println("Consumed: " + value);
            notFull.signal(); // Notify the producer that the buffer is not full
            return value;
        } finally {
            lock.unlock(); // Ensure the lock is released
        }
    }

    public static void main(String[] args) {
        ProducerConsumerReentrantLock pc = new ProducerConsumerReentrantLock();

        // Producer thread
        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    pc.produce(i);
                    Thread.sleep(500); // Simulate time taken to produce
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Consumer thread
        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    pc.consume();
                    Thread.sleep(1000); // Simulate time taken to consume
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producerThread.start(); // Start the producer thread
        consumerThread.start(); // Start the consumer thread
    }
}

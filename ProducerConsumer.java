import java.util.LinkedList;
import java.util.Queue;

class ProducerConsumer {
    private Queue<Integer> buffer = new LinkedList<>();
    private final int LIMIT = 5;

    public synchronized void produce(int value) throws InterruptedException {
        while (buffer.size() == LIMIT) {
            wait();
        }
        buffer.add(value);
        System.out.println("Produced: " + value);
        notify();
    }

    public synchronized int consume() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait();
        }
        int value = buffer.poll();
        System.out.println("Consumed: " + value);
        notify();
        return value;
    }

    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();

        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    pc.produce(i);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    pc.consume();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producerThread.start();
        consumerThread.start();
    }
}
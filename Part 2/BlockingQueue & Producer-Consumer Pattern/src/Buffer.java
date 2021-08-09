import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Buffer {
    private BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);

    private void addItem(Integer item) {
        try {
            queue.put(item);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
    }

    private Integer getItem() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
    }

    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Random random = new Random();

        Thread producer = new Thread(() -> {
            while (true) {
                int anInt = random.nextInt(100);
                System.out.println("Produced: " + anInt);
                buffer.addItem(anInt);
                sleepOneSecond();
            }
        });

        Thread consumer = new Thread(() -> {
            while (true) {
                Integer item = buffer.getItem();
                System.out.println("Consumed: " + item);
                sleepOneSecond();
            }
        });

        producer.start();
        consumer.start();
    }

    private static void sleepOneSecond() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
    }
}
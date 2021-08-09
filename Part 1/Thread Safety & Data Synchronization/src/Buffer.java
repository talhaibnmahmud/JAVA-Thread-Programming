import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class Buffer {
    private final static int SIZE = 10;
    private Queue<Integer> queue = new LinkedList<>();
    private final Object lock = new Object();

    void addItem(int item) {
        synchronized (lock) {
            while (queue.size() == SIZE) {
                log("Size if full. Let's wait!");
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new AssertionError(e);
                }
            }

            log("Thread Resumed!");
            log("Adding item: "+ item);

            queue.add(item);

            log("Item added, let's notify");
            lock.notifyAll();
        }
    }

    private void log(String msg) {
        String time = DateTimeFormatter.ISO_LOCAL_TIME.format(LocalDateTime.now());

        System.out.println(time + " Thread: " + Thread.currentThread().getName() + " " + msg);
        System.out.flush();
    }

    void getItem() {
        synchronized (lock) {
            while (queue.isEmpty()) {
                log("Queue is empty. Let's wait!");
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new AssertionError(e);
                }
            }

            log("Thread Resumed!");
            log("Let's consume value");

            Integer values = queue.poll();
            log("Consumed: " + values);
            lock.notifyAll();

        }
    }
}

class BufferDemo {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Random random = new Random();
        int number = random.nextInt(100);

        Thread thread1 = new Thread(() -> buffer.addItem(number));
        Thread thread2 = new Thread(buffer::getItem);

        thread1.start();
        thread2.start();
    }
}
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    private int count;
    private Lock lock = new ReentrantLock();

    private void increment() {
        lock.lock();

        try {
            count = count + 1;
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Counter counter = new Counter();

        counter.increment();
    }
}
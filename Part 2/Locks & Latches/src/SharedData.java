import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SharedData {
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private int value;

    private int getValue() {
        lock.readLock().lock();

        try {
            return value;
        } finally {
            lock.readLock().unlock();
        }
    }

    private void increment() {
        lock.writeLock().lock();

        try {
            value++;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void main(String[] args) {
        SharedData data = new SharedData();

        data.increment();

        System.out.println(data.getValue());
    }
}

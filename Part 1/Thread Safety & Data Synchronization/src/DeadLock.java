import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

class DeadLock {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    void running() {
        synchronized (lock1) {
            log("Acquired Lock 1");
            synchronized (lock2) {
                log("Acquired Lock 2");
                System.out.println("Running!");
                log("About to release Lock 2");
            }
            log("About to release Lock 1");
        }
    }

    void walking() {
        synchronized (lock2) {
            log("Acquired Lock 2");
            synchronized (lock1) {
                log("Acquired Lock 1");
                System.out.println("Walking!");
                log("About to release Lock 1");
            }
            log("About to release Lock 2");
        }
    }

    private void log(String msg) {
        LocalDateTime now = LocalDateTime.now();
        String time = DateTimeFormatter.ISO_LOCAL_TIME.format(now);

        String threadName = Thread.currentThread().getName();
        System.out.println(time + " " + threadName + " : " + msg);
    }
}

class DeadLockDemo {
    public static void main(String[] args) {
        DeadLock deadLock = new DeadLock();

        Thread runningThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                deadLock.running();

                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new AssertionError(e);
                }
            }
        });
        runningThread.setName("Running Thread");

        Thread walkingThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                deadLock.walking();

                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new AssertionError(e);
                }
            }
        });
        walkingThread.setName("Walking Thread");

        runningThread.start();
        walkingThread.start();
    }
}
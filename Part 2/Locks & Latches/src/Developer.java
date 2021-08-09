import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Developer implements Runnable{
    private CountDownLatch latch;
    private String name;

    Developer(String name, CountDownLatch latch) {
        this.latch = latch;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("Task assigned to developer: " + name);

        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }

        System.out.println("Task finished by developer: " + name);

        this.latch.countDown();
    }
}

class Tester implements Runnable {
    private CountDownLatch latch;
    private String name;

    Tester(String name, CountDownLatch latch) {
        this.latch = latch;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println("Tester is waiting for developers to finish their work! " + name);
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }

        System.out.println("Testing is done by tester: " + name);
    }
}
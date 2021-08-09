import java.util.concurrent.TimeUnit;

public class ThreadLifeCycle {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            try {
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.setName("MyThread # 1");
        thread1.start();

        Thread thread2 = new Thread(() -> {
            while (!Thread.interrupted()) {
                System.out.println("Running");
            }
        });

        thread2.setName("MyThread # 2");
        thread2.start();

        try {
            TimeUnit.MINUTES.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread2.interrupt();
    }
}

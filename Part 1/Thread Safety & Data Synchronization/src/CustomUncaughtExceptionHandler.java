import java.util.concurrent.TimeUnit;

public class CustomUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Exception has been thrown form " + t.getName());
        System.out.println("Exception message: " + e.getMessage());
    }
}

class ThreadUncheckedExceptionExample {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted due to " + e.getMessage());
            }

            throw new RuntimeException("GoodBye cruel world!");
        });

        thread.setUncaughtExceptionHandler(new CustomUncaughtExceptionHandler());
        thread.start();
    }
}
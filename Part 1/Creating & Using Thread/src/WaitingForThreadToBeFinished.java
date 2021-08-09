import java.util.concurrent.TimeUnit;

public class WaitingForThreadToBeFinished {
    private static boolean doneWorking = false;

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            find20FibonacciNumber();
            doneWorking = true;
        });
        thread.start();

        try {
            TimeUnit.MILLISECONDS.sleep(500);

            if(doneWorking) {
                System.out.println("\nThread thread has finished the work!");
            } else {
                System.out.println("\nThread thread didn't finish the work");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void find20FibonacciNumber() {
        for(int value = 1; value <= 20; value++) {
            System.out.println(fib(value) + ", ");
        }
    }

    private static int fib(int value) {
        if(value == 1 || value == 2) {
            return 1;
        }
        return fib(value - 1) + fib(value - 2);
    }
}

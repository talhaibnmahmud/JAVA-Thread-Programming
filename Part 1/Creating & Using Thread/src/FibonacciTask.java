import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FibonacciTask implements Runnable {
    private long n;
    private String id;

    private FibonacciTask(long n, String id) {
        this.n = n;
        this.id = id;
    }

    private BigDecimal fib(long n) {
        if(n == 0) {
            return BigDecimal.ZERO;
        } else if(n == 1) {
            return BigDecimal.ONE;
        } else {
            return fib(n - 1).add(fib(n - 2));
        }
    }

    @Override
    public void run() {
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

        System.out.println("String task " + id + " at " + isoFormatter.format(LocalDateTime.now()));
        BigDecimal fib = fib(n);
        System.out.println(fib);
        System.out.println("Ending task " + id + " at " + isoFormatter.format(LocalDateTime.now()));
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new FibonacciTask(45, "FibonacciTask# 1"));
        Thread thread2 = new Thread(new FibonacciTask(45, "FibonacciTask# 2"));
        Thread thread3 = new Thread(new FibonacciTask(45, "FibonacciTask# 3"));
        Thread thread4 = new Thread(new FibonacciTask(45, "FibonacciTask# 4"));
        Thread thread5 = new Thread(new FibonacciTask(45, "FibonacciTask# 5"));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
}

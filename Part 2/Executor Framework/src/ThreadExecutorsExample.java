import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class ThreadExecutorsExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        IntStream.range(0, 100).forEach(i -> {
            Runnable task = () -> System.out.println("Thread: " + Thread.currentThread().getName() + " is executing task: " + i);

            executorService.execute(task);
        });

        executorService.shutdown();
    }
}

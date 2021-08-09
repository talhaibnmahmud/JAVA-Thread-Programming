import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TextCopierApp {
    public static void main(String[] args) {
        TextCopier jugbd = new TextCopier("https://jugbd.org/");
        TextCopier masterDevSkill = new TextCopier("https://masterdevskills.com");

        List<TextCopier> tasks = Arrays.asList(jugbd, masterDevSkill);
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            List<Future<String>> futures = executorService.invokeAll(tasks);

            for (Future<String> future : futures) {
                String result = future.get();
                System.out.println(result);
            }
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
    }
}

import java.util.List;

public class Core {
    public static void main(String[] args) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        long availableMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();

        int version = Runtime.version().feature();
        List<Integer> versions = Runtime.version().version();

        System.out.println("Available Processors: " + availableProcessors);
        System.out.println("Available Memory: " + availableMemory);
        System.out.println("Total Memory: " + totalMemory);
        System.out.println("Max Memory: " + maxMemory);

        System.out.println(versions);
        System.out.println(version);
    }
}

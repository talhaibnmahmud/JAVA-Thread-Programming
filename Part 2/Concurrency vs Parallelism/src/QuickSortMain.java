import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class QuickSortMain {
    public static void main(String[] args) {
        List<Integer> randomNumbers = initializeSomeRandomNumbers();
        QuickSortAction<Integer> quickSort = new QuickSortAction<>(randomNumbers);

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        forkJoinPool.invoke(quickSort);

        System.out.println(randomNumbers);
    }

    private static List<Integer> initializeSomeRandomNumbers() {
        final int SIZE = 10000;
        List<Integer> randomNumbers = new ArrayList<>(SIZE);

        for(int i = 0; i < SIZE; i++) {
            int value = (int) (Math.random() * 1000);
            randomNumbers.add(value);
        }

        return randomNumbers;
    }
}

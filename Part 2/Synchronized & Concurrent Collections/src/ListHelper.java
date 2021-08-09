import java.util.*;

public class ListHelper {
    List<Integer> numbers = Collections.synchronizedList(new ArrayList<>());
    Iterator<Integer> iterator = numbers.iterator();

    void getNumber() {
        synchronized (numbers) {
            for (Integer integer : numbers) {
                process(integer);
            }
        }
    }

    private void process(Integer integer) {
    }

    void iterate() {
        while (iterator.hasNext()) {
            Integer next = iterator.next();
        }
    }


    public static <E> E getLast(Vector<E> list) {
        synchronized (list) {
            int lastIndex = list.size() - 1;

            return list.get(lastIndex);
        }
    }

    public static <E> void removeLast(Vector<E> list) {
        synchronized (list) {
            int lastIndex = list.size() - 1;

            list.remove(lastIndex);
        }
    }
}

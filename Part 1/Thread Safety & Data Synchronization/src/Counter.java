class Counter {
    private final Object lock = new Object();
    private int count = 0;

    void increment() {
        synchronized (lock) {
            count++;
        }
    }
    void decrement() {
        synchronized (lock) {
            count--;
        }
    }
}

class StaticCounter {
    private static int count;

    //Synchronized static method
    public static synchronized void incrementCounter() {
        count = count + 1;
    }

    //Synchronized code block in static method
    public void increment() {
        System.out.println("Going to increment counter.");
        synchronized (StaticCounter.class) {
            count = count + 1;
        }
    }
}

class CounterExample {
    private int count;

    //Synchronized method
    public synchronized void incrementCounter() {
        this.count = count + 1;
    }

    //Synchronized code block
    public void increment() {
        System.out.println("Going to increment counter.");
        synchronized (this) {
            count = count + 1;
        }
    }

    //Explicit Lock
    private final Object lock = new Object();
    public void inc() {
        synchronized (lock) {
            count++;
        }
    }
}

class CounterMain {
    public static void main(String[] args) {
        Counter counter = new Counter();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                counter.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                counter.decrement();
            }
        });

        long start = System.currentTimeMillis();

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }

        long end = System.currentTimeMillis();

        System.out.println("Duration: " + (end - start));
    }
}
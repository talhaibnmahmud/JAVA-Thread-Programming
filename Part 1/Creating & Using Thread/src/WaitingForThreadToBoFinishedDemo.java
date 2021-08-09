public class WaitingForThreadToBoFinishedDemo {
    public static void main(String[] args) {
        Thread fibonacciThread = new Thread(WaitingForThreadToBoFinishedDemo::find20FibonacciNumber);
        fibonacciThread.start();

        try {
            fibonacciThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Thread is terminated.");
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

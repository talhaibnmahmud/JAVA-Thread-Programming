public class HiHello {
    private static boolean s = false;
    public static void main(String[] args) {
        Thread thread1 = new Thread(HiHello::run2);
        thread1.start();

        Thread thread2 = new Thread(HiHello::run);
        thread2.start();
    }

    private static void run() {
        s = true;
        System.out.println("Hi");
    }

    private static void run2() {
        while (!s) {
        }
        System.out.println("Hello");
    }
}
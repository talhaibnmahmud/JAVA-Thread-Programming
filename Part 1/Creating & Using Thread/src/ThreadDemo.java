public class ThreadDemo {
    public static void main(String[] args) {
//        Anonymous Inner Class
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        });
        thread1.start();

//        Lambda Expression
        Thread thread2 = new Thread(() -> doWork());
        thread2.start();

//        Method Reference
//        Latest Update
        Thread thread3 = new Thread(ThreadDemo::doWork);
        thread3.start();
    }



    private static void doWork() {
        System.out.println("Doing some important task.");
    }
}

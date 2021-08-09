import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Watch implements Runnable {
    private volatile boolean running = true;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:s a");
    @Override
    public void run() {
        while (running) {
            printCurrentTime();
            sleepOneSecond();

            if(Thread.interrupted()) {
                System.out.println("Thread is Interrupted.");
                return;
            }
        }
    }

    private void printCurrentTime() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:s a");
        String formattedCurrentTime = LocalDateTime.now().format(FORMATTER);

        System.out.println(formattedCurrentTime);
    }

    private void sleepOneSecond() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void shutDown() {this.running = false;}
}

class WatchDemo {
    public static void main(String[] args) throws InterruptedException {
        Watch watch = new Watch();
        Thread watchThread = new Thread(watch);
        watchThread.start();

        Thread.sleep(500);
//        watchThread.interrupt();
        watch.shutDown();
    }
}
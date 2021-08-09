import java.util.concurrent.TimeUnit;

public class CricketScoreChecker extends Thread {
    private volatile boolean keepChecking = true;

    @Override
    public void run() {
        while (keepChecking) {
            checkScoreAndPrintResult();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkScoreAndPrintResult() {
        System.out.println("Checking Score... ");
    }

    void shutDown() {this.keepChecking = false;}
}

class CricketScoreCheckerDemo {
    public static void main(String[] args) {
        CricketScoreChecker cricketScoreChecker = new CricketScoreChecker();
        cricketScoreChecker.start();

        try {
            TimeUnit.HOURS.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cricketScoreChecker.shutDown();
    }
}
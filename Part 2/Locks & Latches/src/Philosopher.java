import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Philosopher implements Runnable {
    private String name;
    private final Lock leftFork;
    private final Lock rightFork;

    Philosopher(String name, Lock leftFork, Lock rightFork) {
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void think() {log("Thinking");}

    private void eat() {
//        assume, eat requires some time
//        Let's put a random number
        try {
            log("Eating");
            int eatingTime = getRandomEatingTime();
            TimeUnit.NANOSECONDS.sleep(eatingTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
    }

    private int getRandomEatingTime() {
        Random random = new Random();
        return random.nextInt(500) + 50;
    }

    private void log(String msg) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

        String time = formatter.format(LocalDateTime.now());
        String thread = Thread.currentThread().getName();

        System.out.println(time + " " + thread + " " + name + " " + msg);

        System.out.flush();
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 10) {
            keepThinkingAndEating();
            i++;
        }
    }

    private void keepThinkingAndEating() {
        think();

        if(leftFork.tryLock()) {
            try {
                log("Grabbed Left Fork");

                if (rightFork.tryLock()) {
                    try {
                        log("Grabbed Right Fork");
                        eat();
                    } finally {
                        log("Put down Right Fork");
                        rightFork.unlock();
                    }
                }
            } finally {
                log("Put down Left Fork");
                leftFork.unlock();
            }
        }
    }
}
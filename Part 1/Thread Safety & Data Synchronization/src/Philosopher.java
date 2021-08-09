import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Philosopher extends Thread {
    private String name;
    private final Fork leftFork;
    private final Fork rightFork;

    Philosopher(String name, Fork leftFork, Fork rightFork) {
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void think() {log("thinking");}

    private void eat() {
        try {
            log("eating");
            int eatingTime = getRandomEatingTime();
            TimeUnit.MILLISECONDS.sleep(eatingTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
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
        synchronized (leftFork) {
            log("grabbed left fork");
            synchronized (rightFork) {
                log("grabbed right fork");
                eat();
                log("put down right fork");
            }
            log("put down left fork");
        }
    }

    private static int getRandomEatingTime() {
        Random random = new Random();
        return random.nextInt(100) + 50;
    }

    private void log(String msg) {
        String time = DateTimeFormatter.ISO_LOCAL_TIME.format(LocalDateTime.now());
        System.out.println(time + " Thread: " + Thread.currentThread().getName() + " " + name + " " + msg);

        System.out.flush();
    }
}

class Fork {
}

class PhilosopherMain {
    public static void main(String[] args) {
        Fork[] forks = new Fork[5];

        for(int i = 0; i < forks.length; i++) {
            forks[i] = new Fork();
        }

        Philosopher[] philosophers = new Philosopher[5];
        for(int i = 0; i < philosophers.length; i++) {
            Fork leftFork = forks[i];
            Fork rightFork = forks[(i + 1) % forks.length];

            if(i == philosophers.length - 1) {
                philosophers[i] = new Philosopher("Philosopher " + (i + 1), rightFork, leftFork);
            } else {
                philosophers[i] = new Philosopher("Philosopher " + (i + 1), leftFork, rightFork);
            }

            philosophers[i].start();
        }
    }
}
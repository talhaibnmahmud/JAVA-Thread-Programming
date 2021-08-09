import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

class BankAccount {
    private volatile long balance;

    BankAccount(long balance) {
        this.balance = balance;
    }

    synchronized void withdraw(long amount) {
        System.out.println("Inside withdraw method! Lock acquired");
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw  new AssertionError(e);
        }

        System.out.println("Withdrawing amount: " + amount);
        long newBalance = balance - amount;
        System.out.println("New balance is: " + newBalance);
        balance = newBalance;

        System.out.println("End of withdraw method! Releasing the Lock");
    }

    synchronized void deposit(long amount) {
        System.out.println("Inside deposit method! Lock acquired");
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw  new AssertionError(e);
        }

        System.out.println("Depositing: " + amount);
        long newBalance = balance + amount;
        System.out.println("New Balance: " + newBalance);
        balance = newBalance;

        System.out.println("End of deposit method! Releasing the Lock");
    }

    long getBalance() {return balance;}
}

class BankAccountMain {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(100);
        DateTimeFormatter isoTimeFormatter = DateTimeFormatter.ISO_TIME;

        /*Thread depositThread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccount.deposit(100);
                sleep();
            }
        });

        Thread withdrawThread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                bankAccount.withdraw(100);
                sleep();
            }
        });*/

        Thread withdrawThread = new Thread(() -> {
            System.out.println(
                    Thread.currentThread().getName() +
                            " started at " +
                            isoTimeFormatter.format(LocalDateTime.now())
            );
            bankAccount.withdraw(100);
        });
        withdrawThread.setName("Withdraw Thread");
        Thread depositThread = new Thread(() -> {
            System.out.println(
                    Thread.currentThread().getName() +
                            " started at " +
                            isoTimeFormatter.format(LocalDateTime.now())
            );
            bankAccount.deposit(100);
        });
        depositThread.setName("Deposit Thread");

        depositThread.start();
        withdrawThread.start();

        try {
            depositThread.join();
            withdrawThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }

        System.out.println("Current balance available in account: " + bankAccount.getBalance());
    }

    /*private static void sleep() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
    }*/
}
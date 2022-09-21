package ua.com.andromeda.threads;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MicrochipProgrammer implements Runnable {
    private static final Random RANDOM = new Random();
    private static final AtomicInteger amountDoneWork = new AtomicInteger(0);

    @SneakyThrows
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + "'s started job");
        while (amountDoneWork.get() < 100) {
            if (RANDOM.nextDouble() <= 0.3) {
                System.out.println("Oops, " + name + " has broken chip.");
                amountDoneWork.set(0);
                TimeUnit.SECONDS.sleep(2);
                continue;
            }
            int broughtGas = RANDOM.nextInt(25, 36);
            amountDoneWork.addAndGet(broughtGas);
            System.out.println(name + "'s done work on " + broughtGas + "%");
            System.out.println("Total amount of done work ==> " + amountDoneWork + "%");
            TimeUnit.SECONDS.sleep(2);
        }
        System.out.println(name + "'s done job");
    }
}

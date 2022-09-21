package ua.com.andromeda.threads;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Builder implements Runnable {
    private static final Random RANDOM = new Random();
    private static final AtomicInteger amountDoneWork = new AtomicInteger(0);

    @SneakyThrows
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + "'s started job");
        while (amountDoneWork.get() < 100) {
            int broughtGas = RANDOM.nextInt(5, 11);
            amountDoneWork.addAndGet(broughtGas);
            System.out.println(name + "'s done work on " + broughtGas + "%");
            System.out.println("Total amount of done work ==> " + amountDoneWork + "%");
            TimeUnit.SECONDS.sleep(2);
        }
        System.out.println(name + "'s done job");
    }
}

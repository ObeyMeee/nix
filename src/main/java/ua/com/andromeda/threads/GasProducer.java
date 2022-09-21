package ua.com.andromeda.threads;

import lombok.Getter;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class GasProducer implements Runnable {

    private final AtomicInteger totalGas = new AtomicInteger(0);
    private static final Random RANDOM = new Random();

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + "'s started job");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                break;
            }
            int broughtGas = RANDOM.nextInt(500, 1001);
            totalGas.addAndGet(broughtGas);
            System.out.println(name + "'s brought " + broughtGas + " amount of gas");
            System.out.println("Total gas ==> " + totalGas);
        }
    }
}

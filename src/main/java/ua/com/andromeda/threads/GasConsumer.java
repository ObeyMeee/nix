package ua.com.andromeda.threads;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GasConsumer implements Runnable {
    private static final Random RANDOM = new Random();

    private final GasProducer gasProducer;

    public GasConsumer(GasProducer gasProducer) {
        this.gasProducer = gasProducer;
    }

    @SneakyThrows
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + "'s started job");
        AtomicInteger totalGas;
        for (int i = 0; i < 10; i++) {
            totalGas = gasProducer.getTotalGas();
            int consumedGas = RANDOM.nextInt(350, 701);
            totalGas.addAndGet(-consumedGas);
            System.out.println(name + "'s consumed " + consumedGas + " amount of gas");
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(name + "'s finished job");
    }
}

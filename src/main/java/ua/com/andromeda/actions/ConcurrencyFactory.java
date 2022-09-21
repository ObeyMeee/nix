package ua.com.andromeda.actions;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrencyFactory implements Command {
    private static final Random RANDOM = new Random();
    private final AtomicInteger countGas = new AtomicInteger(0);

    @SneakyThrows
    @Override
    public void execute() {
        Thread gasConsumer = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " has started job");
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int gotGas = RANDOM.nextInt(500, 1001);
                countGas.addAndGet(gotGas);
                System.out.println("Bot-1 brought " + gotGas + " gas");
                System.out.println("Total gas ==> " + countGas.get());
            }
        });
        gasConsumer.setName("Bot-1");
        gasConsumer.setDaemon(true);
        gasConsumer.start();
        A target = new A();
        Thread thread2 = new Thread(target);
        Thread thread3 = new Thread(target);
        thread2.setName("Bot-2");
        thread3.setName("Bot-3");
        thread2.start();
        thread3.start();
        thread2.join();
        thread3.join();

        Thread thread4 = new Thread(new Runnable() {
            int count = 0;

            @SneakyThrows
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " has started job");
                while (count < 100) {
                    int amount = RANDOM.nextInt(25, 36);
                    if (RANDOM.nextDouble() <= 0.3) {
                        System.out.println("Oops... " + threadName + " has broken microchip");
                        count = 0;
                        continue;
                    }
                    synchronized (this) {
                        count = count + amount;
                    }
                    System.out.println(threadName + " increased his job on " + amount);
                    System.out.println("Current job ==> " + count);
                    TimeUnit.SECONDS.sleep(1);
                }
                System.out.println(threadName + " has finished job");
            }
        });
        thread4.setName("Bot-4");
        thread4.start();
        thread4.join();
        Thread thread5 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " has started job");
                for (int i = 0; i < 10; i++) {
                    int neededGas = RANDOM.nextInt(350, 701);
                    System.out.println("neededGas for " + threadName + " ==> " + neededGas);
                    countGas.addAndGet(-neededGas);
                    System.out.println("current gas ==> " + countGas.get());
                    TimeUnit.SECONDS.sleep(1);
                }
                System.out.println(threadName + " has finished job");
                System.out.println("Bot-1" + " has finished job");
                gasConsumer.interrupt();
            }
        });
        thread5.setName("Bot-5");
        thread5.start();
        thread5.join();
    }

    class A implements Runnable {
        private final AtomicInteger count = new AtomicInteger(0);
        private static final Random RANDOM = new Random();


        @SneakyThrows
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " has started job");
            int doneJob;
            while (count.get() < 100) {
                doneJob = RANDOM.nextInt(5, 11);
                count.addAndGet(doneJob);
                System.out.println(threadName + " increased his job on " + doneJob);
                System.out.println("Current job ==> " + count.get());
                TimeUnit.SECONDS.sleep(2);
            }
            System.out.println(threadName + " has finished job");
        }
    }
}

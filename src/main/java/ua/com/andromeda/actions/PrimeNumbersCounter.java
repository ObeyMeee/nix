package ua.com.andromeda.actions;

import lombok.SneakyThrows;
import org.apache.commons.math3.primes.Primes;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.stream.IntStream;

public class PrimeNumbersCounter implements Command {
    @Override
    public void execute() {
        List<Integer> numbers = getAndFillNumbersList();
        int size = numbers.size();
        int firstPartAmount = countPrimeNumbersInRange(numbers, 0, size / 2);
        int secondPartAmount = countPrimeNumbersInRange(numbers, size / 2, size);
        System.out.println("Amount of prime numbers in list ==> " + (firstPartAmount + secondPartAmount));
    }

    private List<Integer> getAndFillNumbersList() {
        return IntStream.range(0, 100)
                .boxed()
                .toList();
    }

    @SneakyThrows
    private int countPrimeNumbersInRange(List<Integer> numbers, int start, int end) {
        FutureTask<Integer> countFirstPartPrimeNumbersTask = getTask(numbers, start, end);
        new Thread(countFirstPartPrimeNumbersTask).start();
        return countFirstPartPrimeNumbersTask.get();
    }

    private FutureTask<Integer> getTask(List<Integer> numbers, int start, int end) {
        Callable<Integer> primeNumbers = () ->
                (int) IntStream.range(start, end)
                        .filter(i -> isPrime(numbers.get(i)))
                        .count();

        return new FutureTask<>(primeNumbers);
    }

    private boolean isPrime(int number) {
        return Primes.isPrime(number);
    }
}

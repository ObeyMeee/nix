package ua.com.andromeda.actions;

import lombok.SneakyThrows;

public class MultithreadingPrinter implements Command {
    @Override
    @SneakyThrows
    public void execute() {
        for (int i = 49; i >= 0; i--) {
            int finalI = i;
            Thread thread = new Thread(() -> System.out.println("Hello from thread-" + finalI));
            thread.start();
            thread.join();
        }
    }
}

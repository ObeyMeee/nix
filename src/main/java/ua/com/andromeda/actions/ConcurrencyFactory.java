package ua.com.andromeda.actions;

import lombok.SneakyThrows;
import ua.com.andromeda.threads.Builder;
import ua.com.andromeda.threads.GasConsumer;
import ua.com.andromeda.threads.GasProducer;
import ua.com.andromeda.threads.MicrochipProgrammer;

public class ConcurrencyFactory implements Command {

    @SneakyThrows
    @Override
    public void execute() {
        GasProducer producer = new GasProducer();
        Thread gasProducer = new Thread(producer);
        gasProducer.setName("Bot-1");
        gasProducer.setDaemon(true);
        gasProducer.start();

        Builder builder = new Builder();
        Thread builder2 = new Thread(builder);
        Thread builder3 = new Thread(builder);
        builder2.setName("Bot-2");
        builder3.setName("Bot-3");
        builder2.start();
        builder3.start();
        builder2.join();
        builder3.join();

        Thread microchipProgrammer = new Thread(new MicrochipProgrammer());
        microchipProgrammer.setName("Bot-4");
        microchipProgrammer.start();
        microchipProgrammer.join();

        Thread gasConsumer = new Thread(new GasConsumer(producer));
        gasConsumer.setName("Bot-5");
        gasConsumer.start();
        gasConsumer.join();
        gasProducer.interrupt();
    }
}

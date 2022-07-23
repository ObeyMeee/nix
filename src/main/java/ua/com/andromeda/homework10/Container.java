package ua.com.andromeda.homework10;

import ua.com.andromeda.homework10.model.Vehicle;

import java.math.BigDecimal;
import java.util.Random;

public class Container<E extends Vehicle> {
    private final E auto;

    public Container(E auto) {
        this.auto = auto;
    }

    public E getAuto() {
        return auto;
    }

    public void generateDiscount() {
        BigDecimal discount = BigDecimal.valueOf(new Random().nextDouble(0.1, 0.3));
        System.out.println("generated discount ==> " + discount + "%");
        auto.setPrice(auto.getPrice().multiply(BigDecimal.ONE.subtract(discount)));
    }

    public <T extends Number> void increasePrice(T value) {
        double doubleValue = value.doubleValue();
        if (doubleValue < 0) {
            throw new IllegalArgumentException("value mut be more than 0!");
        }

        auto.setPrice(auto.getPrice().add(BigDecimal.valueOf(doubleValue)));
    }

    @Override
    public String toString() {
        return "Container{" +
                "auto=" + auto +
                '}';
    }
}

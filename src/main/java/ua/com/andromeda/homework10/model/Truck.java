package ua.com.andromeda.homework10.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Truck extends Auto {
    private int maxCarryingCapacity;

    public Truck(String model, Manufacturer manufacturer,
                 BigDecimal price, String bodyType, int maxCarryingCapacity) {

        super(model, manufacturer, price, bodyType);
        this.maxCarryingCapacity = maxCarryingCapacity;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id='" + id + '\'' +
                ", maxCarryingCapacity=" + maxCarryingCapacity +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }
}

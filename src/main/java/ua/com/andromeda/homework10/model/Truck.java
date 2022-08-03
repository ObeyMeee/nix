package ua.com.andromeda.homework10.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Truck extends Auto {
    private int maxCarryingCapacity;

    public Truck(String model, Manufacturer manufacturer,
                 BigDecimal price, String bodyType, List<String> details, int maxCarryingCapacity) {

        super(model, manufacturer, price, bodyType, details);
        this.maxCarryingCapacity = maxCarryingCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Truck truck)) return false;
        return Objects.equals(model, truck.model)
                && Objects.equals(price, truck.price)
                && manufacturer == truck.manufacturer
                && Objects.equals(bodyType, truck.bodyType)
                && Objects.equals(maxCarryingCapacity, truck.maxCarryingCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, price, manufacturer, bodyType, maxCarryingCapacity);
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

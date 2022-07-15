package ua.com.andromeda.homework10.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SportCar extends Auto {
    private int maxSpeed;

    public SportCar(String model, Manufacturer manufacturer, BigDecimal price, String bodyType, int maxSpeed) {
        super(model, manufacturer, price, bodyType);
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        return "SportCar{" +
                "id='" + id + '\'' +
                ", maxSpeed=" + maxSpeed +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }
}

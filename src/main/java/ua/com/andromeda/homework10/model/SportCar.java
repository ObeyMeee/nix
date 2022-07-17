package ua.com.andromeda.homework10.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class SportCar extends Auto {
    private int maxSpeed;

    public SportCar(String model, Manufacturer manufacturer, BigDecimal price, String bodyType, int maxSpeed) {
        super(model, manufacturer, price, bodyType);
        this.maxSpeed = maxSpeed;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !obj.getClass().getSimpleName().equals(SportCar.class.getSimpleName())) {
            return false;
        }
        SportCar other = (SportCar) obj;

        return getManufacturer().equals(other.getManufacturer()) &&
                getModel().equals(other.getModel()) &&
                getPrice().equals(other.getPrice()) &&
                maxSpeed == other.getMaxSpeed() &&
                getBodyType().equals(other.getBodyType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getModel(), getBodyType(), getManufacturer(), getPrice(), maxSpeed);
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

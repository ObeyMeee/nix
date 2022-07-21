package ua.com.andromeda.homework10.dto;

import ua.com.andromeda.homework10.model.Manufacturer;

import java.math.BigDecimal;
import java.util.Objects;

public class SportCarDto {
    private final String model;
    private final BigDecimal price;
    private final Manufacturer manufacturer;
    private final String bodyType;
    private final int speed;

    public SportCarDto(String model, BigDecimal price, Manufacturer manufacturer, String bodyType, int speed) {
        this.model = model;
        this.price = price;
        this.manufacturer = manufacturer;
        this.bodyType = bodyType;
        this.speed = speed;
    }

    public String getModel() {
        return model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public String getBodyType() {
        return bodyType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SportCarDto autoDto)) return false;
        return Objects.equals(model, autoDto.model)
                && Objects.equals(price, autoDto.price)
                && manufacturer == autoDto.manufacturer
                && Objects.equals(bodyType, autoDto.bodyType)
                && Objects.equals(speed, autoDto.speed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, price, manufacturer, bodyType, speed);
    }

    @Override
    public String toString() {
        return "AutoDto{" +
                "model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                ", bodyType='" + bodyType + '\'' +
                ", speed='" + speed + '\'' +
                '}';
    }
}
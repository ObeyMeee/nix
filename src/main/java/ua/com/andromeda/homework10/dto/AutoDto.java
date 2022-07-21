package ua.com.andromeda.homework10.dto;

import ua.com.andromeda.homework10.model.Manufacturer;

import java.math.BigDecimal;
import java.util.Objects;

public class AutoDto {
    private final String model;
    private final BigDecimal price;
    private final Manufacturer manufacturer;
    private final String bodyType;

    public AutoDto(String model, BigDecimal price, Manufacturer manufacturer, String bodyType) {
        this.model = model;
        this.price = price;
        this.manufacturer = manufacturer;
        this.bodyType = bodyType;
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
        if (!(o instanceof AutoDto autoDto)) return false;
        return Objects.equals(model, autoDto.model) && Objects.equals(price, autoDto.price) && manufacturer == autoDto.manufacturer && Objects.equals(bodyType, autoDto.bodyType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, price, manufacturer, bodyType);
    }

    @Override
    public String toString() {
        return "AutoDto{" +
                "model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                ", bodyType='" + bodyType + '\'' +
                '}';
    }
}
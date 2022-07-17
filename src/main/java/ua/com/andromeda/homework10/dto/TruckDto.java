package ua.com.andromeda.homework10.dto;

import ua.com.andromeda.homework10.model.Manufacturer;

import java.math.BigDecimal;
import java.util.Objects;

public class TruckDto {
    private final String model;
    private final BigDecimal price;
    private final Manufacturer manufacturer;
    private final String bodyType;
    private final int carryingCapacity;

    public TruckDto(String model, BigDecimal price, Manufacturer manufacturer, String bodyType, int carryingCapacity) {
        this.model = model;
        this.price = price;
        this.manufacturer = manufacturer;
        this.bodyType = bodyType;
        this.carryingCapacity = carryingCapacity;
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
        if (!(o instanceof TruckDto truckDto)) return false;
        return Objects.equals(model, truckDto.model)
                && Objects.equals(price, truckDto.price)
                && manufacturer == truckDto.manufacturer
                && Objects.equals(bodyType, truckDto.bodyType)
                && Objects.equals(carryingCapacity, truckDto.carryingCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, price, manufacturer, bodyType, carryingCapacity);
    }

    @Override
    public String toString() {
        return "AutoDto{" +
                "model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                ", bodyType='" + bodyType + '\'' +
                ", carryingCapacity='" + carryingCapacity + '\'' +
                '}';
    }
}
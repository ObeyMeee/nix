package ua.com.andromeda.homework10.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class Vehicle {
    protected final String id;
    protected String model;
    protected BigDecimal price;
    protected Manufacturer manufacturer;
    protected List<String> details;

    protected Vehicle(String model, Manufacturer manufacturer, BigDecimal price, List<String> details) {
        this.id = UUID.randomUUID().toString();
        this.model = model;
        this.manufacturer = manufacturer;
        this.price = price;
        this.details = details;
    }
}

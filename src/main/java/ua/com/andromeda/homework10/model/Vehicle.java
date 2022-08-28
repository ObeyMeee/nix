package ua.com.andromeda.homework10.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public abstract class Vehicle {
    protected String id;
    protected String model;
    protected BigDecimal price;
    protected Manufacturer manufacturer;
    protected String invoiceId;
    protected List<String> details;
}

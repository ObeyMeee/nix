package ua.com.andromeda.homework10.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Auto extends Vehicle {
    protected String bodyType;
    protected int count;
    private Engine engine;
    private String currency;
    private LocalDateTime created;

    public Auto(){

    }
    public Auto(String model, Manufacturer manufacturer, BigDecimal price, String bodyType, List<String> details) {
        super(model, manufacturer, price, details);
        this.bodyType = bodyType;
    }

    public Auto(String model, Manufacturer manufacturer, BigDecimal price, List<String> details, String bodyType) {
        super(model, manufacturer, price, details);
        this.bodyType = bodyType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !obj.getClass().getSimpleName().equals(Auto.class.getSimpleName())) {
            return false;
        }
        Auto other = (Auto) obj;

        return getManufacturer().equals(other.getManufacturer()) &&
                getModel().equals(other.getModel()) &&
                getPrice().equals(other.getPrice()) &&
                getEngine().equals(other.getEngine()) &&
                getCount() == other.getCount() &&
                getBodyType().equals(other.getBodyType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getModel(), getBodyType(), getManufacturer(), getPrice());
    }

    @Override
    public String toString() {
        return "Auto{" +
                "bodyType='" + bodyType + '\'' +
                ", id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", details=" + details +
                ", engine=" + engine +
                ", count=" + count +
                ", created=" + created+
                ", currency=" + currency +
                ", manufacturer=" + manufacturer +
                '}';
    }
}

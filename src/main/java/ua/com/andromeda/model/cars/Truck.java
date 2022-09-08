package ua.com.andromeda.model.cars;

import lombok.Getter;
import lombok.Setter;
import ua.com.andromeda.model.Detail;
import ua.com.andromeda.model.Engine;
import ua.com.andromeda.model.Manufacturer;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Truck extends Auto {
    @Column(name = "max_carrying_capacity")
    private int maxCarryingCapacity;

    public Truck() {

    }

    public Truck(Auto auto) {
        this.id = auto.getId();
        this.bodyType = auto.getBodyType();
        this.count = auto.getCount();
        this.engine = auto.getEngine();
        this.currency = auto.getCurrency();
        this.created = LocalDateTime.now();
        this.model = auto.getModel();
        this.price = auto.getPrice();
        this.manufacturer = auto.getManufacturer();
        this.details = auto.getDetails();
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
                ", bodyType='" + bodyType + '\'' +
                ", count=" + count +
                ", engine=" + engine +
                ", currency='" + currency + '\'' +
                ", created=" + created +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                ", details=" + details +
                '}';
    }

    public static class Builder {
        private final Truck truck;

        public Builder() {
            truck = new Truck();
        }

        public Truck.Builder getBuilder() {
            return new Truck.Builder();
        }

        public Truck.Builder setPrice(BigDecimal price) {
            this.truck.price = price;
            return this;
        }

        public Truck.Builder setManufacturer(Manufacturer manufacturer) {
            this.truck.manufacturer = manufacturer;
            return this;
        }

        public Truck.Builder setModel(String model) {
            this.truck.model = model;
            return this;
        }

        public Truck.Builder setDetails(List<Detail> details) {
            this.truck.details = details;
            return this;
        }

        public Truck.Builder setMaxCarryingCapacity(int maxCarryingCapacity) {
            this.truck.maxCarryingCapacity = maxCarryingCapacity;
            return this;
        }

        public Truck.Builder setEngine(Engine engine) {
            this.truck.engine = engine;
            return this;
        }

        public Truck.Builder setCurrency(String currency) {
            this.truck.currency = currency;
            return this;
        }

        public Truck.Builder setCreated(LocalDateTime created) {
            this.truck.created = created;
            return this;
        }

        public Truck.Builder setBodyType(String bodyType) {
            bodyType = bodyType.trim();
            if (bodyType.length() > 20 || bodyType.length() <= 0) {
                throw new IllegalArgumentException("bodyType must be less 20 characters long");
            }
            this.truck.bodyType = bodyType;
            return this;
        }

        public Truck.Builder setCount(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("count must be greater than zero");
            }
            this.truck.count = count;
            return this;
        }

        public Builder setId(String id) {
            this.truck.id = id;
            return this;
        }

        public Truck build() {
            Objects.requireNonNull(truck.price);
            return truck;
        }
    }
}

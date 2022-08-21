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
    protected Engine engine;
    protected String currency;
    protected LocalDateTime created;

    protected Auto() {

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
                ", created=" + created +
                ", currency=" + currency +
                ", manufacturer=" + manufacturer +
                '}';
    }

    public static class Builder {
        private final Auto auto;

        public Builder() {
            auto = new Auto();
        }

        public Builder getBuilder() {
            return new Builder();
        }

        public Builder setPrice(BigDecimal price) {
            this.auto.price = price;
            return this;
        }

        public Builder setManufacturer(Manufacturer manufacturer) {
            this.auto.manufacturer = manufacturer;
            return this;
        }

        public Builder setModel(String model) {
            this.auto.model = model;
            return this;
        }

        public Builder setDetails(List<String> details) {
            this.auto.details = details;
            return this;
        }

        public Builder setBodyType(String bodyType) {
            bodyType = bodyType.trim();
            if (bodyType.length() > 20 || bodyType.length() <= 0) {
                throw new IllegalArgumentException("bodyType must be less 20 characters long");
            }
            this.auto.bodyType = bodyType;
            return this;
        }

        public Builder setEngine(Engine engine) {
            this.auto.engine = engine;
            return this;
        }

        public Builder setCount(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("count must be greater than zero");
            }
            this.auto.count = count;
            return this;
        }


        public Builder setCurrency(String currency) {
            this.auto.currency = currency;
            return this;
        }

        public Builder setCreated(LocalDateTime created) {
            this.auto.created = created;
            return this;
        }

        public Auto build() {
            Objects.requireNonNull(auto.price);
            return auto;
        }
    }
}

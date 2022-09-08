package ua.com.andromeda.model.cars;

import lombok.Getter;
import lombok.Setter;
import ua.com.andromeda.model.Detail;
import ua.com.andromeda.model.Engine;
import ua.com.andromeda.model.Manufacturer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sport_car")
@Getter
@Setter
public class SportCar extends Auto {
    @Column(name = "max_speed")
    private int maxSpeed;

    public SportCar() {

    }

    public SportCar(Auto auto) {
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
                "maxSpeed=" + maxSpeed +
                ", bodyType='" + bodyType + '\'' +
                ", count=" + count +
                ", engine=" + engine +
                ", currency='" + currency + '\'' +
                ", created=" + created +
                ", id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                ", details=" + details +
                '}';
    }

    public static class Builder {
        private final SportCar sportCar;

        public Builder() {
            sportCar = new SportCar();
        }

        public SportCar.Builder getBuilder() {
            return new SportCar.Builder();
        }

        public SportCar.Builder setPrice(BigDecimal price) {
            this.sportCar.price = price;
            return this;
        }

        public SportCar.Builder setManufacturer(Manufacturer manufacturer) {
            this.sportCar.manufacturer = manufacturer;
            return this;
        }

        public SportCar.Builder setModel(String model) {
            this.sportCar.model = model;
            return this;
        }

        public SportCar.Builder setDetails(List<Detail> details) {
            this.sportCar.details = details;
            return this;
        }

        public SportCar.Builder setMaxSpeed(int maxSpeed) {
            this.sportCar.maxSpeed = maxSpeed;
            return this;
        }

        public SportCar.Builder setEngine(Engine engine) {
            this.sportCar.engine = engine;
            return this;
        }

        public SportCar.Builder setCurrency(String currency) {
            this.sportCar.currency = currency;
            return this;
        }

        public SportCar.Builder setCreated(LocalDateTime created) {
            this.sportCar.created = created;
            return this;
        }

        public SportCar.Builder setBodyType(String bodyType) {
            bodyType = bodyType.trim();
            if (bodyType.length() > 20 || bodyType.length() <= 0) {
                throw new IllegalArgumentException("bodyType must be less 20 characters long");
            }
            this.sportCar.bodyType = bodyType;
            return this;
        }

        public SportCar.Builder setCount(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("count must be greater than zero");
            }
            this.sportCar.count = count;
            return this;
        }

        public Builder setId(String id) {
            this.sportCar.id = id;
            return this;
        }

        public SportCar build() {
            Objects.requireNonNull(sportCar.price);
            return sportCar;
        }
    }
}

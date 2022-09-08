package ua.com.andromeda.model.cars;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ua.com.andromeda.model.Detail;
import ua.com.andromeda.model.Engine;
import ua.com.andromeda.model.Invoice;
import ua.com.andromeda.model.Manufacturer;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Auto extends Vehicle {
    @Column(name = "body_type")
    protected String bodyType;
    @Column
    protected int count;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engine")
    protected Engine engine;
    @Column
    protected String currency;
    @Column
    @CreationTimestamp
    protected LocalDateTime created;

    public Auto() {
        super.id = UUID.randomUUID().toString();
        created = LocalDateTime.now();
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

        public Builder setId(String id) {
            this.auto.id = id;
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

        public Builder setDetails(List<Detail> details) {
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

        public Builder setInvoice(Invoice invoice) {
            this.auto.invoice = invoice;
            return this;
        }

        public Auto build() {
            Objects.requireNonNull(auto.price);
            return auto;
        }
    }
}

package ua.com.andromeda.module2.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public abstract class Product extends BaseEntity {
    protected String series;
    protected String screenType;
    protected BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(series, product.series) && Objects.equals(screenType, product.screenType) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(series, screenType, price);
    }
}

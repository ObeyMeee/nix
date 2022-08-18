package ua.com.andromeda.module2.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Television extends Product {
    private int diagonal;
    private String country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Television television)) return false;

        return super.equals(o) &&
                diagonal == television.diagonal
                && Objects.equals(country, television.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diagonal, country);
    }

    @Override
    public String toString() {
        return "Television{" +
                "id='" + id + '\'' +
                ", series='" + series + '\'' +
                ", screenType='" + screenType + '\'' +
                ", price=" + price +
                ", diagonal=" + diagonal +
                ", country='" + country + '\'' +
                '}';
    }
}

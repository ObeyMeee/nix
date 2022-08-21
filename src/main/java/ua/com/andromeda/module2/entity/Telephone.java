package ua.com.andromeda.module2.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Telephone extends Product {
    private Model model;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Telephone telephone)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(model, telephone.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), model);
    }

    @Override
    public String toString() {
        return "Telephone{" +
                "id='" + id + '\'' +
                ", series='" + series + '\'' +
                ", price=" + price +
                ", screenType='" + screenType + '\'' +
                ", model=" + model +
                '}';
    }
}

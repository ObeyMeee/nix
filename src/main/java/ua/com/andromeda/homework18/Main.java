package ua.com.andromeda.homework18;

import ua.com.andromeda.homework10.model.Auto;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Auto auto = new Auto.Builder()
                .setPrice(BigDecimal.ZERO)
                .setCount(2)
                .setBodyType("body type")
                .build();
        System.out.println("auto = " + auto);
    }
}

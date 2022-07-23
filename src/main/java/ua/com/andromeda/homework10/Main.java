package ua.com.andromeda.homework10;

import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.Truck;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Truck truck = new Truck(
                "model",
                Manufacturer.BMW,
                BigDecimal.valueOf(1000),
                "body",
                3000);

        Container<Truck> truckContainer = new Container<>(truck);
        System.out.println("truckContainer = " + truckContainer);
        Truck truckFromContainer = truckContainer.getAuto();
        truckContainer.generateDiscount();
        System.out.println("price after discount = " + truckFromContainer.getPrice());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Input value to increase price of auto ==> ");
        double valueDouble = 0;
        int valueInt = 0;
        if (scanner.hasNextLine()) {
            String value = scanner.nextLine();
            valueDouble = Double.parseDouble(value);
            valueInt = Integer.parseInt(value);
        }
        truckContainer.increasePrice(valueDouble);
        System.out.printf("price after increasing ==> %f%n", truckFromContainer.getPrice());
        truckContainer.increasePrice(valueInt);
    }
}
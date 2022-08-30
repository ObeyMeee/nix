package ua.com.andromeda.homework14.comparator;

import ua.com.andromeda.homework10.model.SportCar;
import ua.com.andromeda.homework10.service.SportCarService;
import ua.com.andromeda.homework19.ApplicationContext;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {
        SportCarService sportCarService = ApplicationContext.getInstance().get(SportCarService.class);
        // Compare sport car firstly by price in reversed order,
        // then by model on alphabetic order and finally by max speed
        Comparator<SportCar> comparator = Comparator.comparing(SportCar::getPrice)
                .reversed()
                .thenComparing(SportCar::getModel)
                .thenComparing(SportCar::getMaxSpeed);

        Set<SportCar> sportCarSet = new TreeSet<>(comparator);

        for (int i = 0; i < 50; i++) {
            sportCarSet.add(sportCarService.createRandomVehicle());
        }
        sportCarSet.forEach(System.out::println);
    }
}

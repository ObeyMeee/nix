package ua.com.andromeda.homework14.comparator;

import ua.com.andromeda.homework10.model.SportCar;
import ua.com.andromeda.homework10.service.SportCarService;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    private static final SportCarService SPORT_CAR_SERVICE = SportCarService.getInstance();

    public static void main(String[] args) {
        // Compare sport car firstly by price in reversed order,
        // then by model on alphabetic order and finally by max speed
        Comparator<SportCar> comparator = Comparator.comparing(SportCar::getPrice)
                .reversed()
                .thenComparing(SportCar::getModel)
                .thenComparing(SportCar::getMaxSpeed);

        Set<SportCar> sportCarSet = new TreeSet<>(comparator);

        for (int i = 0; i < 50; i++) {
            sportCarSet.add(SPORT_CAR_SERVICE.createRandomVehicle());
        }
        sportCarSet.forEach(System.out::println);
    }
}

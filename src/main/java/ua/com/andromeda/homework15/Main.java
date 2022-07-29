package ua.com.andromeda.homework15;

import ua.com.andromeda.homework10.model.SportCar;
import ua.com.andromeda.homework10.service.SportCarService;

import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Comparator<SportCar> comparator = Comparator.comparing(SportCar::getPrice)
                .reversed()
                .thenComparing(SportCar::getModel)
                .thenComparing(SportCar::getMaxSpeed);

        MyTree<SportCar> tree = new MyTree<>(comparator);
        SportCarService sportCarService = SportCarService.getInstance();
        for (int i = 0; i < 10; i++) {
            tree.add(sportCarService.createRandomVehicle());
        }
        tree.print();
        System.out.println("Sum of price of left branch ==> " + tree.getLeftTreePrice());
        System.out.println("Sum of price of right branch ==> " + tree.getRightTreePrice());
    }
}

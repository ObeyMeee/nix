package ua.com.andromeda.homework10;

import ua.com.andromeda.homework10.repository.AutoRepository;
import ua.com.andromeda.homework10.repository.SportCarRepository;
import ua.com.andromeda.homework10.repository.TruckRepository;
import ua.com.andromeda.homework10.service.AutoService;
import ua.com.andromeda.homework10.service.SportCarService;
import ua.com.andromeda.homework10.service.TruckService;

public class Main {
    private static final AutoService AUTO_SERVICE = new AutoService(new AutoRepository());
    private static final SportCarService SPORT_CAR_SERVICE = new SportCarService(new SportCarRepository());
    private static final TruckService TRUCK_SERVICE = new TruckService(new TruckRepository());

    public static void main(String[] args) {
        AUTO_SERVICE.createAndSaveAutos(5);
        AUTO_SERVICE.printAll();
        delimited();

        SPORT_CAR_SERVICE.createAndSaveSportCars(5);
        SPORT_CAR_SERVICE.printAll();
        delimited();

        TRUCK_SERVICE.createAndSaveTrucks(5);
        TRUCK_SERVICE.printAll();

    }

    private static void delimited() {
        System.out.println();
        System.out.println("--------------------------------");
        System.out.println();
    }
}
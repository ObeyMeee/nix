package ua.com.andromeda.homework10.service;

import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.SportCar;
import ua.com.andromeda.homework10.repository.CrudRepository;
import ua.com.andromeda.homework10.repository.SportCarRepository;

import java.math.BigDecimal;

public class SportCarService extends VehicleService<SportCar> {
    private static SportCarService instance;

    private SportCarService(CrudRepository<SportCar> repository) {
        super(repository);
    }

    public static SportCarService getInstance() {
        if (instance == null) {
            instance = new SportCarService(SportCarRepository.getInstance());
        }
        return instance;
    }

    @Override
    public SportCar createRandomVehicle() {
        String model = "new Model_" + RANDOM.nextInt(10);
        return new SportCar(model,
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextInt(10)),
                "body type",
                generateRandomListDetails(),
                RANDOM.nextInt(400));
    }

    @Override
    protected SportCar createSimpleVehicle() {
        return new SportCar("new Model",
                            Manufacturer.BMW,
                            BigDecimal.TEN,
                            "body type",
                            simpleDetailsList,
                            400);
    }


}

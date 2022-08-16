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
        return new SportCar.Builder()
                .setModel(model)
                .setManufacturer(getRandomManufacturer())
                .setPrice(BigDecimal.valueOf(RANDOM.nextInt(10)))
                .setBodyType("body type")
                .setDetails(generateRandomListDetails())
                .setMaxSpeed(RANDOM.nextInt(400))
                .build();
    }

    @Override
    protected SportCar createSimpleVehicle() {
        return new SportCar.Builder()
                .setModel("new Model")
                .setManufacturer(Manufacturer.BMW)
                .setPrice(BigDecimal.TEN)
                .setBodyType("body type")
                .setDetails(simpleDetailsList)
                .setMaxSpeed(400)
                .build();
    }


}

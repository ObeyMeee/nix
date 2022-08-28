package ua.com.andromeda.homework10.service;

import ua.com.andromeda.homework10.model.Engine;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.SportCar;
import ua.com.andromeda.homework10.repository.CrudRepository;
import ua.com.andromeda.homework10.repository.SportCarRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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
        Engine engine = new Engine();
        engine.setId(UUID.randomUUID().toString());
        engine.setBrand("brand_" + RANDOM.nextInt(500));
        engine.setVolume(RANDOM.nextInt(3000));
        return new SportCar.Builder()
                .setId(UUID.randomUUID().toString())
                .setModel("Model_" + RANDOM.nextInt(300))
                .setEngine(engine)
                .setCount(RANDOM.nextInt(1, 10))
                .setCurrency("$")
                .setCreated(LocalDateTime.now())
                .setPrice(BigDecimal.valueOf(RANDOM.nextDouble(500)))
                .setManufacturer(getRandomManufacturer())
                .setBodyType("body type")
                .setDetails(generateRandomListDetails())
                .setMaxSpeed(RANDOM.nextInt(300))
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

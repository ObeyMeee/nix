package ua.com.andromeda.homework10.service;

import ua.com.andromeda.homework10.model.Auto;
import ua.com.andromeda.homework10.model.Engine;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.repository.AutoRepository;
import ua.com.andromeda.homework10.repository.CrudRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class AutoService extends VehicleService<Auto> {

    private static AutoService instance;

    private AutoService(CrudRepository<Auto> repository) {
        super(repository);
    }

    public static AutoService getInstance() {
        if (instance == null) {
            instance = new AutoService(AutoRepository.getInstance());
        }
        return instance;
    }

    @Override
    public Auto createRandomVehicle() {
        Engine engine = new Engine();
        engine.setId(UUID.randomUUID().toString());
        engine.setBrand("brand_" + RANDOM.nextInt(500));
        engine.setVolume(RANDOM.nextInt(3000));
        return new Auto.Builder()
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
                .build();
    }

    @Override
    protected Auto createSimpleVehicle() {
        return new Auto.Builder()
                .setModel("new Model")
                .setPrice(BigDecimal.TEN)
                .setManufacturer(Manufacturer.BMW)
                .setBodyType("body type")
                .setDetails(simpleDetailsList)
                .build();
    }
}

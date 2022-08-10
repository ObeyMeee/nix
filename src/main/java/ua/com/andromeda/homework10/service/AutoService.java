package ua.com.andromeda.homework10.service;

import ua.com.andromeda.homework10.model.Auto;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.repository.AutoRepository;
import ua.com.andromeda.homework10.repository.CrudRepository;

import java.math.BigDecimal;

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
        return new Auto.Builder()
                .setModel("Model_" + RANDOM.nextInt(300))
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

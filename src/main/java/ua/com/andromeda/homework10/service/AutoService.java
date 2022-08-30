package ua.com.andromeda.homework10.service;

import ua.com.andromeda.homework10.model.Auto;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.repository.AutoRepository;
import ua.com.andromeda.homework10.repository.CrudRepository;
import ua.com.andromeda.homework19.annotations.Autowired;
import ua.com.andromeda.homework19.annotations.Qualifier;
import ua.com.andromeda.homework19.annotations.Singleton;

import java.math.BigDecimal;

@Singleton
public class AutoService extends VehicleService<Auto> {

    @Autowired
    public AutoService(@Qualifier(AutoRepository.class) CrudRepository<Auto> repository) {
        super(repository);
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

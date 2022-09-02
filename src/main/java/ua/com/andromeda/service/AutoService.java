package ua.com.andromeda.service;

import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.annotations.Qualifier;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.model.Engine;
import ua.com.andromeda.model.Manufacturer;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.repository.CrudRepository;
import ua.com.andromeda.repository.hibernate.HibernateAutoRepository;

import java.math.BigDecimal;

@Singleton
public class AutoService extends VehicleService<Auto> {

    @Autowired
    public AutoService(@Qualifier(HibernateAutoRepository.class) CrudRepository<Auto> repository) {
        super(repository);
    }


    @Override
    public Auto createRandomVehicle() {
        Engine engine = new Engine();
        engine.setBrand("brand_" + RANDOM.nextInt(500));
        engine.setVolume(RANDOM.nextInt(3000));
        return new Auto.Builder()
                .setModel("Model_" + RANDOM.nextInt(300))
                .setEngine(engine)
                .setCount(RANDOM.nextInt(1, 10))
                .setCurrency("$")
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

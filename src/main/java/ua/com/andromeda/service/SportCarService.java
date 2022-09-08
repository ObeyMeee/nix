package ua.com.andromeda.service;

import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.annotations.Qualifier;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.model.Engine;
import ua.com.andromeda.model.Manufacturer;
import ua.com.andromeda.model.cars.SportCar;
import ua.com.andromeda.repository.CrudRepository;
import ua.com.andromeda.repository.mongodb.SportCarRepositoryMongoDbImpl;

import java.math.BigDecimal;

@Singleton
public class SportCarService extends VehicleService<SportCar> {

    @Autowired
    public SportCarService(@Qualifier(SportCarRepositoryMongoDbImpl.class) CrudRepository<SportCar> repository) {
        super(repository);
    }


    @Override
    public SportCar createRandomVehicle() {
        Engine engine = new Engine();
        engine.setBrand("brand_" + RANDOM.nextInt(500));
        engine.setVolume(RANDOM.nextInt(3000));
        return new SportCar.Builder()
                .setModel("Model_" + RANDOM.nextInt(300))
                .setEngine(engine)
                .setCount(RANDOM.nextInt(1, 10))
                .setCurrency("$")
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

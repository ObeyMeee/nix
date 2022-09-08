package ua.com.andromeda.service;

import ua.com.andromeda.annotations.Qualifier;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.model.Engine;
import ua.com.andromeda.model.Manufacturer;
import ua.com.andromeda.model.cars.Truck;
import ua.com.andromeda.repository.CrudRepository;
import ua.com.andromeda.repository.hibernate.HibernateTruckRepository;

import java.math.BigDecimal;

@Singleton
public class TruckService extends VehicleService<Truck> {

    public TruckService(@Qualifier(HibernateTruckRepository.class) CrudRepository<Truck> repository) {
        super(repository);
    }

    @Override
    public Truck createRandomVehicle() {
        Engine engine = new Engine();
        engine.setBrand("brand_" + RANDOM.nextInt(500));
        engine.setVolume(RANDOM.nextInt(3000));
        return new Truck.Builder()
                .setModel("new Model_" + RANDOM.nextInt(300))
                .setEngine(engine)
                .setCount(RANDOM.nextInt(1, 10))
                .setCurrency("$")
                .setPrice(BigDecimal.valueOf(RANDOM.nextDouble(500)))
                .setManufacturer(getRandomManufacturer())
                .setBodyType("body type")
                .setDetails(generateRandomListDetails())
                .setMaxCarryingCapacity(RANDOM.nextInt(30000))
                .build();
    }

    @Override
    protected Truck createSimpleVehicle() {
        return new Truck.Builder()
                .setModel("new Model")
                .setManufacturer(Manufacturer.BMW)
                .setPrice(BigDecimal.TEN)
                .setBodyType("body type")
                .setDetails(simpleDetailsList)
                .setMaxCarryingCapacity(20000)
                .build();
    }
}
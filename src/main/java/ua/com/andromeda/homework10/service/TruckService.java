package ua.com.andromeda.homework10.service;

import ua.com.andromeda.homework10.model.Engine;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.Truck;
import ua.com.andromeda.homework10.repository.CrudRepository;
import ua.com.andromeda.homework10.repository.TruckRepository;
import ua.com.andromeda.homework19.annotations.Autowired;
import ua.com.andromeda.homework19.annotations.Qualifier;
import ua.com.andromeda.homework19.annotations.Singleton;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Singleton
public class TruckService extends VehicleService<Truck> {

    @Autowired
    public TruckService(@Qualifier(TruckRepository.class) CrudRepository<Truck> repository) {
        super(repository);
    }


    @Override
    public Truck createRandomVehicle() {
        Engine engine = new Engine();
        engine.setId(UUID.randomUUID().toString());
        engine.setBrand("brand_" + RANDOM.nextInt(500));
        engine.setVolume(RANDOM.nextInt(3000));
        return new Truck.Builder()
                .setId(UUID.randomUUID().toString())
                .setModel("new Model_" + RANDOM.nextInt(300))
                .setEngine(engine)
                .setCount(RANDOM.nextInt(1, 10))
                .setCurrency("$")
                .setCreated(LocalDateTime.now())
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
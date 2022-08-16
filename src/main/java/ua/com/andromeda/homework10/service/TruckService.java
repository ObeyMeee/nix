package ua.com.andromeda.homework10.service;

import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.Truck;
import ua.com.andromeda.homework10.repository.CrudRepository;
import ua.com.andromeda.homework10.repository.TruckRepository;

import java.math.BigDecimal;

public class TruckService extends VehicleService<Truck> {
    private static TruckService instance;

    private TruckService(CrudRepository<Truck> repository) {
        super(repository);
    }

    public static TruckService getInstance() {
        if (instance == null) {
            instance = new TruckService(TruckRepository.getInstance());
        }
        return instance;
    }

    @Override
    public Truck createRandomVehicle() {
        String model = "new Model_" + RANDOM.nextInt(10);
        return new Truck.Builder()
                .setModel(model)
                .setManufacturer(getRandomManufacturer())
                .setPrice(BigDecimal.valueOf(RANDOM.nextInt(10)))
                .setBodyType("body type")
                .setDetails(generateRandomListDetails())
                .setMaxCarryingCapacity(RANDOM.nextInt(400))
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
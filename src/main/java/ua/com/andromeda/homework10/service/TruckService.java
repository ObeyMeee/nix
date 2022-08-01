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
        return new Truck("new Model", getRandomManufacturer(), BigDecimal.TEN,
                "body type", RANDOM.nextInt(20000));
    }

    @Override
    protected Truck createSimpleVehicle() {
        return new Truck("new Model", Manufacturer.BMW, BigDecimal.TEN,
                "body type", 20000);
    }
}
package ua.com.andromeda.homework10.service;

import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.Truck;
import ua.com.andromeda.homework10.repository.CrudRepository;

import java.math.BigDecimal;

public class TruckService extends VehicleService<Truck> {

    protected TruckService(CrudRepository<Truck> repository) {
        super(repository);
    }

    @Override
    protected Truck createRandomVehicle() {
        return new Truck("new Model", getRandomManufacturer(), BigDecimal.TEN,
                "body type", RANDOM.nextInt(20000));
    }

    @Override
    protected Truck createSimpleVehicle() {
        return new Truck("new Model", Manufacturer.BMW, BigDecimal.TEN,
                "body type", 20000);
    }
}
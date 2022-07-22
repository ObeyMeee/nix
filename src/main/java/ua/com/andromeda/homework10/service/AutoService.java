package ua.com.andromeda.homework10.service;

import ua.com.andromeda.homework10.model.Auto;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.repository.CrudRepository;

import java.math.BigDecimal;

public class AutoService extends VehicleService<Auto> {

    protected AutoService(CrudRepository<Auto> repository) {
        super(repository);
    }

    @Override
    protected Auto createRandomVehicle() {
        return new Auto("new Model", getRandomManufacturer(), BigDecimal.TEN,
                "body type");
    }

    @Override
    protected Auto createSimpleVehicle() {
        return new Auto("new Model", Manufacturer.BMW, BigDecimal.TEN,
                "body type");
    }
}

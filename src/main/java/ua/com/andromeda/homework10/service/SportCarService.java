package ua.com.andromeda.homework10.service;

import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.SportCar;
import ua.com.andromeda.homework10.repository.CrudRepository;

import java.math.BigDecimal;

public class SportCarService extends VehicleService<SportCar> {

    protected SportCarService(CrudRepository<SportCar> repository) {
        super(repository);
    }

    @Override
    protected SportCar createRandomVehicle() {
        return new SportCar("new Model", getRandomManufacturer(),
                BigDecimal.TEN, "body type", RANDOM.nextInt(400));
    }

    @Override
    protected SportCar createSimpleVehicle() {
        return new SportCar("new Model", Manufacturer.BMW,
                BigDecimal.TEN, "body type", 400);
    }
}

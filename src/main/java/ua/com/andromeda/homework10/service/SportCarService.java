package ua.com.andromeda.homework10.service;

import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.SportCar;
import ua.com.andromeda.homework10.repository.CrudRepository;
import ua.com.andromeda.homework10.repository.SportCarRepository;
import ua.com.andromeda.homework19.annotations.Autowired;
import ua.com.andromeda.homework19.annotations.Qualifier;
import ua.com.andromeda.homework19.annotations.Singleton;

import java.math.BigDecimal;

@Singleton
public class SportCarService extends VehicleService<SportCar> {
    @Autowired
    public SportCarService(@Qualifier(SportCarRepository.class) CrudRepository<SportCar> repository) {
        super(repository);
    }


    @Override
    public SportCar createRandomVehicle() {
        String model = "new Model_" + RANDOM.nextInt(10);
        return new SportCar.Builder()
                .setModel(model)
                .setManufacturer(getRandomManufacturer())
                .setPrice(BigDecimal.valueOf(RANDOM.nextInt(10)))
                .setBodyType("body type")
                .setDetails(generateRandomListDetails())
                .setMaxSpeed(RANDOM.nextInt(400))
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

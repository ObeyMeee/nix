package ua.com.andromeda.homework10.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.SportCar;
import ua.com.andromeda.homework10.repository.SportCarRepository;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SportCarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SportCarService.class);
    private static final Random RANDOM = new Random();
    private final SportCarRepository sportCarRepository;

    public SportCarService(SportCarRepository sportCarRepository) {
        this.sportCarRepository = sportCarRepository;
    }

    public List<SportCar> createAndSaveSportCars(int count) {
        List<SportCar> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final SportCar sportCar = new SportCar(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    "Model-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(300)
            );
            result.add(sportCar);
            sportCarRepository.save(sportCar);
            LOGGER.debug("Created sport car {}", sportCar.getId());
        }
        return result;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveSportCars(List<SportCar> sportCars) {
        sportCarRepository.saveAll(sportCars);
    }

    public void printAll() {
        for (SportCar sportCar : sportCarRepository.getAll()) {
            System.out.println(sportCar);
        }
    }

    public SportCar findOneById(String id) {
        if (id == null) {
            return sportCarRepository.getById("");
        }
        return sportCarRepository.getById(id);

    }
}

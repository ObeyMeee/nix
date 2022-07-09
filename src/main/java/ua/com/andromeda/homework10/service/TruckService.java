package ua.com.andromeda.homework10.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.Truck;
import ua.com.andromeda.homework10.repository.TruckRepository;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TruckService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TruckService.class);
    private static final Random RANDOM = new Random();
    private final TruckRepository truckRepository;

    public TruckService(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    public List<Truck> createAndSaveTrucks(int count) {
        List<Truck> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Truck truck = new Truck(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    "Model-" + RANDOM.nextInt(1000),
                    RANDOM.nextInt(4000)
            );
            result.add(truck);
            truckRepository.save(truck);
            LOGGER.debug("Created sport car {}", truck.getId());
        }
        return result;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveTrucks(List<Truck> trucks) {
        truckRepository.saveAll(trucks);
    }

    public void printAll() {
        for (Truck truck : truckRepository.getAll()) {
            System.out.println(truck);
        }
    }

    public Truck findOneById(String id) {
        if (id == null) {
            return truckRepository.getById("");
        }
        return truckRepository.getById(id);

    }
}

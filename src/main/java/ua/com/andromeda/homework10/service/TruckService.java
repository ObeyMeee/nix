package ua.com.andromeda.homework10.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.andromeda.homework10.dto.TruckDto;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.Truck;
import ua.com.andromeda.homework10.repository.TruckRepository;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TruckService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Truck.class);
    private static final Random RANDOM = new Random();
    private final TruckRepository truckRepository;

    private final Truck simpleTruck;

    public TruckService(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
        simpleTruck = createSimpleTruck();
        truckRepository.save(simpleTruck);
    }

    private Truck createSimpleTruck() {
        return new Truck("new Model", Manufacturer.BMW, BigDecimal.TEN, "body type", 10000);
    }

    public List<Truck> createAndSaveTrucks(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count can't be less than 0");
        }

        List<Truck> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Truck truck = new Truck(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble() * 1000),
                    "Model-" + RANDOM.nextInt(1000),
                    20000
            );
            result.add(truck);
            truckRepository.save(truck);
            LOGGER.debug("Created truck {}", truck.getId());
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

    public Optional<Truck> findOneById(String id) {
        return id == null ? truckRepository.findById("") :
                truckRepository.findById(id);
    }

    public boolean save(Truck truck) {
        if (truck == null) {
            throw new IllegalArgumentException();
        }
        truckRepository.save(truck);
        return true;
    }

    public void showOptionalExamples() {
        String id = simpleTruck.getId();
        filter(id);
        ifPresentUpdateTruckOrElseSave(simpleTruck);
        ifPresent(id);
        orElse(id);
        orElseGet(id);
        map(id);
        try {
            orElseThrow(id);
        } catch (RuntimeException exception) {
            System.err.println(exception.getMessage());
        }
        or(id);
    }

    public void filter(String id) {
        findOneById(id);
    }

    public void ifPresentUpdateTruckOrElseSave(Truck truck) {
        truckRepository.update(truck);
    }

    public void ifPresent(String id) {
        findOneById(id).ifPresent(truck -> System.out.println("Founded truck ==> " + truck));
    }

    public Truck orElse(String id) {
        return findOneById(id).orElse(createSimpleTruck());
    }

    public Truck orElseGet(String id) {

        return findOneById(id).orElseGet(() -> {
            System.out.println("Creating new truck...");
            String model = "model-23";
            return new Truck(model, Manufacturer.BMW, BigDecimal.ZERO, "body22", 15000);
        });
    }

    public Optional<TruckDto> map(String id) {
        return findOneById(id)
                .map(truck -> new TruckDto(truck.getModel(), truck.getPrice(), truck.getManufacturer(), truck.getBodyType(), 11111));
    }

    public Truck orElseThrow(String id) {
        Truck truck;
        truck = findOneById(id).orElseThrow(() -> {
            throw new RuntimeException("Cannot find truck by id = " + id);
        });
        return truck;
    }

    public Optional<Truck> or(String id) {
        return findOneById(id).or(() -> Optional.of(createSimpleTruck()));
    }
}

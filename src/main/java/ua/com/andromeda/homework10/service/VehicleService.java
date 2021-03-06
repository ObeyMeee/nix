package ua.com.andromeda.homework10.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.Vehicle;
import ua.com.andromeda.homework10.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public abstract class VehicleService<T extends Vehicle> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(VehicleService.class);
    protected static final Random RANDOM = new Random();
    private final CrudRepository<T> repository;

    protected VehicleService(CrudRepository<T> repository) {
        this.repository = repository;
    }


    public List<T> createAndSaveVehicles(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count can't be less than 0");
        }

        List<T> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final T vehicle = createRandomVehicle();
            result.add(vehicle);
            repository.save(vehicle);
            LOGGER.debug("Created auto {}", vehicle.getId());
        }
        return result;
    }

    protected abstract T createRandomVehicle();

    protected abstract T createSimpleVehicle();

    protected Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        for (T vehicle : repository.getAll()) {
            System.out.println(vehicle);
        }
    }

    public Optional<T> findOneById(String id) {
        return id == null ? repository.findById("") :
                repository.findById(id);
    }

    public Optional<T> findById(String id) {
        return repository.findById(id);
    }

    public List<T> getAll() {
        return repository.getAll();
    }

    public boolean save(T vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Auto must not be null");
        }
        if (vehicle.getPrice().equals(BigDecimal.ZERO)) {
            vehicle.setPrice(BigDecimal.valueOf(-1));
        }
        return repository.save(vehicle);
    }

    public void saveAll(List<T> vehicles) {
        if (vehicles == null) {
            throw new IllegalArgumentException("auto cannot be null");
        }
        repository.saveAll(vehicles);
    }

    public void update(T vehicle) {
        repository.update(vehicle);
    }

    public boolean delete(String id) {
        return repository.delete(id);
    }


    public void showOptionalExamples() {
        T simpleAuto = createSimpleVehicle();
        String id = simpleAuto.getId();
        filter(id);
        ifPresentUpdateAutoOrElseSave(simpleAuto);
        ifPresent(id);
        orElse(id);
        orElseGet(id);
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

    public void ifPresentUpdateAutoOrElseSave(T auto) {
        repository.update(auto);
    }


    public void ifPresent(String id) {
        findOneById(id).ifPresent(auto -> System.out.println("Founded auto ==> " + auto));
    }

    public T orElse(String id) {
        return findOneById(id).orElse(createSimpleVehicle());
    }

    public T orElseGet(String id) {
        T auto = findOneById(id).orElseGet(() -> {
            System.out.println("Creating new auto...");
            return createSimpleVehicle();
        });
        System.out.println("auto has been successfully created");
        return auto;
    }


    public T orElseThrow(String id) {
        T auto;
        auto = findOneById(id).orElseThrow(() -> {
            throw new RuntimeException("Cannot find auto by id = '" + id + "'");
        });
        return auto;
    }

    public Optional<T> or(String id) {

        return findOneById(id).or(
                () -> Optional.of(createSimpleVehicle()));
    }


}

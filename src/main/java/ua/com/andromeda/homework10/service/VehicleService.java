package ua.com.andromeda.homework10.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.Vehicle;
import ua.com.andromeda.homework10.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class VehicleService<T extends Vehicle> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(VehicleService.class);
    protected static final Random RANDOM = new Random();
    protected final Predicate<T> vehicleHasPrice = vehicle -> !vehicle.getPrice().equals(BigDecimal.ZERO);
    protected final Predicate<List<T>> listHasPrice = vehicles -> vehicles.stream().allMatch(vehicleHasPrice);
    protected final Function<Map<String, Object>, T> function = map -> {
        T vehicle = createSimpleVehicle();

        BigDecimal price = (BigDecimal) map.get("price");
        String model = (String) map.get("model");
        Manufacturer manufacturer = (Manufacturer) map.get("manufacturer");
        List<String> details = (List<String>) map.get("details");

        vehicle.setPrice(price);
        vehicle.setModel(model);
        vehicle.setManufacturer(manufacturer);
        vehicle.setDetails(details);
        return vehicle;
    };
    protected final List<String> simpleDetailsList = List.of("details1", "details2", "details3");

    private final CrudRepository<T> repository;

    protected VehicleService(CrudRepository<T> repository) {
        this.repository = repository;
    }

    protected List<String> generateRandomListDetails() {
        return List.of("details_" + RANDOM.nextInt(100), "details_" + RANDOM.nextInt(100),
                "details_" + RANDOM.nextInt(100));
    }


    public List<T> createVehicles(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count can't be less than 0");
        }

        List<T> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final T vehicle = createRandomVehicle();
            result.add(vehicle);
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
        boolean isSaved = repository.save(vehicle);
        if (isSaved) {
            LOGGER.info("Saved auto ==> {}", vehicle);
        }
        return isSaved;
    }

    public void saveAll(List<T> vehicles) {
        if (vehicles == null) {
            throw new IllegalArgumentException("auto cannot be null");
        }
        repository.saveAll(vehicles);
    }

    public void update(T vehicle, String id) {
        repository.update(vehicle, id);
    }

    public boolean delete(String id) {
        boolean isDeleted = repository.delete(id);
        if (isDeleted) {
            LOGGER.info("Auto with id == '{}' has been successfully deleted", id);
        }
        return isDeleted;
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
        repository.update(auto, auto.getId());
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


    public void printVehicles(BigDecimal price) {
        List<T> vehicles = repository.getAll();
        vehicles.stream()
                .filter(vehicle -> vehicle.getPrice().compareTo(price) > 0)
                .forEach(System.out::println);
    }

    public void printSummaryPrice() {
        List<T> vehicles = repository.getAll();
        vehicles.stream()
                .map(Vehicle::getPrice)
                .reduce(BigDecimal::add)
                .ifPresent(summaryPrice -> System.out.println("Summary price ==> " + summaryPrice));

    }

    public void sortByModelAndRemoveDuplicatesToMap() {
        List<T> vehicles = repository.getAll();
        vehicles.stream()
                .distinct()
                .sorted(Comparator.comparing(Vehicle::getModel))
                .collect(Collectors.toMap(Vehicle::getId,
                                          Vehicle::getModel,
                                         (vehicle, duplicatedVehicle) -> duplicatedVehicle,
                        LinkedHashMap::new))
                .forEach((key, value) -> System.out.printf("%s ==> %s%n", key, value));
    }

    public boolean hasDetail(String detail){
        List<T> vehicles = repository.getAll();

        return vehicles.stream()
                .flatMap(vehicle -> vehicle.getDetails().stream())
                .anyMatch(details -> details.equals(detail));

    }
    public void printVehiclePriceStatistics() {
        List<T> vehicles = repository.getAll();
        DoubleSummaryStatistics priceSummaryStatistics = vehicles.stream()
                .mapToDouble(vehicle -> vehicle.getPrice().doubleValue())
                .summaryStatistics();

        System.out.println("priceSummaryStatistics = " + priceSummaryStatistics);
    }

    public void testAllVehiclesHasPrice() {
        List<T> vehicles = getAll();
        if (listHasPrice.test(vehicles)){
            System.out.println("All autos has price");
        }else {
            System.out.println("some autos without price are founded");
        }
    }
    public T getVehicleFromMap(Map<String, Object> map){
        return function.apply(map);
    }
}

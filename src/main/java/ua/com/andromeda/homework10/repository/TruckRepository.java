package ua.com.andromeda.homework10.repository;

import ua.com.andromeda.homework10.model.Truck;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TruckRepository implements CrudRepository<Truck> {
    private final List<Truck> trucks;

    public TruckRepository() {
        trucks = new LinkedList<>();
    }

    @Override
    public Optional<Truck> findById(String id) {
        return trucks.stream()
                .filter(truck -> truck.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Truck> getAll() {
        return trucks;
    }

    @Override
    public boolean save(Truck truck) {
        if (truck == null) {
            throw new IllegalArgumentException("sport car must not be null");
        }
        if (truck.getPrice().equals(BigDecimal.ZERO)) {
            truck.setPrice(BigDecimal.valueOf(-1));
        }
        trucks.add(truck);
        return true;
    }

    @Override
    public void saveAll(List<Truck> sportCarsToAdd) {
        if (sportCarsToAdd == null) {
            throw new IllegalArgumentException("auto cannot be null");
        }
        trucks.addAll(sportCarsToAdd);
    }

    @Override
    public void update(Truck truck) {
        String id = truck.getId();
        Optional<Truck> optionalTruck = findById(id);
        optionalTruck.ifPresentOrElse(founded -> TruckRepository.TruckCopy.copy(truck, founded),
                () -> save(truck));
    }

    @Override
    public boolean delete(String id) {
        return trucks.removeIf(truck -> truck.getId().equals(id));

    }

    private static class TruckCopy {
        static void copy(final Truck from, final Truck to) {
            to.setModel(from.getModel());
            to.setBodyType(from.getBodyType());
            to.setPrice(from.getPrice());
            to.setMaxCarryingCapacity(from.getMaxCarryingCapacity());
        }
    }
}

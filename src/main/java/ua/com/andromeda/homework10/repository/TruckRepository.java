package ua.com.andromeda.homework10.repository;

import ua.com.andromeda.homework10.model.Truck;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class TruckRepository implements CrudRepository<Truck> {
    private final List<Truck> trucks;

    public TruckRepository() {
        trucks = new LinkedList<>();
    }

    @Override
    public Truck getById(String id) {
        for (Truck truck : trucks) {
            if (truck.getId().equals(id)) {
                return truck;
            }
        }
        return null;
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
        final Truck founded = getById(truck.getId());
        if (founded != null) {
            SportCarCopy.copy(truck, founded);
        }
    }

    @Override
    public boolean delete(String id) {
        return trucks.removeIf(truck -> truck.getId().equals(id));

    }

    private static class SportCarCopy {
        static void copy(final Truck from, final Truck to) {
            to.setModel(from.getModel());
            to.setBodyType(from.getBodyType());
            to.setPrice(from.getPrice());
            to.setMaxCarryingCapacity(from.getMaxCarryingCapacity());
        }
    }
}

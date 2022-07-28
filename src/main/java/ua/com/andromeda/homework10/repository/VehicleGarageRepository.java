package ua.com.andromeda.homework10.repository;

import ua.com.andromeda.homework10.model.Vehicle;
import ua.com.andromeda.homework14.garage.Garage;

import java.util.Date;
import java.util.Optional;

public class VehicleGarageRepository implements GarageRepository<Vehicle> {
    private static VehicleGarageRepository instance;

    private final Garage<Vehicle> vehicles;

    private VehicleGarageRepository() {
        vehicles = new Garage<>();
    }

    public static VehicleGarageRepository getInstance() {
        if (instance == null) {
            instance = new VehicleGarageRepository();
        }
        return instance;
    }

    @Override
    public Optional<Vehicle> findByIndex(int index) {
        return Optional.ofNullable(vehicles.get(index));
    }

    @Override
    public Garage<Vehicle> getAll() {
        return vehicles;
    }

    @Override
    public boolean add(Vehicle vehicle) {
        if (vehicle == null) {
            return false;
        }
        vehicles.add(vehicle);
        return true;
    }

    @Override
    public boolean insert(int index, Vehicle vehicle) {
        if (vehicle == null) {
            return false;
        }
        vehicles.add(index, vehicle);
        return true;
    }

    @Override
    public void set(int index, Vehicle vehicle) {
        vehicles.set(index, vehicle);
    }

    @Override
    public boolean remove(int index) {
        if (index < 0 || index >= vehicles.getSize()) {
            return false;
        }
        vehicles.remove(index);
        return true;
    }

    @Override
    public void print() {
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }

    @Override
    public Date getCreatedDate(int index) {
        return vehicles.getCreatedDate(index);
    }

    @Override
    public Date getUpdatedDate(int index) {
        return vehicles.getUpdatedDate(index);
    }

    @Override
    public int getRestylingNumber(int index) {
        return vehicles.getRestylingNumber(index);
    }
}

package ua.com.andromeda.homework10.service;

import ua.com.andromeda.homework10.model.Vehicle;
import ua.com.andromeda.homework10.repository.VehicleGarageRepository;
import ua.com.andromeda.homework14.garage.Garage;

import java.util.Date;
import java.util.Optional;

public class VehicleGarageService {

    private static VehicleGarageService instance;
    private final VehicleGarageRepository repository;

    private VehicleGarageService(VehicleGarageRepository repository) {
        this.repository = repository;
    }

    public static VehicleGarageService getInstance() {
        if (instance == null) {
            instance = new VehicleGarageService(VehicleGarageRepository.getInstance());
        }
        return instance;
    }

    public Optional<Vehicle> findByIndex(int index) {
        return repository.findByIndex(index);
    }

    public Garage<Vehicle> getAll() {
        return repository.getAll();
    }

    public boolean add(Vehicle vehicle) {
        return repository.add(vehicle);
    }

    public boolean insert(int index, Vehicle vehicle) {
        return repository.insert(index, vehicle);
    }

    public void set(int index, Vehicle vehicle) {
        Optional<Vehicle> optionalVehicle = findByIndex(index);
        if (optionalVehicle.isEmpty()) {
            System.out.printf("There isn't vehicle by index = %d%n", index);
            return;
        }
        repository.set(index, vehicle);
    }

    public boolean remove(int index) {
        return repository.remove(index);
    }

    public void print() {
        repository.print();
    }

    public Date getCreatedDate(int index) {
        return repository.getCreatedDate(index);
    }

    public Date getUpdatedDate(int index) {
        return repository.getUpdatedDate(index);
    }

    public int getRestylingAmount(int index) {
        return repository.getRestylingNumber(index);
    }
}

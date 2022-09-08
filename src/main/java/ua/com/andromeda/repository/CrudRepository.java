package ua.com.andromeda.repository;

import ua.com.andromeda.model.cars.Vehicle;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends Vehicle> {
    Optional<T> findById(String id);

    List<T> getAll();

    boolean save(T vehicle);

    void saveAll(List<T> vehicles);

    void update(T vehicle);

    boolean delete(String id);
}

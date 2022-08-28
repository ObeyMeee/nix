package ua.com.andromeda.homework10.repository;

import ua.com.andromeda.homework10.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends Vehicle> {
    Optional<T> findById(String id);

    List<T> getAll();

    boolean save(T vehicle);

    void saveAll(List<T> vehicles);

    void update(T vehicle, String id);

    boolean delete(String id);
}

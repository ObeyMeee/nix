package ua.com.andromeda.homework10.repository;

import ua.com.andromeda.homework10.model.Vehicle;
import ua.com.andromeda.homework14.garage.Garage;

import java.util.Date;
import java.util.Optional;

public interface GarageRepository<T extends Vehicle> {
    Optional<T> findByIndex(int findByIndex);

    Garage<T> getAll();

    boolean add(T vehicle);

    boolean insert(int index, T vehicle);

    void set(int index, T vehicle);

    boolean remove(int index);

    void print();

    Date getCreatedDate(int index);

    Date getUpdatedDate(int index);

    int getRestylingNumber(int index);
}

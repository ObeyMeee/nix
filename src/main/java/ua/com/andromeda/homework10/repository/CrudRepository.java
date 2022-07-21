package ua.com.andromeda.homework10.repository;


import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(String id);

    List<T> getAll();

    boolean save(T entity);

    void saveAll(List<T> entity);

    void update(T entity);

    boolean delete(String id);
}

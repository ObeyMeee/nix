package ua.com.andromeda.homework10.repository;


import java.util.List;

public interface CrudRepository<T> {
    T getById(String id);

    List<T> getAll();

    void save(T entity);

    void saveAll(List<T> entity);

    void update(T entity);

    void delete(String id);
}

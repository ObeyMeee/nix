package ua.com.andromeda.homework10.repository;

import ua.com.andromeda.homework10.model.Auto;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class AutoRepository implements CrudRepository<Auto> {
    private final List<Auto> autos;

    public AutoRepository() {
        autos = new LinkedList<>();
    }

    @Override
    public Auto getById(String id) {
        for (Auto auto : autos) {
            if (auto.getId().equals(id)) {
                return auto;
            }
        }
        return null;
    }

    @Override
    public List<Auto> getAll() {
        return autos;
    }

    @Override
    public void save(Auto auto) {
        if (auto == null) {
            throw new IllegalArgumentException("Auto must not be null");
        }
        if (auto.getPrice().equals(BigDecimal.ZERO)) {
            auto.setPrice(BigDecimal.valueOf(-1));
        }
        autos.add(auto);
    }

    @Override
    public void saveAll(List<Auto> auto) {
        if (auto == null) {
            throw new IllegalArgumentException("auto cannot be null");
        }
        autos.addAll(auto);
    }

    @Override
    public void update(Auto auto) {
        final Auto founded = getById(auto.getId());
        if (founded != null) {
            AutoCopy.copy(auto, founded);
        }
    }

    public boolean updateByBodyType(String bodyType, Auto copyFrom) {
        for (Auto auto : autos) {
            if (auto.getBodyType().equals(bodyType)) {
                AutoCopy.copy(copyFrom, auto);
            }
        }
        return true;
    }

    @Override
    public void delete(String id) {
        autos.removeIf(auto -> auto.getId().equals(id));
    }

    private static class AutoCopy {
        static void copy(final Auto from, final Auto to) {
            to.setModel(from.getModel());
            to.setBodyType(from.getBodyType());
            to.setPrice(from.getPrice());
        }
    }
}
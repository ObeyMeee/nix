package ua.com.andromeda.homework10.repository;

import ua.com.andromeda.homework10.model.Auto;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AutoRepository implements CrudRepository<Auto> {
    private final List<Auto> autos;

    public AutoRepository() {
        autos = new LinkedList<>();
    }

    @Override
    public Optional<Auto> findById(String id) {
        return autos.stream()
                .filter(auto -> auto.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Auto> getAll() {
        return autos;
    }

    @Override
    public boolean save(Auto auto) {
        if (auto == null) {
            throw new IllegalArgumentException("Auto must not be null");
        }
        if (auto.getPrice().equals(BigDecimal.ZERO)) {
            auto.setPrice(BigDecimal.valueOf(-1));
        }
        autos.add(auto);
        return true;
    }

    @Override
    public void saveAll(List<Auto> autos) {
        if (autos == null) {
            throw new IllegalArgumentException("auto cannot be null");
        }
        autos.addAll(autos);
    }

    @Override
    public void update(Auto auto) {
        String id = auto.getId();
        Optional<Auto> optionalAuto = findById(id);
        optionalAuto.ifPresentOrElse(founded -> AutoCopy.copy(auto, founded),
                () -> save(auto));
    }

    @Override
    public boolean delete(String id) {
        return autos.removeIf(auto -> auto.getId().equals(id));
    }

    private static class AutoCopy {
        static void copy(final Auto from, final Auto to) {
            to.setModel(from.getModel());
            to.setBodyType(from.getBodyType());
            to.setPrice(from.getPrice());
        }
    }
}

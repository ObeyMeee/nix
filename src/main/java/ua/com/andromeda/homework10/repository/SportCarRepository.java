package ua.com.andromeda.homework10.repository;

import ua.com.andromeda.homework10.model.SportCar;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SportCarRepository implements CrudRepository<SportCar> {
    private final List<SportCar> sportCars;

    public SportCarRepository() {
        sportCars = new LinkedList<>();
    }

    @Override
    public Optional<SportCar> findById(String id) {
        return sportCars.stream()
                .filter(sportCar -> sportCar.getId().equals(id))
                .findAny();
    }

    @Override
    public List<SportCar> getAll() {
        return sportCars;
    }

    @Override
    public boolean save(SportCar sportCar) {
        if (sportCar == null) {
            throw new IllegalArgumentException("sport car cannot be null");
        }
        if (sportCar.getPrice().equals(BigDecimal.ZERO)) {
            sportCar.setPrice(BigDecimal.valueOf(-1));
        }
        sportCars.add(sportCar);
        return true;
    }

    @Override
    public void saveAll(List<SportCar> sportCarsToAdd) {
        if (sportCarsToAdd == null) {
            throw new IllegalArgumentException("sport car cannot be null");
        }
        sportCars.addAll(sportCarsToAdd);
    }

    @Override
    public void update(SportCar sportCar) {
        String id = sportCar.getId();
        Optional<SportCar> optionalSportCar = findById(id);
        optionalSportCar.ifPresentOrElse(founded -> SportCarRepository.SportCarCopy.copy(sportCar, founded),
                () -> save(sportCar));
    }

    @Override
    public boolean delete(String id) {
        return sportCars.removeIf(sportCar -> sportCar.getId().equals(id));
    }

    private static class SportCarCopy {
        static void copy(final SportCar from, final SportCar to) {
            to.setModel(from.getModel());
            to.setBodyType(from.getBodyType());
            to.setPrice(from.getPrice());
            to.setMaxSpeed(from.getMaxSpeed());
        }
    }
}

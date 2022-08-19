package ua.com.andromeda.homework10.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.SportCar;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class SportCarRepositoryTest {

    private SportCarRepository target;
    private SportCar sportCar;

    @Before
    public void setUp() {
        target = SportCarRepository.getInstance();
        sportCar = new SportCar.Builder()
                .setBodyType("BodyType")
                .setPrice(BigDecimal.TEN)
                .setManufacturer(Manufacturer.BMW)
                .setModel("Model")
                .setMaxSpeed(300)
                .build();
    }

    @Test
    public void getById_notExist() {
        target.save(sportCar);
        Optional<SportCar> actual = target.findById("not exist");
        Assertions.assertEquals(Optional.empty(), actual);
    }


    @Test
    public void getById_exist() {
        target.save(sportCar);
        Optional<SportCar> actual = target.findById(sportCar.getId());
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(sportCar, actual.get());
    }


    @Test
    public void getAll_emptyList() {
        List<SportCar> expected = new LinkedList<>();
        List<SportCar> actual = target.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAll() {
        target.save(sportCar);
        List<SportCar> expected = new LinkedList<>();
        expected.add(sportCar);
        List<SportCar> actual = target.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void save_null() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> target.save(null)
        );
    }

    @Test
    public void save_autoPriceZero() {
        sportCar.setPrice(BigDecimal.ZERO);
        target.save(sportCar);
        BigDecimal expected = BigDecimal.valueOf(-1);
        BigDecimal actual = sportCar.getPrice();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void save_checkListLength() {
        target.save(sportCar);
        int expected = 1;
        int actual = target.getAll().size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void saveAll_null() {
        String message = "sport car cannot be null";
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> target.saveAll(null),
                message
        );
    }

    @Test
    public void update() {
        target.save(sportCar);
        sportCar.setModel("updated model");
        target.update(sportCar);
        SportCar updatedAuto = new SportCar.Builder()
                .setBodyType("BodyType")
                .setPrice(BigDecimal.TEN)
                .setManufacturer(Manufacturer.BMW)
                .setModel("updated model")
                .setMaxSpeed(300)
                .build();
        Assertions.assertEquals(updatedAuto.getModel(), sportCar.getModel());
    }

    @Test
    public void delete_success() {
        target.save(sportCar);
        boolean actual = target.delete(sportCar.getId());
        Assertions.assertTrue(actual);
    }

    @Test
    public void delete_fail() {
        target.save(sportCar);
        boolean actual = target.delete("not exist");
        Assertions.assertFalse(actual);
    }
}
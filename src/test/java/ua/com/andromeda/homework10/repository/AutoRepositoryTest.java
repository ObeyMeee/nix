package ua.com.andromeda.homework10.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ua.com.andromeda.homework10.model.Auto;
import ua.com.andromeda.homework10.model.Manufacturer;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class AutoRepositoryTest {

    private AutoRepository target;
    private Auto auto;

    @Before
    public void setUp() {
        target = new AutoRepository();
        auto = new Auto("Model", Manufacturer.BMW, BigDecimal.TEN, "BodyType");
    }

    @Test
    public void getById_notExist() {
        target.save(auto);
        Optional<Auto> actual = target.findById("not exist");
        Assertions.assertEquals(Optional.empty(), actual);
    }


    @Test
    public void getById_exist() {
        target.save(auto);
        Optional<Auto> actual = target.findById(auto.getId());
        Assertions.assertEquals(auto, actual.get());
    }


    @Test
    public void getAll_emptyList() {
        List<Auto> expected = new LinkedList<>();
        List<Auto> actual = target.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAll() {
        target.save(auto);
        List<Auto> expected = new LinkedList<>();
        expected.add(auto);
        List<Auto> actual = target.getAll();
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
        auto.setPrice(BigDecimal.ZERO);
        target.save(auto);
        BigDecimal expected = BigDecimal.valueOf(-1);
        BigDecimal actual = auto.getPrice();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void save_checkListLength() {
        target.save(auto);
        int expected = 1;
        int actual = target.getAll().size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void saveAll_null() {
        String message = "auto cannot be null";
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> target.saveAll(null),
                message
        );
    }

    @Test
    public void update() {
        target.save(auto);
        auto.setModel("updated model");
        target.update(auto);
        Auto updatedAuto = new Auto("updated model", Manufacturer.BMW, BigDecimal.TEN, "BodyType");
        Assertions.assertEquals(updatedAuto.getModel(), auto.getModel());
    }

    @Test
    public void delete_success() {
        target.save(auto);
        boolean actual = target.delete(auto.getId());
        Assertions.assertTrue(actual);
    }

    @Test
    public void delete_fail() {
        target.save(auto);
        boolean actual = target.delete("not exist");
        Assertions.assertFalse(actual);
    }
}
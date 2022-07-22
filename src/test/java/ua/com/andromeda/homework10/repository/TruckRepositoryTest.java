package ua.com.andromeda.homework10.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.Truck;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class TruckRepositoryTest {

    private TruckRepository target;
    private Truck truck;

    @Before
    public void setUp() {
        target = new TruckRepository();
        truck = new Truck("Model", Manufacturer.BMW, BigDecimal.TEN, "BodyType", 2000);
    }

    @Test
    public void getById_notExist() {
        target.save(truck);
        Optional<Truck> actual = target.findById("not exist");
        Assertions.assertEquals(Optional.empty(), actual);
    }


    @Test
    public void getById_exist() {
        target.save(truck);
        Optional<Truck> actual = target.findById(truck.getId());
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(truck, actual.get());
    }


    @Test
    public void getAll_emptyList() {
        List<Truck> expected = new LinkedList<>();
        List<Truck> actual = target.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAll() {
        target.save(truck);
        List<Truck> expected = new LinkedList<>();
        expected.add(truck);
        List<Truck> actual = target.getAll();
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
        truck.setPrice(BigDecimal.ZERO);
        target.save(truck);
        BigDecimal expected = BigDecimal.valueOf(-1);
        BigDecimal actual = truck.getPrice();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void save_checkListLength() {
        target.save(truck);
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
        target.save(truck);
        truck.setModel("updated model");
        target.update(truck);
        Truck updatedAuto = new Truck("updated model", Manufacturer.BMW, BigDecimal.TEN, "BodyType", 300);
        Assertions.assertEquals(updatedAuto.getModel(), truck.getModel());
    }

    @Test
    public void delete_success() {
        target.save(truck);
        boolean actual = target.delete(truck.getId());
        Assertions.assertTrue(actual);
    }

    @Test
    public void delete_fail() {
        target.save(truck);
        boolean actual = target.delete("not exist");
        Assertions.assertFalse(actual);
    }
}
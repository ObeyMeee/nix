package ua.com.andromeda.homework10.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import ua.com.andromeda.homework10.StringMatchers;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.Truck;
import ua.com.andromeda.homework10.repository.TruckRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class TruckServiceTest {

    private TruckService target;
    private TruckRepository truckRepository;

    @Before
    public void setUp() {
        truckRepository = Mockito.mock(TruckRepository.class);
        target = new TruckService(truckRepository);
    }

    @Test
    public void createAndSaveTrucks_negativeCount() {
        String message = "count can't be less than 0";
        assertThrows(message,
                IllegalArgumentException.class,
                () -> target.createAndSaveTrucks(-1)
        );
    }

    @Test
    public void createAndSaveTrucks_zeroCount() {
        List<Truck> expected = Collections.EMPTY_LIST;
        List<Truck> actual = target.createAndSaveTrucks(0);
        assertEquals(expected, actual);
    }

    @Test
    public void createAndSaveTrucks_countOne() {
        List<Truck> actual = target.createAndSaveTrucks(1);
        assertEquals(1, actual.size());
        Mockito.verify(truckRepository).save(Mockito.any());
    }


    @Test
    public void createAndSaveTrucks_CountFive() {
        List<Truck> actual = target.createAndSaveTrucks(5);
        assertEquals(5, actual.size());
        Mockito.verify(truckRepository, Mockito.times(5)).save(Mockito.any());
    }

    private Truck createSimpleAuto() {
        return new Truck("simple model", Manufacturer.BMW, BigDecimal.TEN, "body type", 2000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveTruck_null() {
        Mockito.when(truckRepository.save(null)).thenThrow(new IllegalArgumentException());
        target.save(null);
    }

    @Test
    public void printAll() {
        List<Truck> trucks = Arrays.asList(createSimpleAuto(), createSimpleAuto(), createSimpleAuto());
        Mockito.when(truckRepository.getAll()).thenReturn(trucks);
        target.printAll();
    }

    @Test
    public void findOneById_null_matcher() {
        Truck actual = target.findOneById(null);
        ArgumentMatcher<String> matcher = new StringMatchers("234");
        Mockito.verify(truckRepository).getById(Mockito.argThat(matcher));
        assertNull(actual);
    }

    @Test
    public void findOneById_null_captor() {
        target.findOneById(null);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(truckRepository).getById(argumentCaptor.capture());
        assertEquals("234", argumentCaptor.getValue());
    }

}
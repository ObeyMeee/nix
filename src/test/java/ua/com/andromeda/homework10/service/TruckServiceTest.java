package ua.com.andromeda.homework10.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import ua.com.andromeda.homework10.StringMatchers;
import ua.com.andromeda.homework10.dto.TruckDto;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.Truck;
import ua.com.andromeda.homework10.repository.TruckRepository;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

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
        Mockito.verify(truckRepository, Mockito.times(2)).save(Mockito.any());
    }


    @Test
    public void createAndSaveTrucks_CountFive() {
        List<Truck> actual = target.createAndSaveTrucks(5);
        assertEquals(5, actual.size());
        Mockito.verify(truckRepository, Mockito.times(6)).save(Mockito.any());
    }

    private Truck createSimpleTruck() {
        return new Truck("simple model", Manufacturer.BMW, BigDecimal.TEN, "body type", 2000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveTruck_null() {
        Mockito.when(truckRepository.save(null)).thenThrow(new IllegalArgumentException());
        target.save(null);
    }

    @Test
    public void printAll() {
        List<Truck> trucks = Arrays.asList(createSimpleTruck(), createSimpleTruck(), createSimpleTruck());
        Mockito.when(truckRepository.getAll()).thenReturn(trucks);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        target.printAll();
        StringBuilder stringBuilder = truckListToStringBuilder(trucks);
        assertEquals(stringBuilder.toString(), outputStream.toString());
    }

    private StringBuilder truckListToStringBuilder(List<Truck> trucks) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Truck truck : trucks) {
            stringBuilder.append(truck).append(System.lineSeparator());
        }
        return stringBuilder;
    }

    @Test
    public void findOneById_notFound_matcher() {
        Optional<Truck> actual = target.findOneById(null);
        ArgumentMatcher<String> matcher = new StringMatchers("");
        Mockito.verify(truckRepository).findById(Mockito.argThat(matcher));
        assertEquals(Optional.empty(), actual);
    }

    @Test
    public void findOneById_null_captor() {
        target.findOneById(null);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(truckRepository).findById(argumentCaptor.capture());
        assertEquals("", argumentCaptor.getValue());
    }

    @Test
    public void ifPresent_success() {
        Truck truck = createAndSaveSimpleAuto();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        when(truckRepository.findById(truck.getId())).thenReturn(Optional.of(truck));
        target.ifPresent(truck.getId());

        String expected = "Founded truck ==> " + truck + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    private Truck createAndSaveSimpleAuto() {
        Truck truck = createSimpleTruck();
        target.save(truck);
        return truck;
    }

    @Test
    public void ifPresent_fail() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        target.ifPresent("");
        String expected = "";
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void orElse_success() {
        Truck truck = createAndSaveSimpleAuto();
        when(truckRepository.findById(truck.getId())).thenReturn(Optional.of(truck));
        Truck founded = target.orElse(truck.getId());
        assertEquals(truck, founded);
    }

    @Test
    public void orElse_fail() {
        Truck founded = target.orElse("");
        Truck expected = new Truck("new Model", Manufacturer.BMW, BigDecimal.TEN, "body type", 10000);
        assertEquals(expected, founded);
    }

    @Test
    public void orElseGet_success() {
        Truck truck = createAndSaveSimpleAuto();
        when(truckRepository.findById(truck.getId())).thenReturn(Optional.of(truck));
        Truck founded = target.orElseGet(truck.getId());
        assertEquals(truck, founded);
    }

    @Test
    public void orElseGet_fail() {
        Truck expected = new Truck("model-23", Manufacturer.BMW, BigDecimal.ZERO,
                "body22", 15000);

        Truck founded = target.orElseGet("");
        assertEquals(expected, founded);
    }

    @Test
    public void map_success() {
        Truck truck = createAndSaveSimpleAuto();
        when(truckRepository.findById(truck.getId())).thenReturn(Optional.of(truck));
        Optional<TruckDto> actual = target.map(truck.getId());
        assertTrue(actual.isPresent());
        Optional<TruckDto> expected = Optional.of(new TruckDto(truck.getModel(), truck.getPrice(),
                truck.getManufacturer(), truck.getBodyType(), 11111));
        assertEquals(expected, actual);
    }

    @Test
    public void map_fail() {
        Optional<TruckDto> truckDto = target.map("");
        assertFalse(truckDto.isPresent());
    }

    @Test
    public void orElseThrow_success() {
        Truck truck = createSimpleTruck();
        when(truckRepository.findById(truck.getId())).thenReturn(Optional.of(truck));
        Truck actual = target.orElseThrow(truck.getId());
        assertEquals(truck, actual);
    }

    @Test(expected = RuntimeException.class)
    public void orElseThrow_fail() {
        target.orElseThrow("");
    }

    @Test
    public void or_success() {
        Truck truck = createSimpleTruck();
        when(truckRepository.findById(truck.getId())).thenReturn(Optional.of(truck));
        Optional<Truck> optionalTruck = target.or(truck.getId());
        assertTrue(optionalTruck.isPresent());
        assertEquals(truck, optionalTruck.get());
    }

    @Test
    public void or_fail() {
        Optional<Truck> optionalTruck = target.or("");
        Truck expected = new Truck("new Model", Manufacturer.BMW,
                BigDecimal.TEN, "body type", 10000);
        assertEquals(Optional.of(expected), optionalTruck);
    }
}
package ua.com.andromeda.homework10.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import ua.com.andromeda.homework10.StringMatchers;
import ua.com.andromeda.homework10.dto.SportCarDto;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.SportCar;
import ua.com.andromeda.homework10.repository.SportCarRepository;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SportCarServiceTest {

    private SportCarService target;
    private SportCarRepository sportCarRepository;

    @Before
    public void setUp() {
        sportCarRepository = Mockito.mock(SportCarRepository.class);
        target = new SportCarService(sportCarRepository);
    }

    @Test
    public void createAndSaveSportCars_negativeCount() {
        String message = "count can't be less than 0";
        assertThrows(message,
                IllegalArgumentException.class,
                () -> target.createAndSaveSportCars(-1)
        );
    }

    @Test
    public void createAndSaveSportCars_zeroCount() {
        List<SportCar> expected = Collections.EMPTY_LIST;
        List<SportCar> actual = target.createAndSaveSportCars(0);
        assertEquals(expected, actual);
    }

    @Test
    public void createAndSaveSportCars_countOne() {
        List<SportCar> actual = target.createAndSaveSportCars(1);
        assertEquals(1, actual.size());
        Mockito.verify(sportCarRepository, Mockito.times(2)).save(Mockito.any());
    }


    @Test
    public void createAndSaveSportCars_CountFive() {
        List<SportCar> actual = target.createAndSaveSportCars(5);
        assertEquals(5, actual.size());
        Mockito.verify(sportCarRepository, Mockito.times(6)).save(Mockito.any());
    }

    private SportCar createSimpleSportCar() {
        return new SportCar("simple model", Manufacturer.BMW, BigDecimal.TEN, "body type", 200);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveSportCars_null() {
        Mockito.when(sportCarRepository.save(null)).thenThrow(new IllegalArgumentException());
        target.save(null);
    }

    @Test
    public void printAll() {
        List<SportCar> sportCars = Arrays.asList(createSimpleSportCar(), createSimpleSportCar(), createSimpleSportCar());
        Mockito.when(sportCarRepository.getAll()).thenReturn(sportCars);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        target.printAll();
        StringBuilder stringBuilder = sportCarListToStringBuilder(sportCars);
        assertEquals(stringBuilder.toString(), outputStream.toString());
    }

    private StringBuilder sportCarListToStringBuilder(List<SportCar> sportCars) {
        StringBuilder stringBuilder = new StringBuilder();
        for (SportCar sportCar : sportCars) {
            stringBuilder.append(sportCar).append(System.lineSeparator());
        }
        return stringBuilder;
    }

    @Test
    public void findOneById_notFound_matcher() {
        Optional<SportCar> actual = target.findOneById(null);
        ArgumentMatcher<String> matcher = new StringMatchers("");
        Mockito.verify(sportCarRepository).findById(Mockito.argThat(matcher));
        assertEquals(Optional.empty(), actual);
    }

    @Test
    public void findOneById_null_captor() {
        target.findOneById(null);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(sportCarRepository).findById(argumentCaptor.capture());
        assertEquals("", argumentCaptor.getValue());
    }

    @Test
    public void ifPresent_success() {
        SportCar sportCar = createAndSaveSimpleAuto();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        when(sportCarRepository.findById(sportCar.getId())).thenReturn(Optional.of(sportCar));
        target.ifPresent(sportCar.getId());

        String expected = "Founded sport car ==> " + sportCar + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    private SportCar createAndSaveSimpleAuto() {
        SportCar sportCar = createSimpleSportCar();
        target.save(sportCar);
        return sportCar;
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
        SportCar sportCar = createAndSaveSimpleAuto();
        when(sportCarRepository.findById(sportCar.getId())).thenReturn(Optional.of(sportCar));
        SportCar founded = target.orElse(sportCar.getId());
        assertEquals(sportCar, founded);
    }

    @Test
    public void orElse_fail() {
        SportCar founded = target.orElse("");
        SportCar expected = new SportCar("new Model", Manufacturer.BMW, BigDecimal.TEN, "body type", 200);
        assertEquals(expected, founded);
    }

    @Test
    public void orElseGet_success() {
        SportCar sportCar = createAndSaveSimpleAuto();
        when(sportCarRepository.findById(sportCar.getId())).thenReturn(Optional.of(sportCar));
        SportCar founded = target.orElseGet(sportCar.getId());
        assertEquals(sportCar, founded);
    }

    @Test
    public void orElseGet_fail() {
        SportCar expected = new SportCar("model-23", Manufacturer.ZAZ, BigDecimal.ZERO,
                "body22", 234);

        SportCar founded = target.orElseGet("");
        assertEquals(expected, founded);
    }

    @Test
    public void map_success() {
        SportCar sportCar = createAndSaveSimpleAuto();
        when(sportCarRepository.findById(sportCar.getId())).thenReturn(Optional.of(sportCar));
        Optional<SportCarDto> actual = target.map(sportCar.getId());
        assertTrue(actual.isPresent());
        Optional<SportCarDto> expected = Optional.of(new SportCarDto(sportCar.getModel(), sportCar.getPrice(),
                sportCar.getManufacturer(), sportCar.getBodyType(), 234));
        assertEquals(expected, actual);
    }

    @Test
    public void map_fail() {
        Optional<SportCarDto> sportCarDto = target.map("");
        assertFalse(sportCarDto.isPresent());
    }

    @Test
    public void orElseThrow_success() {
        SportCar sportCar = createSimpleSportCar();
        when(sportCarRepository.findById(sportCar.getId())).thenReturn(Optional.of(sportCar));
        SportCar actual = target.orElseThrow(sportCar.getId());
        assertEquals(sportCar, actual);
    }

    @Test(expected = RuntimeException.class)
    public void orElseThrow_fail() {
        target.orElseThrow("");
    }

    @Test
    public void or_success() {
        SportCar sportCar = createSimpleSportCar();
        when(sportCarRepository.findById(sportCar.getId())).thenReturn(Optional.of(sportCar));
        Optional<SportCar> optionalSportCar = target.or(sportCar.getId());
        assertTrue(optionalSportCar.isPresent());
        assertEquals(sportCar, optionalSportCar.get());
    }

    @Test
    public void or_fail() {
        Optional<SportCar> optionalSportCar = target.or("");
        SportCar expected = new SportCar("new Model", Manufacturer.BMW,
                BigDecimal.TEN, "body type", 200);
        assertEquals(Optional.of(expected), optionalSportCar);
    }
}
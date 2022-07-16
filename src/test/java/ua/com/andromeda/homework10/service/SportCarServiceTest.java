package ua.com.andromeda.homework10.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import ua.com.andromeda.homework10.StringMatchers;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.SportCar;
import ua.com.andromeda.homework10.repository.SportCarRepository;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

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
        Mockito.verify(sportCarRepository).save(Mockito.any());
    }


    @Test
    public void createAndSaveSportCars_CountFive() {
        List<SportCar> actual = target.createAndSaveSportCars(5);
        assertEquals(5, actual.size());
        Mockito.verify(sportCarRepository, Mockito.times(5)).save(Mockito.any());
    }

    private SportCar createSimpleSportCar() {
        return new SportCar("simple model", Manufacturer.BMW, BigDecimal.TEN, "body type", 2000);
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
        for (SportCar sportCar: sportCars) {
            stringBuilder.append(sportCar).append(System.lineSeparator());
        }
        return stringBuilder;
    }

    @Test
    public void findOneById_null_matcher() {
        SportCar actual = target.findOneById(null);
        ArgumentMatcher<String> matcher = new StringMatchers("234");
        Mockito.verify(sportCarRepository).getById(Mockito.argThat(matcher));
        assertNull(actual);
    }

    @Test
    public void findOneById_null_captor() {
        target.findOneById(null);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(sportCarRepository).getById(argumentCaptor.capture());
        assertEquals("234", argumentCaptor.getValue());
    }

}
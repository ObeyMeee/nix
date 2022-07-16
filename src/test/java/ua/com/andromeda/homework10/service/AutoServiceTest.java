package ua.com.andromeda.homework10.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import ua.com.andromeda.homework10.StringMatchers;
import ua.com.andromeda.homework10.model.Auto;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.repository.AutoRepository;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class AutoServiceTest {

    private AutoService target;
    private AutoRepository autoRepository;

    @Before
    public void setUp() {
        autoRepository = Mockito.mock(AutoRepository.class);
        target = new AutoService(autoRepository);
    }

    @Test
    public void createAndSaveAutos_negativeCount() {
        String message = "count can't be less than 0";
        assertThrows(message,
                IllegalArgumentException.class,
                () -> target.createAndSaveAutos(-1)
        );
    }

    @Test
    public void createAndSaveAutos_zeroCount() {
        List<Auto> expected = Collections.EMPTY_LIST;
        List<Auto> actual = target.createAndSaveAutos(0);
        assertEquals(expected, actual);
    }

    @Test
    public void createAndSaveAutos_countOne() {
        List<Auto> actual = target.createAndSaveAutos(1);
        assertEquals(1, actual.size());
        Mockito.verify(autoRepository).save(Mockito.any());
    }


    @Test
    public void createAndSaveAutos_CountFive() {
        List<Auto> actual = target.createAndSaveAutos(5);
        assertEquals(5, actual.size());
        Mockito.verify(autoRepository, Mockito.times(5)).save(Mockito.any());
    }

    private Auto createSimpleAuto() {
        return new Auto("simple model", Manufacturer.BMW, BigDecimal.TEN, "body type");
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveAutos_null() {
        Mockito.when(autoRepository.save(null)).thenThrow(new IllegalArgumentException());
        target.save(null);
    }

    @Test
    public void printAll() {
        List<Auto> autos = Arrays.asList(createSimpleAuto(), createSimpleAuto(), createSimpleAuto());
        Mockito.when(autoRepository.getAll()).thenReturn(autos);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        target.printAll();
        StringBuilder stringBuilder = autoListToStringBuilder(autos);
        assertEquals(stringBuilder.toString(), outputStream.toString());
    }

    private StringBuilder autoListToStringBuilder(List<Auto> autos) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Auto auto: autos) {
            stringBuilder.append(auto).append(System.lineSeparator());
        }
        return stringBuilder;
    }

    @Test
    public void findOneById_null_matcher() {
        Auto actual = target.findOneById(null);
        ArgumentMatcher<String> matcher = new StringMatchers("234");
        Mockito.verify(autoRepository).getById(Mockito.argThat(matcher));
        assertNull(actual);
    }

    @Test
    public void findOneById_null_captor() {
        target.findOneById(null);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(autoRepository).getById(argumentCaptor.capture());
        assertEquals("234", argumentCaptor.getValue());
    }

}
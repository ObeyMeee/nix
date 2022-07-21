package ua.com.andromeda.homework10.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import ua.com.andromeda.homework10.StringMatchers;
import ua.com.andromeda.homework10.dto.AutoDto;
import ua.com.andromeda.homework10.model.Auto;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.repository.AutoRepository;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class AutoServiceTest {

    private AutoService target;
    private AutoRepository autoRepository;

    @Before
    public void setUp() {
        autoRepository = Mockito.mock(AutoRepository.class);
        target = new AutoService(autoRepository);
    }

    private Auto createSimpleAuto() {
        return new Auto("simple model", Manufacturer.BMW, BigDecimal.TEN, "body type");
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
        Mockito.verify(autoRepository, Mockito.times(2)).save(Mockito.any());
    }


    @Test
    public void createAndSaveAutos_CountFive() {
        List<Auto> actual = target.createAndSaveAutos(5);
        assertEquals(5, actual.size());
        Mockito.verify(autoRepository, Mockito.times(6)).save(Mockito.any());
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
        for (Auto auto : autos) {
            stringBuilder.append(auto).append(System.lineSeparator());
        }
        return stringBuilder;
    }

    @Test
    public void findOneById_notFound_matcher() {
        Optional<Auto> actual = target.findOneById(null);
        ArgumentMatcher<String> matcher = new StringMatchers("");
        Mockito.verify(autoRepository).findById(Mockito.argThat(matcher));
        assertEquals(Optional.empty(), actual);
    }

    @Test
    public void findOneById_null_captor() {
        target.findOneById(null);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(autoRepository).findById(argumentCaptor.capture());
        assertEquals("", argumentCaptor.getValue());
    }

    @Test
    public void ifPresent_success() {
        Auto auto = createAndSaveSimpleAuto();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        when(autoRepository.findById(auto.getId())).thenReturn(Optional.of(auto));
        target.ifPresent(auto.getId());

        String expected = "Founded auto ==> " + auto + System.lineSeparator();
        assertEquals(expected, outputStream.toString());
    }

    private Auto createAndSaveSimpleAuto() {
        Auto auto = createSimpleAuto();
        target.save(auto);
        return auto;
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
        Auto auto = createSimpleAuto();
        when(autoRepository.findById(auto.getId())).thenReturn(Optional.of(auto));
        Auto founded = target.orElse(auto.getId());
        assertEquals(auto, founded);
    }

    @Test
    public void orElse_fail() {
        Auto founded = target.orElse("");
        Auto expected = new Auto("new Model", Manufacturer.BMW, BigDecimal.TEN, "body type");
        assertEquals(expected, founded);
    }

    @Test
    public void orElseGet_success() {
        Auto auto = createAndSaveSimpleAuto();
        when(autoRepository.findById(auto.getId())).thenReturn(Optional.of(auto));
        Auto founded = target.orElseGet(auto.getId());
        assertEquals(auto, founded);
    }

    @Test
    public void orElseGet_fail() {
        Auto expected = new Auto("model-23", Manufacturer.OPEL, BigDecimal.ZERO, "body22");
        Auto founded = target.orElseGet("");
        assertEquals(expected, founded);
    }

    @Test
    public void map_success() {
        Auto auto = createAndSaveSimpleAuto();
        when(autoRepository.findById(auto.getId())).thenReturn(Optional.of(auto));
        Optional<AutoDto> actual = target.map(auto.getId());
        assertTrue(actual.isPresent());
        Optional<AutoDto> expected = Optional.of(new AutoDto(auto.getModel(), auto.getPrice(),
                auto.getManufacturer(), auto.getBodyType()));
        assertEquals(expected, actual);
    }

    @Test
    public void map_fail() {
        Optional<AutoDto> autoDto = target.map("");
        assertFalse(autoDto.isPresent());
    }

    @Test
    public void orElseThrow_success() {
        Auto auto = createSimpleAuto();
        when(autoRepository.findById(auto.getId())).thenReturn(Optional.ofNullable(auto));
        Auto actual = target.orElseThrow(auto.getId());
        assertEquals(auto, actual);
    }

    @Test(expected = RuntimeException.class)
    public void orElseThrow_fail() {
        target.orElseThrow("");
    }

    @Test
    public void or_success() {
        Auto auto = createSimpleAuto();
        when(autoRepository.findById(auto.getId())).thenReturn(Optional.of(auto));
        Optional<Auto> optionalAuto = target.or(auto.getId());
        assertTrue(optionalAuto.isPresent());
        assertEquals(auto, optionalAuto.get());
    }

    @Test
    public void or_fail() {
        Optional<Auto> optionalAuto = target.or("");
        Optional<Auto> expected = Optional.of(new Auto("new Model", Manufacturer.BMW,
                BigDecimal.TEN, "body type"));
        assertEquals(expected, optionalAuto);
    }
}
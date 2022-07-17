package ua.com.andromeda.homework10.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.andromeda.homework10.dto.AutoDto;
import ua.com.andromeda.homework10.model.Auto;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.repository.AutoRepository;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class AutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoService.class);
    private static final Random RANDOM = new Random();
    private final AutoRepository autoRepository;

    private final Auto simpleAuto;

    public AutoService(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
        simpleAuto = createSimpleAuto();
        autoRepository.save(simpleAuto);
    }

    private Auto createSimpleAuto() {
        return new Auto("new Model", Manufacturer.BMW, BigDecimal.TEN, "body type");
    }

    public List<Auto> createAndSaveAutos(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count can't be less than 0");
        }

        List<Auto> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Auto auto = new Auto(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble() * 1000),
                    "Model-" + RANDOM.nextInt(1000)
            );
            result.add(auto);
            autoRepository.save(auto);
            LOGGER.debug("Created auto {}", auto.getId());
        }
        return result;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveAutos(List<Auto> autos) {
        autoRepository.saveAll(autos);
    }

    public void printAll() {
        for (Auto auto : autoRepository.getAll()) {
            System.out.println(auto);
        }
    }

    public Optional<Auto> findOneById(String id) {
        return id == null ? autoRepository.findById("") :
                autoRepository.findById(id);
    }

    public boolean save(Auto auto) {
        if (auto == null) {
            throw new IllegalArgumentException();
        }
        autoRepository.save(auto);
        return true;
    }

    public void showOptionalExamples() {
        String id = simpleAuto.getId();
        filter(id);
        ifPresentUpdateAutoOrElseSave(simpleAuto);
        ifPresent(id);
        orElse(id);
        orElseGet(id);
        map(id);
        try {
            orElseThrow(id);
        } catch (RuntimeException exception) {
            System.err.println(exception.getMessage());
        }
        or(id);
    }

    public void filter(String id) {
        findOneById(id);
    }

    public void ifPresentUpdateAutoOrElseSave(Auto auto) {
        autoRepository.update(auto);
    }


    public void ifPresent(String id) {
        findOneById(id).ifPresent(auto -> System.out.println("Founded auto ==> " + auto));
    }

    public Auto orElse(String id) {
        return findOneById(id).orElse(createSimpleAuto());

    }

    public Auto orElseGet(String id) {
        Auto auto = findOneById(id).orElseGet(() -> {
            System.out.println("Creating new auto...");
            String model = "model-23";
            return new Auto(model, Manufacturer.OPEL, BigDecimal.ZERO, "body22");
        });
        System.out.println("auto has been successfully created");
        return auto;
    }

    public Optional<AutoDto> map(String id) {
        Optional<AutoDto> autoDto = findOneById(id)
                .map(auto -> new AutoDto(auto.getModel(), auto.getPrice(), auto.getManufacturer(), auto.getBodyType()));

        return autoDto;
    }

    public Auto orElseThrow(String id) {
        Auto auto = null;
        auto = findOneById(id).orElseThrow(() -> {
            throw new RuntimeException("Cannot find auto by id = '" + id + "'");
        });
        return auto;
    }

    public Optional<Auto> or(String id) {
        Optional<Auto> auto = findOneById(id).or(
                () -> Optional.of(new Auto("new Model", Manufacturer.BMW,
                        BigDecimal.TEN, "body type")));

        return auto;
    }
}

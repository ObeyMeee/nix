package ua.com.andromeda.homework10.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.andromeda.homework10.dto.SportCarDto;
import ua.com.andromeda.homework10.model.Auto;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.model.SportCar;
import ua.com.andromeda.homework10.repository.SportCarRepository;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SportCarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SportCarService.class);
    private static final Random RANDOM = new Random();
    private final SportCarRepository sportCarRepository;
    private final SportCar simpleSportCar;

    public SportCarService(SportCarRepository sportCarRepository) {
        this.sportCarRepository = sportCarRepository;
        simpleSportCar = createSimpleSportCar();
        sportCarRepository.save(simpleSportCar);
    }

    private SportCar createSimpleSportCar() {
        return new SportCar("new Model", Manufacturer.BMW, BigDecimal.TEN, "body type", 200);
    }

    public List<SportCar> createAndSaveSportCars(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count can't be less than 0");
        }

        List<SportCar> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final SportCar sportCar = new SportCar(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble() * 1000),
                    "Model-" + RANDOM.nextInt(1000),
                    300
            );
            result.add(sportCar);
            sportCarRepository.save(sportCar);
            LOGGER.debug("Created auto {}", sportCar.getId());
        }
        return result;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveSportCars(List<SportCar> sportCars) {
        sportCarRepository.saveAll(sportCars);
    }

    public void printAll() {
        for (Auto auto : sportCarRepository.getAll()) {
            System.out.println(auto);
        }
    }

    public Optional<SportCar> findOneById(String id) {
        return id == null ? sportCarRepository.findById("") :
                sportCarRepository.findById(id);
    }

    public boolean save(SportCar sportCar) {
        if (sportCar == null) {
            throw new IllegalArgumentException();
        }
        sportCarRepository.save(sportCar);
        return true;
    }

    public void showOptionalExamples() {
        String id = simpleSportCar.getId();
        filter(id);
        ifPresentUpdateSportCarElseSave(simpleSportCar);
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

    public void ifPresentUpdateSportCarElseSave(SportCar sportCar) {
        sportCarRepository.update(sportCar);
    }

    public void ifPresent(String id) {
        findOneById(id).ifPresent(sportCar -> System.out.println("Founded sport car ==> " + sportCar));
    }

    public SportCar orElse(String id) {
        return findOneById(id).orElse(createSimpleSportCar());
    }

    public SportCar orElseGet(String id) {
        return findOneById(id).orElseGet(() -> {
            System.out.println("Creating new auto...");
            String model = "model-23";
            return new SportCar(model, Manufacturer.ZAZ, BigDecimal.ZERO, "body22", 234);
        });
    }

    public Optional<SportCarDto> map(String id) {


        return findOneById(id)
                .map(auto -> new SportCarDto(auto.getModel(), auto.getPrice(),
                        auto.getManufacturer(), auto.getBodyType(), 234));
    }

    public SportCar orElseThrow(String id) {
        SportCar sportCar;
        sportCar = findOneById(id).orElseThrow(() -> {
            throw new RuntimeException("Cannot find sport car by id = " + id);
        });
        return sportCar;
    }

    public Optional<SportCar> or(String id) {
        return findOneById(id).or(() -> Optional.of(createSimpleSportCar()));
    }
}

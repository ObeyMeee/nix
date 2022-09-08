package ua.com.andromeda.service;

import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.annotations.Qualifier;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.model.Invoice;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.model.cars.SportCar;
import ua.com.andromeda.model.cars.Truck;
import ua.com.andromeda.model.cars.Vehicle;
import ua.com.andromeda.repository.mongodb.InvoiceRepositoryMongoDbImpl;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Singleton
public class InvoiceService {
    private static final Random RANDOM = new Random();
    private final InvoiceRepositoryMongoDbImpl invoiceRepository;
    private final AutoService autoService;
    private final SportCarService sportCarService;
    private final TruckService truckService;

    @Autowired
    public InvoiceService(@Qualifier(InvoiceRepositoryMongoDbImpl.class) InvoiceRepositoryMongoDbImpl invoiceRepository,
                          AutoService autoService,
                          SportCarService sportCarService,
                          TruckService truckService) {

        this.invoiceRepository = invoiceRepository;
        this.autoService = autoService;
        this.sportCarService = sportCarService;
        this.truckService = truckService;
    }

    public void createAndSaveInvoice(int vehicleAmount) {

        Invoice invoice = new Invoice();
        List<Vehicle> vehicles = getRandomVehicles(vehicleAmount);
        vehicles.forEach(invoice::add);
        invoiceRepository.save(invoice);
    }

    private List<Vehicle> getRandomVehicles(int vehicleAmount) {
        List<Vehicle> vehicles = new LinkedList<>();
        int amountAuto = createAndSaveAutos(vehicles, vehicleAmount);
        int amountSportCarAndTruck = vehicleAmount - amountAuto;
        if (amountSportCarAndTruck != 0) {
            int amountSportCar = createAndSaveSportCars(vehicles, amountSportCarAndTruck);
            int amountTrucks = amountSportCarAndTruck - amountSportCar;
            if (amountTrucks != 0) {
                createAndSaveTrucks(vehicles, amountTrucks);
            }
        }
        return vehicles;
    }

    private int createAndSaveAutos(List<Vehicle> vehicles, int vehiclesAmount) {
        int autosAmount = RANDOM.nextInt(vehiclesAmount);
        List<Auto> autos = autoService.createVehicles(autosAmount);
        vehicles.addAll(autos);
        return autosAmount;
    }

    private int createAndSaveSportCars(List<Vehicle> vehicles, int amountSportCarAndTruck) {
        int sportCarsAmount = RANDOM.nextInt(amountSportCarAndTruck);
        List<SportCar> sportCars = sportCarService.createVehicles(sportCarsAmount);
        vehicles.addAll(sportCars);
        return sportCarsAmount;
    }

    private void createAndSaveTrucks(List<Vehicle> vehicles, int truckAmount) {
        List<Truck> trucks = truckService.createVehicles(truckAmount);
        vehicles.addAll(trucks);
    }

    public List<Invoice> getInvoicesWhereTotalPriceGreaterThan(BigDecimal totalPrice) {
        return invoiceRepository.getInvoicesWhereTotalPriceGreaterThan(totalPrice);
    }

    public int getInvoicesCount() {
        return invoiceRepository.getInvoicesCount();
    }

    public void updateTime(String id) {
        invoiceRepository.updateTime(id);
    }

    public Map<BigDecimal, Integer> groupByTotalPrice() {
        return invoiceRepository.groupByTotalPrice();
    }
}

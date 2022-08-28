package ua.com.andromeda.homework10.service;

import ua.com.andromeda.homework10.model.*;
import ua.com.andromeda.homework10.repository.InvoiceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class InvoiceService {
    private static final InvoiceRepository INVOICE_REPOSITORY = InvoiceRepository.getInstance();
    private static final Random RANDOM = new Random();
    private static final AutoService autoService = AutoService.getInstance();
    private static final SportCarService sportCarService = SportCarService.getInstance();
    private static final TruckService truckService = TruckService.getInstance();
    private static InvoiceService instance;

    private InvoiceService() {

    }

    public static InvoiceService getInstance() {
        if (instance == null) {
            instance = new InvoiceService();
        }
        return instance;
    }

    public void createAndSaveInvoice(int vehicleAmount) {

        Invoice invoice = new Invoice();
        invoice.setCreated(LocalDateTime.now());
        String invoiceId = UUID.randomUUID().toString();
        invoice.setId(invoiceId);
        List<Vehicle> vehicles = getRandomVehicles(vehicleAmount, invoiceId);

        invoice.setVehicles(vehicles);
        INVOICE_REPOSITORY.save(invoice);
    }

    private List<Vehicle> getRandomVehicles(int vehicleAmount, String invoiceId) {
        List<Vehicle> vehicles = new LinkedList<>();
        int amountAuto = createAndSaveAutos(vehicles, vehicleAmount, invoiceId);
        int amountSportCarAndTruck = vehicleAmount - amountAuto;
        if (amountSportCarAndTruck != 0) {
            int amountSportCar = createAndSaveSportCars(vehicles, amountSportCarAndTruck, invoiceId);
            int amountTrucks = amountSportCarAndTruck - amountSportCar;
            if (amountTrucks != 0) {
                createAndSaveTrucks(vehicles, amountTrucks, invoiceId);
            }
        }
        return vehicles;
    }

    private int createAndSaveAutos(List<Vehicle> vehicles, int vehicleAmount, String invoiceId) {
        int amountAuto = RANDOM.nextInt(vehicleAmount);
        List<Auto> autos = autoService.createVehicles(amountAuto);
        autos.forEach(auto -> {
            auto.setInvoiceId(invoiceId);
            autoService.save(auto);
            vehicles.add(auto);
        });
        return amountAuto;
    }

    private int createAndSaveSportCars(List<Vehicle> vehicles, int amountSportCarAndTruck, String invoiceId) {
        int amountSportCar = RANDOM.nextInt(amountSportCarAndTruck);
        List<SportCar> sportCars = sportCarService.createVehicles(amountSportCar);
        sportCars.forEach(sportCar -> {
            sportCar.setInvoiceId(invoiceId);
            sportCarService.save(sportCar);
            vehicles.add(sportCar);
        });
        return amountSportCar;
    }

    private void createAndSaveTrucks(List<Vehicle> vehicles, int amountTrucks, String invoiceId) {
        List<Truck> trucks = truckService.createVehicles(amountTrucks);
        trucks.forEach(truck -> {
            truck.setInvoiceId(invoiceId);
            truckService.save(truck);
            vehicles.add(truck);
        });
    }

    public List<Invoice> getInvoicesWhereTotalPriceGreaterThan(BigDecimal totalPrice) {
        return INVOICE_REPOSITORY.getInvoicesWhereTotalPriceGreaterThan(totalPrice);
    }

    public int getInvoicesCount() {
        return INVOICE_REPOSITORY.getInvoicesCount();
    }

    public void updateTime(String id) {
        INVOICE_REPOSITORY.updateTime(id);
    }

    public Map<String, BigDecimal> groupByTotalPrice() {
        return INVOICE_REPOSITORY.groupByTotalPrice();
    }
}

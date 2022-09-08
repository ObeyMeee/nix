package ua.com.andromeda.utils.mapper;

import com.google.gson.*;
import org.bson.Document;
import org.bson.types.Decimal128;
import ua.com.andromeda.config.ApplicationContext;
import ua.com.andromeda.model.Invoice;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.model.cars.SportCar;
import ua.com.andromeda.model.cars.Truck;
import ua.com.andromeda.model.cars.Vehicle;
import ua.com.andromeda.repository.CrudRepository;
import ua.com.andromeda.repository.mongodb.AutoRepositoryMongoDbImpl;
import ua.com.andromeda.repository.mongodb.SportCarRepositoryMongoDbImpl;
import ua.com.andromeda.repository.mongodb.TruckRepositoryMongoDbImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class InvoiceMapper {
    private static final Gson GSON;
    private static final ApplicationContext CONTEXT = ApplicationContext.getInstance();
    private static final AutoRepositoryMongoDbImpl AUTO_REPOSITORY = CONTEXT.get(AutoRepositoryMongoDbImpl.class);
    private static final SportCarRepositoryMongoDbImpl SPORT_CAR_REPOSITORY = CONTEXT.get(SportCarRepositoryMongoDbImpl.class);
    private static final TruckRepositoryMongoDbImpl TRUCK_REPOSITORY = CONTEXT.get(TruckRepositoryMongoDbImpl.class);

    static {
        JsonSerializer<LocalDateTime> serializer = (localDateTime, typeOfSrc, context) ->
                new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        JsonDeserializer<LocalDateTime> deserializer = (json, typeOfT, context) ->
                LocalDateTime.parse(json.getAsString());

        GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, serializer)
                .registerTypeAdapter(LocalDateTime.class, deserializer)
                .create();
    }

    private InvoiceMapper() {

    }

    public static Document mapInvoiceToDocument(Invoice invoice) {
        String json = GSON.toJson(invoice);
        Document document = Document.parse(json);
        Set<Vehicle> vehicles = invoice.getVehicles();
        saveVehicles(vehicles);
        List<String> vehiclesIds = getVehiclesIds(vehicles);
        BigDecimal totalPrice = getTotalPrice(vehicles);
        document.append("vehicles", vehiclesIds);
        document.append("totalPrice", totalPrice);
        return document;
    }

    private static void saveVehicles(Set<Vehicle> vehicles) {
        vehicles.forEach(vehicle -> {
            String simpleClassName = vehicle.getClass().getSimpleName();
            if (simpleClassName.equals("Auto")) {
                Auto auto = (Auto) vehicle;
                AUTO_REPOSITORY.save(auto);
            } else if (simpleClassName.equals("SportCar")) {
                SportCar sportCar = (SportCar) vehicle;
                SPORT_CAR_REPOSITORY.save(sportCar);
            } else {
                Truck truck = (Truck) vehicle;
                TRUCK_REPOSITORY.save(truck);
            }
        });
    }

    private static List<String> getVehiclesIds(Set<Vehicle> vehicles) {
        return vehicles.stream()
                .map(Vehicle::getId)
                .toList();
    }

    private static BigDecimal getTotalPrice(Set<Vehicle> vehicles) {
        return vehicles.stream()
                .map(vehicle -> (Auto) vehicle)
                .map(auto -> {
                    BigDecimal autoPrice = auto.getPrice();
                    BigDecimal count = BigDecimal.valueOf(auto.getCount());
                    return autoPrice.multiply(count);
                }).reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public static Invoice mapDocumentToInvoice(Document document) {
        Invoice invoice = new Invoice();
        invoice.setId(document.getString("_id"));
        invoice.setCreated(LocalDateTime.parse(document.getString("created")));
        BigDecimal totalPrice = document.get("totalPrice", Decimal128.class).bigDecimalValue();
        invoice.setTotalPrice(totalPrice);
        Set<Vehicle> vehicles = getAllVehicles(document);
        invoice.setVehicles(vehicles);
        return invoice;
    }

    private static Set<Vehicle> getAllVehicles(Document document) {
        Set<Vehicle> vehicles = new HashSet<>();
        List<String> vehiclesIds = document.get("vehicles", ArrayList.class);

        List<Auto> autos = getVehiclesByInvoiceId(vehiclesIds, AUTO_REPOSITORY);
        List<SportCar> sportCars = getVehiclesByInvoiceId(vehiclesIds, SPORT_CAR_REPOSITORY);
        List<Truck> trucks = getVehiclesByInvoiceId(vehiclesIds, TRUCK_REPOSITORY);
        vehicles.addAll(autos);
        vehicles.addAll(sportCars);
        vehicles.addAll(trucks);
        return vehicles;
    }

    private static <T extends Vehicle> List<T> getVehiclesByInvoiceId(List<String> vehiclesIds, CrudRepository<T> crudRepository) {
        return vehiclesIds.stream()
                .map(crudRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}

package ua.com.andromeda.repository.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.config.MongoDbConfig;
import ua.com.andromeda.model.cars.Truck;
import ua.com.andromeda.repository.CrudRepository;
import ua.com.andromeda.utils.mapper.TruckMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

@Singleton
public class TruckRepositoryMongoDbImpl implements CrudRepository<Truck> {

    private static final String ID = "_id";
    private final MongoCollection<Document> trucks;

    @Autowired
    public TruckRepositoryMongoDbImpl(MongoDbConfig mongoDbConfig) {
        MongoDatabase database = mongoDbConfig.getMongoDatabase("test_mongodb");
        database.drop();
        trucks = database.getCollection("trucks");
    }

    @Override
    public Optional<Truck> findById(String id) {
        Document document = trucks.find(eq(ID, id)).first();
        if (document == null) {
            return Optional.empty();
        }
        Truck truck = TruckMapper.mapDocumentToTruck(document);
        return Optional.of(truck);
    }

    @Override
    public List<Truck> getAll() {
        return trucks.find()
                .map(TruckMapper::mapDocumentToTruck)
                .into(new ArrayList<>());
    }

    @Override
    public boolean save(Truck vehicle) {
        Document document = TruckMapper.mapTruckToDocument(vehicle);
        trucks.insertOne(document);
        return true;
    }

    @Override
    public void saveAll(List<Truck> vehicles) {
        List<Document> documents = vehicles.stream()
                .map(TruckMapper::mapTruckToDocument)
                .toList();

        trucks.insertMany(documents);
    }

    @Override
    public void update(Truck vehicle) {
        Document document = new Document();
        Document update = TruckMapper.mapTruckToDocument(vehicle);
        document.append("$set", update);
        trucks.updateOne(eq(ID, vehicle.getId()), document);
    }

    @Override
    public boolean delete(String id) {
        trucks.deleteOne(eq(ID, id));
        return true;
    }


    public List<Truck> getVehiclesByInvoiceId(String invoiceId) {
        Document filter = new Document();
        filter.append("invoiceId", invoiceId);
        return trucks.find(filter)
                .into(new ArrayList<>())
                .stream()
                .map(TruckMapper::mapDocumentToTruck)
                .toList();
    }
}

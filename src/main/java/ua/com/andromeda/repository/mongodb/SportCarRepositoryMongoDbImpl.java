package ua.com.andromeda.repository.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.config.MongoDbConfig;
import ua.com.andromeda.model.cars.SportCar;
import ua.com.andromeda.repository.CrudRepository;
import ua.com.andromeda.utils.mapper.SportCarMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

@Singleton
public class SportCarRepositoryMongoDbImpl implements CrudRepository<SportCar> {

    private final MongoCollection<Document> sportCars;

    @Autowired
    public SportCarRepositoryMongoDbImpl(MongoDbConfig mongoDbConfig) {
        MongoDatabase database = mongoDbConfig.getMongoDatabase("test_mongodb");
        database.drop();
        sportCars = database.getCollection("sportCars");
    }

    @Override
    public Optional<SportCar> findById(String id) {
        Document document = sportCars.find(eq("_id", id)).first();
        if (document == null) {
            return Optional.empty();
        }
        SportCar sportCar = SportCarMapper.mapDocumentToSportCar(document);
        return Optional.of(sportCar);
    }

    @Override
    public List<SportCar> getAll() {
        return sportCars.find()
                .map(SportCarMapper::mapDocumentToSportCar)
                .into(new ArrayList<>());
    }

    @Override
    public boolean save(SportCar vehicle) {
        Document document = SportCarMapper.mapSportCarToDocument(vehicle);
        sportCars.insertOne(document);
        return true;
    }

    @Override
    public void saveAll(List<SportCar> vehicles) {
        List<Document> documents = vehicles.stream()
                .map(SportCarMapper::mapSportCarToDocument)
                .toList();

        sportCars.insertMany(documents);
    }

    @Override
    public void update(SportCar vehicle) {
        Document document = new Document();
        Document update = SportCarMapper.mapSportCarToDocument(vehicle);
        document.append("$set", update);
        sportCars.updateOne(eq("_id", vehicle.getId()), document);
    }

    @Override
    public boolean delete(String id) {
        sportCars.deleteOne(eq("_id", id));
        return true;
    }


    public List<SportCar> getVehiclesByInvoiceId(String invoiceId) {
        Document filter = new Document();
        filter.append("invoiceId", invoiceId);
        return sportCars.find(filter)
                .into(new ArrayList<>())
                .stream()
                .map(SportCarMapper::mapDocumentToSportCar)
                .toList();
    }
}

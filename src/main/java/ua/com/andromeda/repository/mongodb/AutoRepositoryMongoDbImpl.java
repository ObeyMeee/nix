package ua.com.andromeda.repository.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.config.ApplicationContext;
import ua.com.andromeda.config.MongoDbConfig;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.repository.CrudRepository;
import ua.com.andromeda.utils.mapper.AutoMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class AutoRepositoryMongoDbImpl implements CrudRepository<Auto> {
    private static final ApplicationContext CONTEXT = ApplicationContext.getInstance();
    private final MongoCollection<Document> autos;
    private static final String COLLECTION_NAME = "autos";

    @Autowired
    public AutoRepositoryMongoDbImpl(MongoDbConfig mongoDbConfig) {
        MongoDatabase database = mongoDbConfig.getMongoDatabase("test_mongodb");
        database.drop();
        autos = database.getCollection(COLLECTION_NAME);
    }

    @Override
    public Optional<Auto> findById(String id) {
        Document filter = getDocumentById(id);
        FindIterable<Document> documents = autos.find(filter);
        Document document = documents.first();
        if (document == null) {
            return Optional.empty();
        }
        Auto auto = AutoMapper.mapDocumentToAuto(document);
        return Optional.of(auto);
    }

    private Document getDocumentById(String id) {
        Document filter = new Document();
        filter.append("_id", id);
        return filter;
    }

    @Override
    public List<Auto> getAll() {
        return autos.find()
                .map(AutoMapper::mapDocumentToAuto)
                .into(new ArrayList<>());
    }

    @Override
    public boolean save(Auto vehicle) {
        Document document = AutoMapper.mapAutoToDocument(vehicle);
        EngineRepositoryMongoDbImpl engineRepository = CONTEXT.get(EngineRepositoryMongoDbImpl.class);
        engineRepository.save(vehicle.getEngine());
        DetailRepositoryMongoDbImpl detailRepository = CONTEXT.get(DetailRepositoryMongoDbImpl.class);
        detailRepository.save(vehicle.getDetails());
        autos.insertOne(document);
        return true;
    }

    @Override
    public void saveAll(List<Auto> vehicles) {
        List<Document> documents = new ArrayList<>();

        for (Auto auto : vehicles) {
            EngineRepositoryMongoDbImpl engineRepository = CONTEXT.get(EngineRepositoryMongoDbImpl.class);
            engineRepository.save(auto.getEngine());
            DetailRepositoryMongoDbImpl detailRepository = CONTEXT.get(DetailRepositoryMongoDbImpl.class);
            detailRepository.save(auto.getDetails());
            Document document = AutoMapper.mapAutoToDocument(auto);
            documents.add(document);
        }
        autos.insertMany(documents);
    }

    @Override
    public void update(Auto vehicle) {
        EngineRepositoryMongoDbImpl engineRepository = CONTEXT.get(EngineRepositoryMongoDbImpl.class);
        DetailRepositoryMongoDbImpl detailRepository = CONTEXT.get(DetailRepositoryMongoDbImpl.class);
        detailRepository.save(vehicle.getDetails());
        engineRepository.save(vehicle.getEngine());

        Document newData = AutoMapper.mapAutoToDocument(vehicle);
        Document filter = getDocumentById(vehicle.getId());
        Document updatedAuto = new Document();
        updatedAuto.append("$set", newData);
        autos.updateOne(filter, updatedAuto);
    }

    @Override
    public boolean delete(String id) {
        Document filter = getDocumentById(id);
        autos.deleteOne(filter);
        return true;
    }

    public List<Auto> getVehiclesByInvoiceId(String invoiceId) {
        Document filter = new Document();
        filter.append("invoiceId", invoiceId);
        return autos.find(filter)
                .into(new ArrayList<>())
                .stream()
                .map(AutoMapper::mapDocumentToAuto)
                .toList();
    }
}

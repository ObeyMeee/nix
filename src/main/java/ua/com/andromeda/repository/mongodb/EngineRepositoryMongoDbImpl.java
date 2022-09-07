package ua.com.andromeda.repository.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.config.MongoDbConfig;
import ua.com.andromeda.model.Engine;
import ua.com.andromeda.utils.mapper.EngineMapper;

import java.util.Optional;

@Singleton
public class EngineRepositoryMongoDbImpl {
    private final MongoDatabase database;
    private static final String COLLECTION_NAME = "engines";

    @Autowired
    public EngineRepositoryMongoDbImpl(MongoDbConfig mongoDbConfig) {
        database = mongoDbConfig.getMongoDatabase("test_mongodb");
    }

    public void save(Engine engine) {
        MongoCollection<Document> engines = database.getCollection(COLLECTION_NAME);
        Document document = EngineMapper.mapEngineToDocument(engine);
        engines.insertOne(document);
    }

    public Optional<Engine> getById(String id) {
        MongoCollection<Document> engines = database.getCollection(COLLECTION_NAME);
        Document filter = new Document();
        filter.append("id", id);
        FindIterable<Document> documents = engines.find(filter);
        Document document = documents.first();
        assert document != null;
        Engine engine = EngineMapper.mapDocumentToEngine(document);
        return Optional.of(engine);
    }
}

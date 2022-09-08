package ua.com.andromeda.repository.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.config.MongoDbConfig;
import ua.com.andromeda.model.Detail;
import ua.com.andromeda.utils.mapper.DetailMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class DetailRepositoryMongoDbImpl {
    private final MongoDatabase database;
    private static final String COLLECTION_NAME = "details";

    @Autowired
    public DetailRepositoryMongoDbImpl(MongoDbConfig mongoDbConfig) {
        database = mongoDbConfig.getMongoDatabase("test_mongodb");
    }

    public void save(List<Detail> detailsList) {
        MongoCollection<Document> detailsDbCollection = database.getCollection(COLLECTION_NAME);
        List<Document> documents = new ArrayList<>();
        for (Detail detail : detailsList) {
            Document document = DetailMapper.mapDetailToDocument(detail);
            documents.add(document);
        }
        detailsDbCollection.insertMany(documents);
    }

    public Optional<Detail> getById(String id) {
        MongoCollection<Document> details = database.getCollection(COLLECTION_NAME);
        Document filter = new Document();
        filter.append("_id", id);
        FindIterable<Document> documents = details.find(filter);
        Document document = documents.first();
        Detail detail = DetailMapper.mapDocumentToDetail(document);
        return Optional.of(detail);
    }
}

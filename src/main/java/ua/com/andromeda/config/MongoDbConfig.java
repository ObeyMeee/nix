package ua.com.andromeda.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import ua.com.andromeda.annotations.Singleton;

@Singleton
@Getter
public class MongoDbConfig {
    private final MongoClient mongoClient;

    public MongoDbConfig() {
        String dbURI = "mongodb+srv://Andromeda:Andromeda@cluster0.y4tuetf.mongodb.net/?retryWrites=true&w=majority";
        mongoClient = new MongoClient(new MongoClientURI(dbURI));
    }

    public MongoDatabase getMongoDatabase(String databaseName) {
        return mongoClient.getDatabase(databaseName);
    }
}

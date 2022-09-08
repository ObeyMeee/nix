package ua.com.andromeda.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import ua.com.andromeda.annotations.Singleton;

@Singleton
@Getter
public class MongoDbConfig {
    private final MongoClient mongoClient;

    public MongoDbConfig() {
        mongoClient = new MongoClient("localhost", 27017);
    }

    public MongoDatabase getMongoDatabase(String databaseName) {
        return mongoClient.getDatabase(databaseName);
    }
}

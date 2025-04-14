package me.hamza.blaze.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.hamza.blaze.utils.CC;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Hammzar
 * @since 14.04.2025
 */
@Getter
public class MongoHandler {

    private MongoClient mongoClient;
    private MongoDatabase database;

    public MongoHandler(FileConfiguration config) {
        try {
            connect(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connect(FileConfiguration config) {
        String host = config.getString("MONGO.HOST");
        int port = config.getInt("MONGO.PORT");
        String databaseName = config.getString("MONGO.DATABASE");
        boolean authEnabled = config.getBoolean("MONGO.AUTH.ENABLED");

        String uri;

        if (authEnabled) {
            String user = config.getString("MONGO.AUTH.USER");
            String pass = config.getString("MONGO.AUTH.PASS");
            String authDb = config.getString("MONGO.AUTH.AUTH-DB");

            uri = String.format("mongodb://%s:%s@%s:%d/%s", user, pass, host, port, authDb);
        } else {
            uri = String.format("mongodb://%s:%d", host, port);
        }

        mongoClient = MongoClients.create(uri);
        database = mongoClient.getDatabase(databaseName);
    }

}

package us.TestersQTs.GooseBot.database;

import com.mongodb.client.*;
import org.bson.Document;
import us.TestersQTs.GooseBot.Config;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

public class DatabaseManager {

    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    public static void addGuildToDatabase(long guildId, String guildName, String prefix) {
        Document document = new Document("guildId", guildId);
        MongoCollection collection = mongoDatabase.getCollection("guilds");

        document.append("guildName", guildName);
        document.append("guildPrefix", prefix);

        collection.insertOne(document);
    }

    public static void removeGuildFromDatabase(long guildId) {
        MongoCollection collection = mongoDatabase.getCollection("guilds");
        Document document = (Document) collection.find(eq("guildId", guildId)).first();

        collection.deleteOne(document);
    }

    public static void updatePrefix(long guildId, String prefix) {
        MongoCollection collection = mongoDatabase.getCollection("guilds");
        collection.updateOne(eq("guildId", guildId), new Document("$set", new Document("guildPrefix", prefix)));
    }

    public static String getPrefix(long guildId) {
        MongoCollection collection = mongoDatabase.getCollection("guilds");
        Document document = (Document) collection.find(eq("guildId", guildId)).first();

        return (String) document.get("guildPrefix");
    }

    /**
     * The following methods are not changing and are used to open/terminate the established Mongo connection
     *
     * openConnection() should be called first followed by the database method and finally the connection
     * should be terminated by calling the closeConnection() method
     */

    @Deprecated
    public static void closeConnection() {
        mongoClient.close();
    }

    public static void openConnection() {
        mongoClient = MongoClients.create(Config.get("MONGO"));
        mongoDatabase = mongoClient.getDatabase("goose");
    }

}

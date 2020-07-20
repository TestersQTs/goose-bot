package us.TestersQTs.GooseBot.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import us.TestersQTs.GooseBot.Config;

import javax.annotation.Nonnull;

import java.time.Instant;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

public class DatabaseManager {

    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    public static void addGuildToDatabase(long guildId, @Nonnull String guildName, @Nonnull String prefix) {

        /*
          Guild settings get saved first. (guildId, prefix etc)
         */

        Document docGuilds = new Document("guildId", guildId);
        MongoCollection<Document> guilds = mongoDatabase.getCollection("guilds");

        docGuilds.append("guildName", guildName);
        docGuilds.append("guildPrefix", prefix);
        docGuilds.append("joined", Instant.now());

        guilds.insertOne(docGuilds);

        /*
          Event changes get created.
         */

        Document docSettings = new Document("guildId", guildId);
        MongoCollection<Document> settings = mongoDatabase.getCollection("config");

        docSettings.append("EVENT_MESSAGE_SEND", 0.15);
        docSettings.append("EVENT_MESSAGE_REACT", 0.33);
        docSettings.append("EVENT_USER_VC_DISCONNECT", 0.35);
        docSettings.append("EVENT_MESSAGE_DELETE", 0.05);
        docSettings.append("EVENT_ROLE_DELETE", 0.33);


        settings.insertOne(docSettings);
    }

    public static void removeGuildFromDatabase(long guildId) {
        MongoCollection<Document> guilds = mongoDatabase.getCollection("guilds");
        Document docGuilds = guilds.find(eq("guildId", guildId)).first();

        guilds.deleteOne(Objects.requireNonNull(docGuilds));

        MongoCollection<Document> settings = mongoDatabase.getCollection("config");
        Document docSettings = settings.find(eq("guildId", guildId)).first();

        settings.deleteOne(Objects.requireNonNull(docSettings));
    }

    public static void updatePrefix(long guildId, @Nonnull String prefix) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("guilds");
        collection.updateOne(eq("guildId", guildId), new Document("$set", new Document("guildPrefix", prefix)));
    }

    public static String getPrefix(long guildId) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("guilds");
        Document document = collection.find(eq("guildId", guildId)).first();

        return (String) Objects.requireNonNull(document).get("guildPrefix");
    }

    public static double getEventChance(long guildId, @Nonnull String event) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("config");
        Document document = collection.find(eq("guildId", guildId)).first();

        return (double) Objects.requireNonNull(document).get(event);
    }

    public static void setEventChance(long guildId, @Nonnull String event, double chance) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("config");
        collection.updateOne(eq("guildId", guildId), new Document("$set", new Document(event, chance)));
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

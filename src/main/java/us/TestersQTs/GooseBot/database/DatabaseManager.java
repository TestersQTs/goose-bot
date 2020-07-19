package us.TestersQTs.GooseBot.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import us.TestersQTs.GooseBot.Config;

import javax.annotation.Nonnull;

import static com.mongodb.client.model.Filters.eq;

public class DatabaseManager {

    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    public static void addGuildToDatabase(@Nonnull long guildId, @Nonnull String guildName, @Nonnull String prefix) {

        /**
         * Guild settings get saved first. (guildId, prefix etc)
         */

        Document docGuilds = new Document("guildId", guildId);
        MongoCollection guilds = mongoDatabase.getCollection("guilds");

        docGuilds.append("guildName", guildName);
        docGuilds.append("guildPrefix", prefix);

        guilds.insertOne(docGuilds);

        /**
         * Event changes get created.
         */

        Document docSettings = new Document("guildId", guildId);
        MongoCollection settings = mongoDatabase.getCollection("config");

        docSettings.append("EVENT_MESSAGE_SEND", 0.15);
        docSettings.append("EVENT_USER_VC_DISCONNECT", 0.35);
        docSettings.append("EVENT_MESSAGE_DELETE", 0.05);
        docSettings.append("EVENT_ROLE_DELETE", 0.33);

        settings.insertOne(docSettings);
    }

    public static void removeGuildFromDatabase(long guildId) {
        MongoCollection guilds = mongoDatabase.getCollection("guilds");
        Document docGuilds = (Document) guilds.find(eq("guildId", guildId)).first();

        guilds.deleteOne(docGuilds);

        MongoCollection settings = mongoDatabase.getCollection("config");
        Document docSettings = (Document) settings.find(eq("guildId", guildId)).first();

        settings.deleteOne(docSettings);
    }

    public static void updatePrefix(long guildId, @Nonnull String prefix) {
        MongoCollection collection = mongoDatabase.getCollection("guilds");
        collection.updateOne(eq("guildId", guildId), new Document("$set", new Document("guildPrefix", prefix)));
    }

    public static String getPrefix(long guildId) {
        MongoCollection collection = mongoDatabase.getCollection("guilds");
        Document document = (Document) collection.find(eq("guildId", guildId)).first();

        return (String) document.get("guildPrefix");
    }

    public static double getEventChance(long guildId, @Nonnull String event) {
        MongoCollection collection = mongoDatabase.getCollection("config");
        Document document = (Document) collection.find(eq("guildId", guildId)).first();

        return (double) document.get(event);
    }

    public static void setEventChance(long guildId, @Nonnull String event, double chance) {
        MongoCollection collection = mongoDatabase.getCollection("config");
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

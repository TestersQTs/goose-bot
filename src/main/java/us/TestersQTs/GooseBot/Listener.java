package us.TestersQTs.GooseBot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.TestersQTs.GooseBot.command.CommandManager;
import us.TestersQTs.GooseBot.database.DatabaseManager;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

public class Listener extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private final CommandManager manager = new CommandManager();

    JsonObject phrases;
    JsonObject emotes;

    {
        try {
            phrases = new JsonParser().parse(new FileReader("goosePhrases.json")).getAsJsonObject();
            emotes = new JsonParser().parse(new FileReader("gooseEmotes.json")).getAsJsonObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        User user = event.getAuthor();

        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }

        String prefix = DatabaseManager.getPrefix(event.getGuild().getIdLong());
        String content = event.getMessage().getContentRaw();

        if (content.startsWith(prefix)) {
            manager.handle(event);
            return;
        }

        double random = new Random().nextDouble();


        if (random <= DatabaseManager.getEventChance(event.getGuild().getIdLong(), "EVENT_MESSAGE_DELETE")) {

            JsonArray phraseArray = phrases.get("messageEvent").getAsJsonObject().get("eventDelete").getAsJsonArray();

            event.getMessage().delete().queue();
            event.getChannel().sendMessage(phraseArray.get(new Random().nextInt(phraseArray.size())).toString().replaceAll("(\")", "")).queue();
            return;
        }

        if (random <= DatabaseManager.getEventChance(event.getGuild().getIdLong(), "EVENT_MESSAGE_SEND")) {

            JsonArray phraseArray = phrases.get("messageEvent").getAsJsonObject().get("eventMessage").getAsJsonArray();

            event.getChannel().sendMessage(phraseArray.get(new Random().nextInt(phraseArray.size())).toString().replaceAll("(\")", "")).queue();
        }

        if (random <= DatabaseManager.getEventChance(event.getGuild().getIdLong(), "EVENT_MESSAGE_REACT")) {

            JsonArray phraseArray = emotes.get("emotes").getAsJsonObject().get("eventReaction").getAsJsonArray();

            event.getMessage().addReaction(phraseArray.get(new Random().nextInt(phraseArray.size())).toString().replaceAll("(\")", "")).queue();

        }

    }

    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        double random = new Random().nextDouble();

        if (random <= DatabaseManager.getEventChance(event.getGuild().getIdLong(), "EVENT_USER_VC_DISCONNECT")) {
            event.getGuild().kickVoiceMember(event.getMember()).queue();
        }
    }

    @Override
    public void onRoleCreate(@Nonnull RoleCreateEvent event) {
        Role role = event.getRole();

        double random = new Random().nextDouble();

        if (random <= DatabaseManager.getEventChance(event.getGuild().getIdLong(), "EVENT_ROLE_DELETE")) {
            role.delete().queue();
        }
    }

    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {
        LOGGER.info("Joined a new guild! {}", event.getGuild().getId());
        DatabaseManager.addGuildToDatabase(event.getGuild().getIdLong(), event.getGuild().getName(), Config.get("PREFIX"));
    }

    @Override
    public void onGuildLeave(@Nonnull GuildLeaveEvent event) {
        LOGGER.info("Left a guild! {}", event.getGuild().getId());
        DatabaseManager.removeGuildFromDatabase(event.getGuild().getIdLong());
    }
}

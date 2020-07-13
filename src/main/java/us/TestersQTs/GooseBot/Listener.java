package us.TestersQTs.GooseBot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.TestersQTs.GooseBot.command.CommandManager;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

public class Listener extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private final CommandManager manager = new CommandManager();

    JsonObject phrases;

    {
        try {
            phrases = new JsonParser().parse(new FileReader("goosePhrases.json")).getAsJsonObject();
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

        String prefix = Config.get("PREFIX");
        String content = event.getMessage().getContentRaw();

        if (content.startsWith(prefix)) {
            manager.handle(event);
            return;
        }

        double random = new Random().nextDouble();
        int phrase = new Random().nextInt(16);

        if (random <= 0.15) {
            event.getMessage().delete().queue();
            event.getChannel().sendMessage(String.valueOf(phrases.get("messageEvent").getAsJsonObject().get("eventDelete")
                    .getAsJsonPrimitive()).replaceAll("(\")", "")).queue();
            return;
        }


        if (random <= 0.35) {
            event.getChannel().sendMessage(String.valueOf(phrases.get("messageEvent").getAsJsonObject().get("eventMessage")
                    .getAsJsonArray().get(phrase)).replaceAll("(\")", "")).queue();
        }
    }

    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        double random = new Random().nextDouble();

        if (random <= 0.35) {
            event.getGuild().kickVoiceMember(event.getMember()).queue();
            return;
        }
    }
}

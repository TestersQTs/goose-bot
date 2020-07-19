package us.TestersQTs.GooseBot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import us.TestersQTs.GooseBot.database.DatabaseManager;

import javax.security.auth.login.LoginException;

public class Bot {

    @SuppressWarnings("deprecation")
    private Bot() throws LoginException {
        DatabaseManager.openConnection();

        new JDABuilder()
                .setToken(Config.get("TOKEN"))
                .addEventListeners(new Listener())
                .build()
                .getPresence().setActivity(Activity.watching("honk honk"));
    }

    public static void main(String[] args) throws LoginException {
        new Bot();
    }
}
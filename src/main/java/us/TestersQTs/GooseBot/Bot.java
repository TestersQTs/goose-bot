package us.TestersQTs.GooseBot;

import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Bot {

    @SuppressWarnings("deprecation")
    private Bot() throws LoginException {
        new JDABuilder()
                .setToken(Config.get("TOKEN"))
                .addEventListeners(new Listener())
                .build();
    }

    public static void main(String args[]) throws LoginException {
        new Bot();
    }
}
package us.TestersQTs.GooseBot.command.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import us.TestersQTs.GooseBot.command.CommandContext;
import us.TestersQTs.GooseBot.command.ICommand;
import us.TestersQTs.GooseBot.database.DatabaseManager;

import java.awt.*;
import java.time.Instant;

public class InfoCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.RED);
        embedBuilder.setAuthor(context.getAuthor().getAsTag(), null, context.getAuthor().getAvatarUrl());
        embedBuilder.setDescription("**" + context.getGuild().getName() + " Settings**");
        embedBuilder.addField("MESSAGE_SEND", String.valueOf(DatabaseManager.getEventChance(context.getGuild().getIdLong(), "EVENT_MESSAGE_SEND")), true);
        embedBuilder.addField("MESSAGE_REACT", String.valueOf(DatabaseManager.getEventChance(context.getGuild().getIdLong(), "EVENT_MESSAGE_REACT")), true);
        embedBuilder.addField("USER_VC_DISCONNECT", String.valueOf(DatabaseManager.getEventChance(context.getGuild().getIdLong(), "EVENT_USER_VC_DISCONNECT")), true);
        embedBuilder.addField("MESSAGE_DELETE", String.valueOf(DatabaseManager.getEventChance(context.getGuild().getIdLong(), "EVENT_MESSAGE_DELETE")), true);
        embedBuilder.addField("ROLE_DELETE", String.valueOf(DatabaseManager.getEventChance(context.getGuild().getIdLong(), "EVENT_ROLE_DELETE")), true);
        embedBuilder.addField("USER_RENAME", String.valueOf(DatabaseManager.getEventChance(context.getGuild().getIdLong(), "EVENT_USER_RENAME")), true);
        embedBuilder.setFooter("Requested by: " + context.getAuthor().getAsTag());
        embedBuilder.setTimestamp(Instant.now());

        context.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "info";
    }
}

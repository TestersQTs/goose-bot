package us.TestersQTs.GooseBot.command.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import us.TestersQTs.GooseBot.command.CommandContext;
import us.TestersQTs.GooseBot.command.ICommand;
import us.TestersQTs.GooseBot.database.DatabaseManager;

import java.awt.*;
import java.time.Instant;

public class HelpCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(new Color(0x7289DA));
        embedBuilder.setAuthor(context.getAuthor().getAsTag(), null, context.getAuthor().getAvatarUrl());
        embedBuilder.setDescription("**Help | WIP**");
        embedBuilder.addField("!prefix", "Usage !prefix [str:new prefix]", false);
        embedBuilder.addField("!configure", "Usage !configure [str:action] [str:event] [double:new value]", false);

        embedBuilder.setFooter("Requested by: " + context.getAuthor().getAsTag());
        embedBuilder.setTimestamp(Instant.now());

        context.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }
}

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
        embedBuilder.addField("MESSAGE_SEND", "0.20 (20%)", true);
        embedBuilder.addField("USER_VC_DISCONNECT", "0.35 (35%)", true);
        embedBuilder.addField("MESSAGE_DELETE", "0.05 (5%)", true);
        embedBuilder.addField("ROLE_RENAME", "0.5 (50%)", true);
        embedBuilder.addField("ROLE_DELETE", "0.33 (33%)", true);
        embedBuilder.setFooter("Requested by: " + context.getAuthor().getAsTag());
        embedBuilder.setTimestamp(Instant.now());

        context.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "info";
    }
}

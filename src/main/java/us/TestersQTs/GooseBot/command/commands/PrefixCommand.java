package us.TestersQTs.GooseBot.command.commands;

import us.TestersQTs.GooseBot.command.CommandContext;
import us.TestersQTs.GooseBot.command.ICommand;
import us.TestersQTs.GooseBot.database.DatabaseManager;

public class PrefixCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {
        String prefix = context.getArgs().get(0);

        if (context.getArgs().size() < 1) {
            context.getChannel().sendMessageFormat("Too many arguments! Expected `%s` got `%s`", "1", context.getArgs().size()).queue();
            return;
        }

        context.getChannel().sendMessage("Updating prefix. Please wait...").queue();

        DatabaseManager.updatePrefix(context.getGuild().getIdLong(), prefix);

        context.getChannel().sendMessageFormat("Prefix updated to `%s`", prefix).queue();
    }

    @Override
    public String getName() {
        return "prefix";
    }
}

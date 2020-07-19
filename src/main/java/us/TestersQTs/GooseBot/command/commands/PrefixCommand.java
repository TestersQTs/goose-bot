package us.TestersQTs.GooseBot.command.commands;

import net.dv8tion.jda.api.Permission;
import us.TestersQTs.GooseBot.command.CommandContext;
import us.TestersQTs.GooseBot.command.ICommand;
import us.TestersQTs.GooseBot.database.DatabaseManager;

public class PrefixCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {

        if (!context.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            context.getChannel().sendMessage("You do not have the correct permission to use this command!").queue();
            return;
        }

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

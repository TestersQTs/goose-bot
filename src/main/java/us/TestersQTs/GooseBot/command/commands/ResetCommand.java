package us.TestersQTs.GooseBot.command.commands;

import net.dv8tion.jda.api.Permission;
import us.TestersQTs.GooseBot.command.CommandContext;
import us.TestersQTs.GooseBot.command.ICommand;

import static us.TestersQTs.GooseBot.database.DatabaseManager.addGuildToDatabase;
import static us.TestersQTs.GooseBot.database.DatabaseManager.removeGuildFromDatabase;

public class ResetCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {
        if (!context.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            context.getChannel().sendMessage("You do not have the correct permission to use this command!").queue();
            return;
        }

        removeGuildFromDatabase(context.getGuild().getIdLong());
        addGuildToDatabase(context.getGuild().getIdLong(), context.getGuild().getName(), "!");
        context.getChannel().sendMessageFormat("All configurations have been reset now.").queue();

    }

    @Override
    public String getName() {
        return "reset";
    }
}

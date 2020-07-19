package us.TestersQTs.GooseBot.command.commands;

import net.dv8tion.jda.api.Permission;
import us.TestersQTs.GooseBot.command.CommandContext;
import us.TestersQTs.GooseBot.command.ICommand;
import us.TestersQTs.GooseBot.database.DatabaseManager;

public class ConfigureCommand implements ICommand {

    @Override
    public void handle(CommandContext context) {

        if (!context.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            context.getChannel().sendMessage("You do not have the correct permission to use this command!").queue();
            return;
        }

        if (context.getArgs().size() < 1) {
            context.getChannel().sendMessageFormat("Too little arguments! Expected `1` got `%s`", context.getArgs().size()).queue();
            return;
        }

        if ("events".equals(context.getArgs().get(0))) {
            if (context.getArgs().size() < 3) {
                context.getChannel().sendMessageFormat("Too little arguments! Expected `3` got `%s`", context.getArgs().size()).queue();
                return;
            }

            double chance;

            try{
                chance = Double.parseDouble(context.getArgs().get(2));

            }catch (NumberFormatException e){
                context.getChannel().sendMessageFormat("Chance must be double! At most 1.0").queue();
                return;
            }

            switch (context.getArgs().get(1)) {
                case "EVENT_MESSAGE_SEND":
                    DatabaseManager.setEventChance(context.getGuild().getIdLong(), "EVENT_MESSAGE_SEND", chance);
                    context.getChannel().sendMessageFormat("Chance for event `EVENT_MESSAGE_SEND` updated to `%s`", chance).queue();
                    break;
                case "EVENT_USER_VC_DISCONNECT":
                    DatabaseManager.setEventChance(context.getGuild().getIdLong(), "EVENT_USER_VC_DISCONNECT", chance);
                    context.getChannel().sendMessageFormat("Chance for event `EVENT_MESSAGE_SEND` updated to `%s`", chance).queue();
                    break;
                case "EVENT_MESSAGE_DELETE":
                    DatabaseManager.setEventChance(context.getGuild().getIdLong(), "EVENT_MESSAGE_DELETE", chance);
                    context.getChannel().sendMessageFormat("Chance for event `EVENT_MESSAGE_SEND` updated to `%s`", chance).queue();
                    break;
                case "EVENT_ROLE_DELETE":
                    DatabaseManager.setEventChance(context.getGuild().getIdLong(), "EVENT_ROLE_DELETE", chance);
                    context.getChannel().sendMessageFormat("Chance for event `EVENT_MESSAGE_SEND` updated to `%s`", chance).queue();
                    break;
                case "EVENT_MESSAGE_REACT":
                    DatabaseManager.setEventChance(context.getGuild().getIdLong(), "EVENT_MESSAGE_REACT", chance);
                    context.getChannel().sendMessageFormat("Chance for event `EVENT_MESSAGE_REACT` updated to `%s`", chance).queue();
                    break;
                default:
                    context.getChannel().sendMessageFormat("Unknown event! `%s`", context.getArgs().get(1)).queue();
                    break;
            }
        } else {
            context.getChannel().sendMessageFormat("Cannot convert `%s` to `action`!", context.getArgs().get(0)).queue();
        }
    }

    @Override
    public String getName() {
        return "configure";
    }
}

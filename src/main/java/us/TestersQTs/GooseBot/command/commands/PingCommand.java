package us.TestersQTs.GooseBot.command.commands;

import net.dv8tion.jda.api.JDA;
import us.TestersQTs.GooseBot.command.CommandContext;
import us.TestersQTs.GooseBot.command.ICommand;

public class PingCommand implements ICommand {
    @Override
    public void handle(CommandContext context) {
        JDA jda = context.getJDA();

        jda.getRestPing().queue((ping) -> {
            context.getChannel()
                    .sendMessageFormat("\uD83C\uDFD3 **PONG** \uD83C\uDFD3 h\n**Rest ping:** %sms \n**WebSocket ping:** %sms", ping, jda.getGatewayPing()).queue();
        });
    }

    @Override
    public String getName() {
        return "ping";
    }
}

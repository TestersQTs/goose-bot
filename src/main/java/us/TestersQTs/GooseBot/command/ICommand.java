package us.TestersQTs.GooseBot.command;

import java.util.Arrays;
import java.util.List;

public interface ICommand {

    void handle(CommandContext context);

    String getName();

    default List<String> getAliases() {
        return Arrays.asList();
    }
}

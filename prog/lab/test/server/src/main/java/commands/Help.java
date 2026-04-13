package commands;

import etc.DataClass;
import network.Response;
import core.CommandRegister;

import java.util.Map;
import java.util.stream.Collectors;

public class Help extends BaseCommand {
    private final CommandRegister commandRegister;

    public Help(CommandRegister commandRegister) {
        super("help", "help", "вывести справку по доступным командам", DataClass.NONE);
        this.commandRegister = commandRegister;
    }


    @Override
    public Response execute(Object arguments) {
        String response = commandRegister.getCommands().entrySet().stream()
                .map(entry -> entry.getValue().getInfoName() + ": " + entry.getValue().getDescription())
                .collect(Collectors.joining("\n"));

        return new Response(true, response.isEmpty() ? "Нет доступных команд" : response);
    }
}

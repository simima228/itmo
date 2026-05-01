package commands;

import network.Response;
import core.CommandRegister;

import java.util.stream.Collectors;

public class Help extends BaseCommand {
    private final CommandRegister commandRegister;

    public Help(CommandRegister commandRegister) {
        super("help", "help", "вывести справку по доступным командам");
        this.commandRegister = commandRegister;
    }


    @Override
    public Response execute(Object arguments, String login, String password) {
        String response = commandRegister.getCommands().values().stream()
                .map(baseCommand -> baseCommand.getInfoName() + ": " + baseCommand.getDescription())
                .collect(Collectors.joining("\n"));

        return new Response(true, response.isEmpty() ? "Нет доступных команд" : response);
    }
}

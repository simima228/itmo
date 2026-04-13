package core;

import network.Request;
import network.Response;
import commands.BaseCommand;

import java.util.LinkedHashMap;
import java.util.Map;


public class CommandRegister {
    private final Map<String, BaseCommand> commands = new LinkedHashMap<>();
    private final HistoryRegister historyRegister;

    public CommandRegister(HistoryRegister historyRegister) {
        this.historyRegister = historyRegister;
    }

    public void register(BaseCommand command) {
        commands.put(command.getName(), command);
    }

    public Map<String, BaseCommand> getCommands() {
        return commands;
    }

    public Response executor(Request request) {
        BaseCommand command = commands.get(request.commandName());
        if (command == null) {
            return new Response(false, "Неизвестная команда");
        }
        Response response =  command.execute(request.arguments());
        if (response.success()){
            historyRegister.addHistory(command.getName());
        }
        return response;
    }
}

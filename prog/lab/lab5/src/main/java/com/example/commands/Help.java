package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.registers.CommandRegister;

import java.util.LinkedHashMap;
import java.util.Map;

public class Help extends BaseCommand {
    private final Console console;
    private final CommandRegister commandRegister;

    public Help(Console console, CommandRegister commandRegister) {
        super("help", "help", "вывести справку по доступным командам");
        this.console = console;
        this.commandRegister = commandRegister;
    }

    public CommandStatus execute() {
        for (Map.Entry<String, BaseCommand> entry : commandRegister.getCommands().entrySet()) {
            console.println(entry.getValue().getInfoName() + ": " + entry.getValue().getDescription());
        }
        return new CommandStatus(true, "Команда выполнена успешно!");
    }
}

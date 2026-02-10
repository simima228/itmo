package com.example.registers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.example.commands.BaseCommand;

public class CommandRegister {
    private Map<String, BaseCommand> commands = new HashMap<>();
    public void register(BaseCommand command) {
        commands.put(command.getName(), command);
    }

    public Map<String, BaseCommand> getCommands() {
        return commands;
    }
}

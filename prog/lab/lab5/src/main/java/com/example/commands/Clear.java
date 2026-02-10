package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.registers.CollectionRegister;

import java.util.Map;

public class Clear extends BaseCommand {
    private Console console;
    private CollectionRegister collectionRegister;

    public Clear(Console console, CollectionRegister collectionRegister) {
        super("clear", "clear", "очистить коллекцию");
        this.console = console;
        this.collectionRegister = collectionRegister;
    }

    public CommandStatus execute(String[] args) {
        if (!args[1].trim().isEmpty()) {
            return wrongUsage();
        }
        collectionRegister.clear();
        return super.execute(args);
    }
}
package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.registers.CollectionRegister;


public class Clear extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public Clear(CollectionRegister collectionRegister) {
        super("clear", "clear", "очистить коллекцию");
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
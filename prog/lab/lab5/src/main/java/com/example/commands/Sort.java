package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.registers.CollectionRegister;

public class Sort extends BaseCommand {
    private Console console;
    private CollectionRegister collectionRegister;

    public Sort(Console console, CollectionRegister collectionRegister) {
        super("sort", "sort", "отсортировать коллекцию в естественном порядке");
        this.console = console;
        this.collectionRegister = collectionRegister;
    }

    public CommandStatus execute(String[] args) {
        if (!args[1].trim().isEmpty()) {
            return wrongUsage();
        }
        collectionRegister.sort();
        return super.execute(args);
    }
}
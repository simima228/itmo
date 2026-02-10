package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.registers.CollectionRegister;
import com.example.registers.ObjectRegister;

public class Add extends BaseCommand{
    private Console console;
    private CollectionRegister collectionRegister;
    private ObjectRegister objectRegister;

    public Add(Console console, CollectionRegister collectionRegister, ObjectRegister objectRegister) {
        super("add", "add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionRegister = collectionRegister;
        this.objectRegister = objectRegister;
    }

    public CommandStatus execute(String[] args) {
        if (!args[1].trim().isEmpty()) {
            return wrongUsage();
        }
        try {
            collectionRegister.push(objectRegister.createMovie(console, collectionRegister.getNewId()));
            return super.execute(args);
        }
        catch (ObjectRegister.Break e) {
            return new CommandStatus(false, e.getMessage());
        }
    }
}

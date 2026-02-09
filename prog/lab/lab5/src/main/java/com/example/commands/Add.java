package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.registers.CollectionRegister;

public class Add extends BaseCommand{
    private final Console console;
    private final CollectionRegister collectionRegister;

    public Add(Console console, CollectionRegister collectionRegister) {
        super("add", "add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionRegister = collectionRegister;
    }

    public CommandStatus execute(Object obj) {
        collectionRegister.push(obj);
        return new CommandStatus(true, "Команда вып1олнена успешно!");
    }
}

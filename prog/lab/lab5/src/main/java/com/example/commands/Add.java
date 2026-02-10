package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.models.Movie;
import com.example.registers.CollectionRegister;
import com.example.registers.ObjectRegister;

public class Add extends BaseCommand{
    private final Console console;
    private final CollectionRegister collectionRegister;
    private final ObjectRegister objectRegister;

    public Add(Console console, CollectionRegister collectionRegister, ObjectRegister objectRegister) {
        super("add", "add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionRegister = collectionRegister;
        this.objectRegister = objectRegister;
    }

    public CommandStatus execute(String[] args) {
        if (!args[1].isEmpty()) {
            return new CommandStatus(false, "Некорректная команда");
        }
        collectionRegister.push(objectRegister.createMovie(console, collectionRegister.getNewId()));
        return new CommandStatus(true, "Команда выполнена успешно!");
    }
}

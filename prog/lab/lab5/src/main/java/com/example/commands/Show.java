package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.registers.CollectionRegister;

public class Show extends BaseCommand {
    private Console console;
    private CollectionRegister collectionRegister;

    public Show(Console console, CollectionRegister collectionRegister) {
        super("show", "show", "вывести в стандартный" +
                " поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionRegister = collectionRegister;

    }

    public CommandStatus execute(String[] args) {
        if (!args[1].trim().isEmpty()) {
            return wrongUsage();
        }
        console.println("Элементы коллекции:");
        console.println(collectionRegister.getInformation(collectionRegister.getStack()));
        return super.execute(args);
    }
}
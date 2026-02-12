package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.registers.CollectionRegister;

public class PrintDescending extends BaseCommand {
    private final Console console;
    private final CollectionRegister collectionRegister;

    public PrintDescending(Console console, CollectionRegister collectionRegister) {
        super("print_descending", "print_descending",
                "вывести элементы коллекции в порядке убывания");
        this.collectionRegister = collectionRegister;
        this.console = console;
    }

    public CommandStatus execute(String[] args) {
        if (!args[1].trim().isEmpty()) {
            return wrongUsage();
        }
        console.println("Элементы коллекции в порядке убывания:");
        console.println(collectionRegister.getInformation(collectionRegister.reverseSort()));
        return super.execute(args);
    }
}

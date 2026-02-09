package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandInterface;
import com.example.registers.CollectionRegister;

public class Add extends BaseCommand implements CommandInterface {
    private final Console console;
    private final CollectionRegister collectionRegister;

    public Add(Console console, CollectionRegister collectionRegister) {
        super("add", "add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionRegister = collectionRegister;
    }

    public int execute() {
        collectionRegister.push("123");
        return 1;
    }
}

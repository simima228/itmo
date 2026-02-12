package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.models.Movie;
import com.example.registers.CollectionRegister;
import com.example.registers.ObjectRegister;

public class InsertAt extends BaseCommand {
    private final Console console;
    private final ObjectRegister objectRegister;
    private final CollectionRegister collectionRegister;

    public InsertAt(Console console, ObjectRegister objectRegister, CollectionRegister collectionRegister) {
        super("insert_at", "insert_at index {element}", "добавить новый элемент в заданную позицию");
        this.console = console;
        this.objectRegister = objectRegister;
        this.collectionRegister = collectionRegister;
    }

    public CommandStatus execute(String[] args) {
        String strId = args[1].trim();
        int id;
        if (strId.isEmpty()) {
            return wrongUsage();
        }
        try {
            id = Integer.parseInt(strId);
        }
        catch (NumberFormatException e) {
            return wrongUsage();
        }
        try {
            if ((id < 0 || id > collectionRegister.getLength() - 1) && id != 0){
                return new CommandStatus(false, "Индекс выходит за пределы количества элементов");
            }
            Movie movie = objectRegister.createMovie(console, collectionRegister.getNewId());
            collectionRegister.setStack(id, movie);
        }
        catch (ObjectRegister.Break e) {
            return new CommandStatus(false, e.getMessage());
        }
        return super.execute(args);
    }
}
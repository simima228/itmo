package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.models.Movie;
import com.example.registers.CollectionRegister;
import com.example.registers.ObjectRegister;

public class UpdateId extends BaseCommand {
    private Console console;
    private ObjectRegister objectRegister;
    private CollectionRegister collectionRegister;

    public UpdateId(Console console, ObjectRegister objectRegister, CollectionRegister collectionRegister) {
        super("update_id", "update_id {element}", "обновить значение" +
                " элемента коллекции, id которого равен заданному");
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
            int index = collectionRegister.getIndex(id);
            if (index == -1){
                return new CommandStatus(false, "Введенное id не найдено!");
            }
            Movie movie = objectRegister.createMovie(console, id);
            collectionRegister.delete(index);
            collectionRegister.setStack(index, movie);
        }
        catch (ObjectRegister.Break e) {
            return new CommandStatus(false, e.getMessage());
        }
        return super.execute(args);
    }
}
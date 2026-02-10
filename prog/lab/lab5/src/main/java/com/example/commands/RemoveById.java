package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.models.Movie;
import com.example.registers.CollectionRegister;
import com.example.registers.ObjectRegister;

public class RemoveById extends BaseCommand {
    private Console console;
    private CollectionRegister collectionRegister;

    public RemoveById(Console console, CollectionRegister collectionRegister) {
        super("remove_by_id","remove_by_id id", "удалить элемент из коллекции по его id");
        this.console = console;
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
        int index = collectionRegister.getIndex(id);
        if (index == -1){
            return new CommandStatus(false, "Введенное id не найдено!");
        }
        collectionRegister.delete(index);
        return super.execute(args);
    }
}
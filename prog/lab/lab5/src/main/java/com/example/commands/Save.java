package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.registers.CollectionRegister;
import com.example.registers.FileRegister;

import java.io.FileNotFoundException;

public class Save extends BaseCommand {
    private final FileRegister fileRegister;
    private final CollectionRegister collectionRegister;

    public Save(FileRegister fileRegister, CollectionRegister collectionRegister) {
        super("save", "save", "сохранить коллекцию в файл");
        this.fileRegister = fileRegister;
        this.collectionRegister = collectionRegister;
    }

    public CommandStatus execute(String[] args) {
        if (!args[1].trim().isEmpty()) {
            return wrongUsage();
        }
        return write();
    }

    public CommandStatus write() {
        try {
            fileRegister.writeCsv(collectionRegister.getStack());
            return new CommandStatus(true, "Команда выполнена успешно!");
        }
        catch (FileNotFoundException e){
            return new CommandStatus(false, "Произошла ошибка, на запись в файл нет прав!");
        }
    }
}
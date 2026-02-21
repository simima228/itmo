package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.registers.FileRegister;

import java.io.FileNotFoundException;

public class Save extends BaseCommand {
    private final Console console;
    private final FileRegister fileRegister;

    public Save(Console console, FileRegister fileRegister) {
        super("save", "save", "сохранить коллекцию в файл");
        this.console = console;
        this.fileRegister = fileRegister;
    }

    public CommandStatus execute(String[] args) {
        if (!args[1].trim().isEmpty()) {
            return wrongUsage();
        }
        return write();
    }

    public CommandStatus write() {
        try {
            fileRegister.writeCsv();
            return new CommandStatus(true, "Команда выполнена успешно!");
        }
        catch (FileNotFoundException e){
            return new CommandStatus(false, "Произошла ошибка, на запись в файл нет прав!");
        }
    }
}

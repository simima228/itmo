package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.registers.CollectionRegister;

public class Info extends BaseCommand {
    private final Console console;
    private final CollectionRegister collectionRegister;

    public Info(Console console, CollectionRegister collectionRegister) {
        super("info", "info", "вывести в стандартный поток вывода" +
                " информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.console = console;
        this.collectionRegister = collectionRegister;
    }

    public CommandStatus execute(String[] args) {
        if (!args[1].trim().isEmpty()) {
            return wrongUsage();
        }
        console.println("Информация о коллекции" + "\n"
                + "Тип: " + collectionRegister.toString() + "\n"
                + "Количество элементов" + ": " + collectionRegister.getStack().size() + "\n"
                + "Дата инициализация" + ": " + collectionRegister.getInitialDate() + "\n"
                + "Дата последнего изменения" + ": " + (collectionRegister.getChangeDate() == null
                ? "Изменений ещё не было": collectionRegister.getChangeDate()));
        return super.execute(args);
    }
}
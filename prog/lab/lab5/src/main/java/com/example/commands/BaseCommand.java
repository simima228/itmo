package com.example.commands;

import com.example.etc.CommandStatus;

abstract public class BaseCommand {
    private final String name;
    private final String description;
    private final String infoName;

    public BaseCommand(String name, String infoName, String description) {
        this.name = name;
        this.description = description;
        this.infoName = infoName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getInfoName() {
        return infoName;
    }

    public CommandStatus execute(String[] args) {
        return new CommandStatus(true, "\nКоманда выполнена успешно!\n");
    }

    public static CommandStatus wrongUsage() {
        return new CommandStatus(false, "\nКоманда используется неверно!\n");
    }

}

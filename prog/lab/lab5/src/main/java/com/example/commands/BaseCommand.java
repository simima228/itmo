package com.example.commands;

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




}

package com.example.commands;

public class UpdateId extends BaseCommand {

    public UpdateId() {
        super("update_id {element}", "update_id", "обновить значение" +
                " элемента коллекции, id которого равен заданному");

    }
}
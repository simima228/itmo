package com.example.commands;

public class Info extends BaseCommand {

    public Info() {
        super("info", "info", "вывести в стандартный поток вывода" +
                " информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");

    }
}
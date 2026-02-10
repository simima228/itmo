package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.models.Movie;
import com.example.registers.CollectionRegister;

public class CountGreaterThanOscarsCount extends BaseCommand {
    private Console console;
    private CollectionRegister collectionRegister;

    public CountGreaterThanOscarsCount(Console console, CollectionRegister collectionRegister) {
        super("count_greater_than_oscars_count","count_greater_than_oscars_count oscarsCount" +
                " oscarsCount", "вывести количество элементов значение поля oscarsCount " +
                "которых больше заданного");
        this.console = console;
        this.collectionRegister = collectionRegister;
    }

    public CommandStatus execute(String[] args) {
        String strCount = args[1].trim();
        double count;
        int elements = 0;
        if (strCount.isEmpty()) {
            return wrongUsage();
        }
        try {
            count = Double.parseDouble(strCount);
        }
        catch (NumberFormatException e) {
            return wrongUsage();
        }
        if (collectionRegister.getLength() == 0){
            console.println("Вы не добавили фильмы!");
            return super.execute(args);
        }
        for (Movie movie : collectionRegister.getStack()) {
            if (movie.getOscarsCount() > count) {
                elements++;
            }
        }
        console.println("Количество фильмов, у которых Оскаров больше заданного" + ": " + elements);
        return super.execute(args);
    }
}
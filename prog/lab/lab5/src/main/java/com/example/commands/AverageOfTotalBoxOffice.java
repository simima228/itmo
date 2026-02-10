package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.models.Movie;
import com.example.registers.CollectionRegister;


public class AverageOfTotalBoxOffice extends BaseCommand {
    private Console console;
    private CollectionRegister collectionRegister;

    public AverageOfTotalBoxOffice(Console console, CollectionRegister collectionRegister) {
        super("average_of_total_box_office", "average_of_total_box_office",
                "вывести среднее значение поля totalBoxOffice для всех элементов коллекции");
        this.console = console;
        this.collectionRegister = collectionRegister;
    }

    public CommandStatus execute(String[] args) {
        if (!args[1].trim().isEmpty()) {
            return wrongUsage();
        }
        double money = 0;
        int count = 0;
        for (Movie movie : collectionRegister.getStack()) {
            money += movie.getTotalBoxOffice();
            count++;
        }
        if (count == 0) {
            console.println("Вы не добавили фильмы!");
            return super.execute(args);
        }
        if (money == 0){
            console.println("У фильмов нет кассовых сборов.");
            return super.execute(args);
        }
        console.println(String.format("Среднее значение кассовых сборов" + ": %.2f", (money / count)));
        return super.execute(args);
    }
}
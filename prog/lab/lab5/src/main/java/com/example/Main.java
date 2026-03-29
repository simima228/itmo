package com.example;

import com.example.commands.*;
import com.example.console.Console;
import com.example.console.Printer;
import com.example.models.Movie;
import com.example.registers.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        Console console = new Console();
        console.initializeScanner();
        CommandRegister commandRegister = new CommandRegister();
        ObjectRegister objectRegister = new ObjectRegister();
        HistoryRegister historyRegister = new HistoryRegister();

        if (args.length != 1) {
            console.println("Введите корректное название файла при запуске!");
            System.exit(0);
        }

        FileRegister fileRegister = new FileRegister(args[0].trim(), console);
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            movies = fileRegister.readCsv();
        }

        catch (FileNotFoundException e) {
            console.println("Файл не найден, считывание информации невозможно!");
        }

        catch (FileRegister.WrongNumberException |
               FileRegister.WrongFieldException |
               FileRegister.EmptyFileException e) {
            console.println(e.getMessage());
        }
        CollectionRegister collectionRegister = new CollectionRegister(movies);
        collectionRegister.setNewId();

        commandRegister.register(new Help(console, commandRegister));
        commandRegister.register(new Info(console, collectionRegister));
        commandRegister.register(new Show(console, collectionRegister));
        commandRegister.register(new Add(console, collectionRegister, objectRegister));
        commandRegister.register(new UpdateId(console, objectRegister, collectionRegister));
        commandRegister.register(new RemoveById(collectionRegister));
        commandRegister.register(new Clear(collectionRegister));
        commandRegister.register(new Save(fileRegister, collectionRegister));
        commandRegister.register(new ExecuteScript());
        commandRegister.register(new Exit());
        commandRegister.register(new InsertAt(console, objectRegister, collectionRegister));
        commandRegister.register(new Sort(collectionRegister));
        commandRegister.register(new History(console, historyRegister));
        commandRegister.register(new AverageOfTotalBoxOffice(console, collectionRegister));
        commandRegister.register(new CountGreaterThanOscarsCount(console, collectionRegister));
        commandRegister.register(new PrintDescending(console, collectionRegister));

        Printer printer = new Printer(console, commandRegister, historyRegister, fileRegister);
        console.println("Вас приветствует командное приложение {вставьте сюда название приложения перед сдачей}," +
                " для ознакомления с командами введите help.");
        printer.run(false);

}
}

package com.example;

import com.example.commands.*;
import com.example.console.Console;
import com.example.console.Printer;
import com.example.registers.*;

import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) {
        Console console = new Console();
        console.setDefaultScanner();
        CollectionRegister collectionRegister = new CollectionRegister();
        CommandRegister commandRegister = new CommandRegister();
        ObjectRegister objectRegister = new ObjectRegister();
        HistoryRegister historyRegister = new HistoryRegister();

        if (args.length != 1) {
            console.println("Введите корректное название файла при запуске!");
            System.exit(0);
        }

        FileRegister fileRegister = new FileRegister(args[0].trim(), console, collectionRegister);

        try {
            fileRegister.readCsv();
        }

        catch (FileNotFoundException e) {
            console.println("Файл не найден, считывание информации невозможно!");
        }

        catch (FileRegister.WrongNumberException |
               FileRegister.WrongFieldException |
               FileRegister.EmptyFileException |
                FileRegister.NoRightsException e) {
            console.println(e.getMessage());
        }

        commandRegister.register(new Help(console, commandRegister));
        commandRegister.register(new Info(console, collectionRegister));
        commandRegister.register(new Show(console, collectionRegister));
        commandRegister.register(new Add(console, collectionRegister, objectRegister));
        commandRegister.register(new UpdateId(console, objectRegister, collectionRegister));
        commandRegister.register(new RemoveById(console, collectionRegister));
        commandRegister.register(new Clear(console, collectionRegister));
        commandRegister.register(new Save(console, fileRegister));
        commandRegister.register(new ExecuteScript(console));
        commandRegister.register(new Exit());
        commandRegister.register(new InsertAt(console, objectRegister, collectionRegister));
        commandRegister.register(new Sort(console, collectionRegister));
        commandRegister.register(new History(console, historyRegister));
        commandRegister.register(new AverageOfTotalBoxOffice(console, collectionRegister));
        commandRegister.register(new CountGreaterThanOscarsCount(console, collectionRegister));
        commandRegister.register(new PrintDescending(console, collectionRegister));

        Printer printer = new Printer(console, commandRegister, historyRegister, fileRegister);
        console.println("Вас приветствует командное приложение MovieManager3000," +
                " для ознакомления с командами введите help.");
        printer.run(false);

}
}

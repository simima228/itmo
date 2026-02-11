package com.example;

import com.example.commands.*;
import com.example.console.Console;
import com.example.console.Printer;
import com.example.models.MpaaRating;
import com.example.registers.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Console console = new Console();
        CollectionRegister collectionRegister = new CollectionRegister();
        CommandRegister commandRegister = new CommandRegister();
        ObjectRegister objectRegister = new ObjectRegister();
        HistoryRegister historyRegister = new HistoryRegister();
        FileRegister fileRegister = new FileRegister("test1.csv", console, collectionRegister);
        try {
            fileRegister.readCsv();
        }
        catch (FileNotFoundException e) {
            console.println("Файл не найден, считывание информации невозможно!");
        }
        catch (FileRegister.WrongNumberException | FileRegister.WrongFieldException e) {
            console.println(e.getMessage());
        }
        commandRegister.register(new Help(console, commandRegister));
        commandRegister.register(new Add(console, collectionRegister, objectRegister));
        commandRegister.register(new Exit());
        commandRegister.register(new History(console, historyRegister));
        commandRegister.register(new Info(console, collectionRegister));
        commandRegister.register(new Show(console, collectionRegister));
        commandRegister.register(new UpdateId(console, objectRegister, collectionRegister));
        commandRegister.register(new RemoveById(console, collectionRegister));
        commandRegister.register(new InsertAt(console, objectRegister, collectionRegister));
        commandRegister.register(new Clear(console, collectionRegister));
        commandRegister.register(new Sort(console, collectionRegister));
        commandRegister.register(new AverageOfTotalBoxOffice(console, collectionRegister));
        commandRegister.register(new CountGreaterThanOscarsCount(console, collectionRegister));
        commandRegister.register(new PrintDescending(console, collectionRegister));
        commandRegister.register(new Save(console, fileRegister));

        Printer printer = new Printer(console, commandRegister, historyRegister);
        printer.run();

}
}

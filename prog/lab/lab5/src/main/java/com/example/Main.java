package com.example;

import com.example.commands.*;
import com.example.console.Console;
import com.example.console.Printer;
import com.example.registers.CollectionRegister;
import com.example.registers.CommandRegister;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Console console = new Console();
        CollectionRegister collectionRegister = new CollectionRegister();
        CommandRegister commandRegister = new CommandRegister();
        commandRegister.register(new Help());
        Printer printer = new Printer(console, commandRegister);
        printer.run();

}
}

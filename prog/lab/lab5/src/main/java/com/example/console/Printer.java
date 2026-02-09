package com.example.console;
import com.example.registers.CollectionRegister;
import com.example.console.Console;
import com.example.registers.CommandRegister;

import java.util.Scanner;

public class Printer {

    private Console console;
    private CommandRegister commandRegister;

    public Printer(Console console, CommandRegister commandRegister) {
        this.console = console;
        this.commandRegister = commandRegister;
    }

    public void run() {
        while (true) {
            String[] command = console.read().split(" ");
            console.print(command[0] + command[1]);
        }
    }
}

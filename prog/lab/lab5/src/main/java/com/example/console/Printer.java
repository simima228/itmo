package com.example.console;
import com.example.commands.BaseCommand;
import com.example.etc.CommandStatus;
import com.example.models.MpaaRating;
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
        console.println("Вас приветствует командное приложение lab5, для ознакомления с командами введите help.");
        while (true) {
            String[] command = console.read().split(" ");
            executeCommand(command[0]);
        }

    }

    public boolean executeCommand(String tempCommand) {
        var command = commandRegister.getCommands().get(tempCommand);
        var commandStatus = command.execute();
        if (commandStatus.getMessage().equals("ㅤ")){
            System.exit(0);
        }
        else {
            console.println(commandStatus.getMessage());
        }
        return true;
    }
}

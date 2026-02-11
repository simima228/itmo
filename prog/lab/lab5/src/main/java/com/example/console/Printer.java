package com.example.console;
import com.example.commands.BaseCommand;
import com.example.etc.CommandStatus;
import com.example.models.MpaaRating;
import com.example.registers.CollectionRegister;
import com.example.console.Console;
import com.example.registers.CommandRegister;
import com.example.registers.HistoryRegister;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class Printer {

    private Console console;
    private CommandRegister commandRegister;
    private HistoryRegister historyRegister;

    public Printer(Console console, CommandRegister commandRegister, HistoryRegister historyRegister) {
        this.console = console;
        this.commandRegister = commandRegister;
        this.historyRegister = historyRegister;
    }

    public void run() {
        console.println("Вас приветствует командное приложение {вставьте название вашего приложения во время сдачи}," +
                " для ознакомления с командами введите help.");
        while (true) {
            console.println("Введите команду: ");
            try {
                String[] command = (console.read() + " ").split(" ", 2);
                executeCommand(command);
            } catch (NullPointerException e) {
                console.println("Некорректная команда! Попробуйте ещё раз.");
            }
        }

    }

    public void executeCommand(String[] tempCommand) {
        var command = commandRegister.getCommands().get(tempCommand[0]);
        var commandStatus = command.execute(tempCommand);
        if (commandStatus.getMessage().equals("ㅤ")){
            System.exit(0);
        }
        else {
            console.println(commandStatus.getMessage());
            if (commandStatus.getStatus()){
                historyRegister.addHistory(tempCommand[0]);
            }
        }
    }


}

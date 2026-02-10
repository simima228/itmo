package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;
import com.example.registers.HistoryRegister;


public class History extends BaseCommand {
    private HistoryRegister historyRegister;
    private Console console;

    public History(Console console, HistoryRegister historyRegister) {
        super("history", "history", "вывести последние 10 команд (без их аргументов)");
        this.console = console;
        this.historyRegister = historyRegister;
    }

    public CommandStatus execute(String[] args) {
        if (!args[1].trim().isEmpty()) {
            return wrongUsage();
        }
        console.println("История команд" + ": " + historyRegister.getHistory());
        return super.execute(args);
    }
}

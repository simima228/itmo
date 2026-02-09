package com.example.commands;

import com.example.etc.CommandStatus;

public class Exit extends BaseCommand {

    public Exit() {
        super("exit", "exit", "завершить программу (без сохранения в файл)");
    }

    public CommandStatus execute() {
        return new CommandStatus(true, "ㅤ");
    }
}

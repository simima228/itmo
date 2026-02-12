package com.example.commands;

import com.example.console.Console;
import com.example.etc.CommandStatus;

public class ExecuteScript extends BaseCommand {
    private final Console console;

    public ExecuteScript(Console console) {
        super("execute_script", "execute_script file_name",
                "считать и исполнить скрипт из указанного файла." +
                " В скрипте содержатся команды в таком же виде," +
                " в котором их вводит пользователь в интерактивном режиме.");
        this.console = console;
    }

    @Override
    public CommandStatus execute(String[] args) {
        if (args[1].trim().isEmpty()){
            return wrongUsage();
        }
        return super.execute(args);
    }
}

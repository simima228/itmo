package com.example.commands;

public class ExecuteScript extends BaseCommand {

    public ExecuteScript() {
        super("execute_script", "execute_script file_name",
                "считать и исполнить скрипт из указанного файла." +
                " В скрипте содержатся команды в таком же виде," +
                " в котором их вводит пользователь в интерактивном режиме.");

    }
}

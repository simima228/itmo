package com.example.console;
import com.example.registers.CommandRegister;
import com.example.registers.FileRegister;
import com.example.registers.HistoryRegister;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Printer {

    private Console console;
    private CommandRegister commandRegister;
    private HistoryRegister historyRegister;
    private FileRegister fileRegister;

    public Printer(Console console, CommandRegister commandRegister, HistoryRegister historyRegister,
                   FileRegister fileRegister) {
        this.console = console;
        this.commandRegister = commandRegister;
        this.historyRegister = historyRegister;
        this.fileRegister = fileRegister;
    }

    public void run() {
        console.println("Вас приветствует командное приложение {вставьте название вашего приложения во время сдачи}," +
                " для ознакомления с командами введите help.");
        while (true) {
            console.println("Введите команду: ");
            try {
                String[] command = (console.read() + " ").split(" ", 2);
                String status = executeCommand(command);
                if (status.equals("ㅤ")){
                    console.println("Завершение программы...");
                    break;
                }
                console.println(status);
            } catch (NullPointerException e) {
                console.println("Некорректная команда! Попробуйте ещё раз.");
            }
            catch (FileRegister.NoRightsException | FileNotFoundException |
                    FileRegister.EmptyFileException e){
                console.println(e.getMessage());
            }
        }

    }

    public String executeCommand(String[] tempCommand) throws
            FileRegister.NoRightsException, FileNotFoundException, FileRegister.EmptyFileException {
        var command = commandRegister.getCommands().get(tempCommand[0]);
        var commandStatus = command.execute(tempCommand);
        if (commandStatus.getStatus()){
            historyRegister.addHistory(tempCommand[0]);
        }
        if (tempCommand[0].equals("execute_script")) {
            try {
                console.println("Скрипт " + tempCommand[1].trim() + " выполняется...");
                scriptExecute(tempCommand[1].trim());
            }
            catch (FileNotFoundException | FileRegister.NoRightsException | FileRegister.EmptyFileException e) {
                console.println(e.getMessage());
            }
        }
        return commandStatus.getMessage();
    }

    private void scriptExecute(String args) throws
            FileRegister.NoRightsException, FileNotFoundException, FileRegister.EmptyFileException {
        try {
            ArrayList<String> commands = fileRegister.readScript(args);
            for (String command : commands) {
                executeCommand((command + " ").split(" ", -1));
            }
        }
        catch (FileRegister.EmptyFileException e) {
            throw new FileRegister.EmptyFileException();
        }
        catch (FileRegister.NoRightsException e) {
            throw new FileRegister.NoRightsException();
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("Файл не найден, считывание информации невозможно!");
        }
    }


}

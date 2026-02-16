package com.example.console;
import com.example.registers.CommandRegister;
import com.example.registers.FileRegister;
import com.example.registers.HistoryRegister;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Printer {


    private final Console console;
    private final CommandRegister commandRegister;
    private final HistoryRegister historyRegister;
    private final FileRegister fileRegister;

    public Printer(Console console, CommandRegister commandRegister, HistoryRegister historyRegister,
                   FileRegister fileRegister) {
        this.console = console;
        this.commandRegister = commandRegister;
        this.historyRegister = historyRegister;
        this.fileRegister = fileRegister;
    }

    public void run(boolean script) {
        while (!script || console.getScanner().hasNextLine()) {
            console.println("Введите команду: ");
            try {
                String[] command = (console.read() + " ").split(" ", 2);
                String status = executeCommand(command, script);
                if (status.equals("exit")){
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
            catch (NoSuchElementException e) {
                console.println("В скрипте недостаточно строк для заполнения полей или Вы нажали ctrl+d!");
                break;
            }
        }

    }

    public String executeCommand(String[] tempCommand, boolean script) throws
            FileRegister.NoRightsException, FileNotFoundException, FileRegister.EmptyFileException {
        var command = commandRegister.getCommands().get(tempCommand[0]);
        var commandStatus = command.execute(tempCommand);
        if (commandStatus.getStatus()){
            historyRegister.addHistory(tempCommand[0]);
        }
        if (tempCommand[0].equals("execute_script")) {
            try {
                console.println("Скрипт " + tempCommand[1].trim() + " выполняется...");
                scriptExecute(tempCommand[1].trim(), script);
                console.setDefaultScanner();
            }
            catch (FileNotFoundException | FileRegister.NoRightsException | FileRegister.EmptyFileException e) {
                console.println(e.getMessage());
            }
        }
        return commandStatus.getMessage();
    }

    private void scriptExecute(String args, boolean script) throws
            FileRegister.NoRightsException, FileNotFoundException, FileRegister.EmptyFileException {
        if (!script){
            if (!checkRecursion(args)){
                return;
            }
        }
        try {
            console.setScanner(fileRegister.read(args));
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
        run(true);
    }

    public boolean checkRecursion(String args) throws FileRegister.EmptyFileException,
            FileRegister.NoRightsException, FileNotFoundException {
        try {
            ArrayList<String> commands = fileRegister.readScript(args);
            for (String command : commands) {
                if ((command + " ").split(" ", 2)[0].trim().equals("execute_script")) {
                    console.println("В скрипте обнаружена рекурсия! Вы уверены, что хотите продолжить? Да/Нет");
                    if ((console.read() + " ").split(" ", 2)[0].trim().equalsIgnoreCase("да")){
                        int i = 1;
                        String newArgs = (command + " ").split(" ", 2)[1].trim();
                        while (i < 100){
                            boolean flag = false;
                            ArrayList<String> checkCommands = fileRegister.readScript(newArgs);
                            for (String checkCommand : checkCommands) {
                                if ((checkCommand + " ").split(" ", 2)[0].trim().equals("execute_script")) {
                                    flag = true;
                                    newArgs = (checkCommand + " ").split(" ", 2)[1].trim();
                                    break;
                                }
                            }
                            if (flag){
                                i++;
                            }
                            else{
                                break;
                            }
                        }
                        if (i == 100){
                            console.println("Превышена глубина рекурсии 100, выполнение невозможно!");
                            return false;
                        }
                        return true;
                    }
                    return false;
                }
                return true;
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
        return true;
    }


}

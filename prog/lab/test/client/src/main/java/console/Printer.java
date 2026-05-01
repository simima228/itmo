package console;

import io.FileRegister;
import network.Request;
import core.CommandInvoker;

import java.io.FileNotFoundException;
import java.util.*;

public class Printer {
    private final Console console;
    private final CommandInvoker commandInvoker;
    private final FileRegister fileRegister;
    private final Set<String> activeScripts = new HashSet<>();

    public Printer(Console console, CommandInvoker commandInvoker, FileRegister fileRegister) {
        this.console = console;
        this.commandInvoker = commandInvoker;
        this.fileRegister = fileRegister;
    }

    public void run() {
        Request request;
        console.println("Для использования авторизуйтесь или войдите в аккаунт! Без авторизации вы можете использовать help, exit, register, login.");
        console.println("Введите команду: ");
        while (console.getScanner().hasNextLine()) {
            try {
                Scanner currentScanner = console.getScanner();
                if (!currentScanner.hasNextLine()) {
                    if (console.scanners.size() > 1) {
                        console.removeScanner();
                        continue;
                    }
                    break;
                }
                String line = console.read();
                if (line.isEmpty()) {
                    console.println("Некорректная команда! Попробуйте ещё раз.");
                    continue;
                }
                request = commandInvoker.createRequest(line);
                if (request.commandName().equals("exit")) {
                    console.println("Завершение программы...");
                    break;
                }
                if (!request.commandName().equals("execute_script")) {
                    commandInvoker.responseHandler(request);
                    if (console.scanners.size() == 1) {
                        console.println("Введите команду: ");
                    }
                }
                else {
                    String scriptName = (String) request.arguments();
                    console.println("Скрипт " + scriptName.trim() + " выполняется...");
                    scriptExecute(scriptName.trim());
                    }
        }
            catch (NoSuchElementException e) {
                if (console.scanners.size() > 1) {
                    console.removeScanner();
                }
                else {
                console.println("В скрипте недостаточно строк для заполнения полей или вы нажали ctrl+d!");
                break;
                }
            }
            catch (Exception e) {
                console.println(e.getMessage());
            }
        }
    }
    private void scriptExecute(String args) throws FileNotFoundException,
            FileRegister.EmptyFileException {
        if (activeScripts.contains(args)) {
            console.println("Обнаружено зацикливание скриптов!");
            return;
        }
        if (activeScripts.size() > 100) {
            console.println("Превышена максимальная глубина вложенности скриптов!");
            return;
        }
        if (!checkRecursion(fileRegister.readScript(args))) {
            return;
        }
        try {
            console.addScanner(fileRegister.read(args));
        }
        catch (FileRegister.EmptyFileException e) {
            throw new FileRegister.EmptyFileException();
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("\nФайл не найден или к нему нет доступа," +
                    " считывание информации невозможно!");
        }
        try {
            activeScripts.add(args);
        }
        catch (NoSuchElementException e) {
            console.println("Во время выполнения скрипта произошла ошибка.");
        }
    }

    private boolean checkRecursion(ArrayList<String> commands) {
        for (String command : commands) {
            if ((command + " ").split(" ", 2)[0].trim().equals("execute_script")) {
                console.println("В скрипте обнаружена рекурсия, выполнение невозможно.");
                return false;
            }
        }
        return true;
        }
    }



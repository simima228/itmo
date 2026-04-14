package console;

import io.FileRegister;
import network.UDPClient;
import network.Request;
import core.CommandInvoker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Printer {
    private final Console console;
    private final UDPClient udpCLient;
    private final CommandInvoker commandInvoker;
    private final FileRegister fileRegister;
    private final Set<String> activeScripts = new HashSet<>();

    public Printer(Console console, UDPClient udpClient, CommandInvoker commandInvoker, FileRegister fileRegister) {
        this.console = console;
        this.udpCLient = udpClient;
        this.commandInvoker = commandInvoker;
        this.fileRegister = fileRegister;
    }

    public void run(boolean script) throws IOException, ClassNotFoundException {
        Request request;
        while (!script || console.getScanner().hasNextLine()){
            try {
                if (!script){
                console.println("Введите команду: ");}
                String line = console.read();
                if (line.isEmpty()){
                    console.println("Некорректная команда! Попробуйте ещё раз.");
                    continue;
                }
                request = commandInvoker.invoke(line);
                if (request.commandName().equals("exit")){
                    console.println("Завершение программы...");
                    break;
                }
                if (!request.commandName().equals("execute_script")) {
                    udpCLient.send(request);
                }
                else {
                    String scriptName = (String) request.arguments();
                    console.println("Скрипт " + scriptName.trim() + " выполняется...");
                    scriptExecute(scriptName.trim(), script);


                    }


        }
            catch (NoSuchElementException e){
                if (script){
                    console.removeScanner();
                    throw new NoSuchElementException("В скрипте недостаточно строк для заполнения полей или вы нажали ctrl+d!");
                }
                console.println("В скрипте недостаточно строк для заполнения полей или вы нажали ctrl+d!");
                break;
            }
            catch (Exception e){
                console.println(e.getMessage());
            }

            }
        }
    private void scriptExecute(String args, boolean script) throws FileNotFoundException,
            FileRegister.EmptyFileException {
        if (activeScripts.contains(args)){
            console.println("Обнаружено зацикливание скриптов!");
            return;
        }
        if (activeScripts.size() > 100){
            console.println("Превышена максимальная глубина вложенности скриптов!");
            return;
        }
        if (!script){
            if (!checkRecursion(fileRegister.readScript(args))){
                return;
            }
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
            run(true);
            console.removeScanner();
            activeScripts.remove(args);
        }
        catch (NoSuchElementException | IOException | ClassNotFoundException e) {
            console.println("Во время выполнения скрипта произошла ошибка.");
        }
        console.println("Скрипт выполнен успешно!");
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



package core;

import console.Console;
import etc.ViewClass;
import model.Movie;
import network.Request;
import etc.DataClass;
import network.Response;
import network.UDPClient;

import java.util.*;

public class CommandInvoker {
    private final Map<String, Map.Entry<DataClass, ViewClass>> commands = new LinkedHashMap<>();
    private final Console console;
    private final ObjectRegister objectRegister;
    private final UDPClient udpClient;
    private String login = null;
    private String password = null;

    public CommandInvoker(Console console, ObjectRegister objectRegister, UDPClient udpClient) {
        this.console = console;
        this.objectRegister = objectRegister;
        this.udpClient = udpClient;

    }

    public void initLoginAndPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void initialize() {
        this.commands.put("add", Map.entry(DataClass.MOV, ViewClass.PRIVATE));
        this.commands.put("average_of_total_box_office", Map.entry(DataClass.NONE, ViewClass.PRIVATE));
        this.commands.put("clear", Map.entry(DataClass.NONE, ViewClass.PRIVATE));
        this.commands.put("count_greater_than_oscars_count", Map.entry(DataClass.INT, ViewClass.PRIVATE));
        this.commands.put("exit", Map.entry(DataClass.NONE, ViewClass.PUBLIC));
        this.commands.put("help", Map.entry(DataClass.NONE, ViewClass.PUBLIC));
        this.commands.put("history", Map.entry(DataClass.NONE, ViewClass.PRIVATE));
        this.commands.put("info", Map.entry(DataClass.NONE, ViewClass.PRIVATE));
        this.commands.put("insert_at", Map.entry(DataClass.INT_MOV, ViewClass.PRIVATE));
        this.commands.put("print_descending", Map.entry(DataClass.NONE, ViewClass.PRIVATE));
        this.commands.put("remove_by_id", Map.entry(DataClass.INT, ViewClass.PRIVATE));
        this.commands.put("show", Map.entry(DataClass.NONE, ViewClass.PRIVATE));
        this.commands.put("sort", Map.entry(DataClass.NONE, ViewClass.PRIVATE));
        this.commands.put("update_id", Map.entry(DataClass.INT_MOV, ViewClass.PRIVATE));
        this.commands.put("execute_script", Map.entry(DataClass.SCRIPT, ViewClass.PRIVATE));
        this.commands.put("register", Map.entry(DataClass.STRING, ViewClass.PUBLIC));
        this.commands.put("login", Map.entry(DataClass.STRING, ViewClass.PUBLIC));
    }

    public Request createRequest(String line) throws Exception {
        String[] split = (line.trim() + " ").split(" ");
        if (commands.containsKey(split[0])) {
            Map.Entry<DataClass, ViewClass> entry = commands.get(split[0]);
            DataClass dataClass = entry.getKey();
            ViewClass viewClass = entry.getValue();
            if (login == null && viewClass == ViewClass.PRIVATE) {
                throw new Exception("Для использования этой команды нужно авторизироваться!");
            }
            if (((dataClass == DataClass.NONE || dataClass == DataClass.MOV) && split.length == 1)
                    || ((dataClass == DataClass.INT_MOV || dataClass == DataClass.INT || dataClass == DataClass.SCRIPT)
                    && split.length == 2) || (dataClass == DataClass.STRING && split.length == 3)) {
                switch (dataClass) {
                    case NONE:
                        return new Request(split[0], null, login, password);
                    case SCRIPT:
                        return new Request(split[0], split[1], login, password);
                    case MOV:
                        try {
                            Movie movie = objectRegister.createMovie(console, 0, login);
                            return new Request(split[0], movie, login, password);

                        }
                        catch (ObjectRegister.Break e) {
                            throw new Exception("Команда прервана.");
                        }
                    case INT:
                        try {
                            int id = Integer.parseInt(split[1]);
                            return new Request(split[0], id, login, password);
                        }
                        catch (NumberFormatException e) {
                            throw new Exception("ID должен быть целым числом.");
                        }
                    case INT_MOV:
                        try {
                            int id = Integer.parseInt(split[1]);
                            Movie movie = objectRegister.createMovie(console, id, login);
                            return new Request(split[0], Arrays.asList(movie, id), login, password);
                        } catch (ObjectRegister.Break e) {
                            throw new Exception("Команда прервана пользователем.");
                        }
                         catch (NumberFormatException e) {
                            throw new Exception("ID должен быть целым числом.");
                         }
                    case STRING:
                        return new Request(split[0], List.of(split[1], split[2]), login, password);

                }
            }
            throw new Exception("Некорректная команда, попробуйте еще раз.");
        }
        throw new Exception("Некорректная команда, попробуйте еще раз.");
    }
    public void responseHandler(Request request) throws Exception {
        Response response = udpClient.send(request);

        if (request.commandName().equals("login") && response.success()) {
            List<?> args = (List<?>) request.arguments();
            String newLogin = (String) args.get(0);
            String newPassword = (String) args.get(1);
            initLoginAndPassword(newLogin, newPassword);
        }

        if (!response.success()) {
            console.println("Не удалось выполнить команду.");
        }
        console.println(response.message());
    }

}

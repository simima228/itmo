package core;

import console.Console;
import model.Movie;
import network.Request;
import etc.DataClass;

import java.util.*;

public class CommandInvoker {
    private final Map<String, DataClass> commands = new LinkedHashMap<>();
    private final Console console;
    private final List intList = Arrays.asList("insert_at", "remove_by_id", "count_greater_than_oscars_count");
    private final List movieList = Arrays.asList("add", "update_id");
    private final ObjectRegister objectRegister;

    public CommandInvoker(Console console, ObjectRegister objectRegister) {
        this.console = console;
        this.objectRegister = objectRegister;

    }
    public void initialize(){
        this.commands.put("add", DataClass.MOV);
        this.commands.put("average_of_total_box_office", DataClass.NONE);
        this.commands.put("clear", DataClass.NONE);
        this.commands.put("count_greater_than_oscars_count", DataClass.INT);
        this.commands.put("exit", DataClass.NONE);
        this.commands.put("help", DataClass.NONE);
        this.commands.put("history", DataClass.NONE);
        this.commands.put("info", DataClass.NONE);
        this.commands.put("insert_at", DataClass.INT_MOV);
        this.commands.put("print_descending", DataClass.NONE);
        this.commands.put("remove_by_id", DataClass.INT);
        this.commands.put("show", DataClass.NONE);
        this.commands.put("sort", DataClass.NONE);
        this.commands.put("update_id", DataClass.INT_MOV);
        this.commands.put("execute_script", DataClass.SCRIPT);
    }

    public Request invoke(String line){
        String[] split = (line.trim() + " ").split(" ");
        if (split.length > 2){
            console.println("Некорректная команда, попробуйте ещё раз.");
            return new Request("error", "error");
        }
        if (commands.containsKey(split[0])){
            DataClass dataClass = commands.get(split[0]);
            if (((dataClass == DataClass.NONE || dataClass == DataClass.MOV) && split.length == 1)
                    || ((dataClass == DataClass.INT_MOV || dataClass == DataClass.INT || dataClass == DataClass.SCRIPT)
                    && split.length == 2)){
                switch (dataClass){
                    case NONE:
                        return new Request(split[0], null);
                    case SCRIPT:
                        return new Request(split[0], split[1]);
                    case MOV:
                        try {
                            Movie movie = objectRegister.createMovie(console, 0);
                            return new Request(split[0], movie);

                        }
                        catch (ObjectRegister.Break e) {
                            console.println("Команда прервана.");
                            return new Request("error", "error");
                        }
                    case INT:
                        try {
                            int id = Integer.parseInt(split[1]);
                            return new Request(split[0], id);
                        }
                        catch (NumberFormatException e) {
                            console.println("ID должен быть целым числом.");
                            return new Request("error", "error");
                        }
                    case INT_MOV:
                        try {
                            int id = Integer.parseInt(split[1]);
                            Movie movie = objectRegister.createMovie(console, id);
                            return new Request(split[0], Arrays.asList(movie, id));
                        } catch (ObjectRegister.Break e) {
                            console.println("Команда прервана пользователем.");
                            return new Request("error", "error");
                        }
                         catch (NumberFormatException e) {
                            console.println("ID должен быть целым числом.");
                            return new Request("error", "error");
                         }

                }
            }
            console.println("Некорректная команда, попробуйте еще раз.");
            return new Request("error", "error");
        }
        console.println("Некорректная команда, попробуйте еще раз.");
        return new Request("error", "error");
    }

}

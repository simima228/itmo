package commands;


import etc.DataClass;
import model.Movie;
import network.Request;
import network.Response;
import core.CollectionRegister;
import core.CommandRegister;
import etc.Log;
import io.FileRegister;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;

public class ExecuteScript extends BaseCommand {
    private final CollectionRegister collectionRegister;
    private final FileRegister fileRegister;
    private final CommandRegister commandRegister;

    public ExecuteScript(CollectionRegister collectionRegister, FileRegister fileRegister, CommandRegister commandRegister) {
        super("execute_script", "execute_script file_name",
                "считать и исполнить скрипт из указанного файла." +
                " В скрипте содержатся команды в таком же виде," +
                " в котором их вводит пользователь в интерактивном режиме.", DataClass.SCRIPT);
        this.collectionRegister = collectionRegister;
        this.fileRegister = fileRegister;
        this.commandRegister = commandRegister;
    }

    @Override
    public Response execute(Object arguments) {
        return new Response(true, "");}
}

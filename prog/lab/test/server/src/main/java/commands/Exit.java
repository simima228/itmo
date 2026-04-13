package commands;

import etc.DataClass;
import network.Response;


public class Exit extends BaseCommand {

    public Exit() {
        super("exit", "exit", "завершить программу", DataClass.NONE);
    }

    @Override
    public Response execute(Object arguments) {
        return null;
    }
}

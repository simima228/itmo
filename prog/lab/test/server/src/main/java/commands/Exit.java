package commands;


import network.Response;


public class Exit extends BaseCommand {

    public Exit() {
        super("exit", "exit", "завершить программу");
    }

    @Override
    public Response execute(Object arguments, String login, String password) {
        return null;
    }
}

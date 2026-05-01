package commands;


import io.Database;
import network.Response;
import core.HistoryRegister;


public class History extends BaseCommand {
    private final HistoryRegister historyRegister;

    public History(HistoryRegister historyRegister) {
        super("history", "history", "вывести последние 10 команд (без их аргументов)");
        this.historyRegister = historyRegister;
    }

    @Override
    public Response execute(Object arguments, String login, String password) {
        try {
            int id = Database.verifyUser(login, password);
            String check = checkUser(id);
            if (!check.equals("ok")) {
                return new Response(false, check);
            }
            return new Response(true, "История команд: " + historyRegister.getHistory(login));
        }
        catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }
}

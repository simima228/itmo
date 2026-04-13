package commands;

import etc.DataClass;
import network.Response;
import core.HistoryRegister;


public class History extends BaseCommand {
    private final HistoryRegister historyRegister;

    public History(HistoryRegister historyRegister) {
        super("history", "history", "вывести последние 10 команд (без их аргументов)", DataClass.NONE);
        this.historyRegister = historyRegister;
    }

    @Override
    public Response execute(Object arguments) {
        return new Response(true, "История команд: " + historyRegister.getHistory());
    }
}

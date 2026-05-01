package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryRegister {
    private final HashMap<String, ArrayList<String>> history;
    public HistoryRegister() {
        history = new HashMap<>();
    }
    public void addHistory(String history, String login) {
        this.history.computeIfAbsent(login, k -> new ArrayList<>());
        this.history.get(login).add(history);
    }
    public String getHistory(String login) {
        if (history.get(login).isEmpty()) {
            return "Вы пока не вводили команд!";
        }
        List<String> list = new ArrayList<>();
        for (int i = history.get(login).size() - 1; i >= 0; i--) {
            list.add(history.get(login).get(i));
        }
        StringBuilder sb = new StringBuilder();
        for (String s : list.subList(0, Math.min(list.size(), 10))) {
            sb.append(s).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }
}

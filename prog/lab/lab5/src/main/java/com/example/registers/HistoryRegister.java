package com.example.registers;

import java.util.ArrayList;
import java.util.List;

public class HistoryRegister {
    private final ArrayList<String> history;
    public HistoryRegister() {
        history = new ArrayList<>();
    }
    public void addHistory(String history) {
        this.history.add(history);
    }
    public String getHistory() {
        if (history.isEmpty()) {
            return "Вы пока не вводили команд!";
        }
        List<String> list = new ArrayList<>();
        for (int i = history.size() - 1; i >= 0; i--) {
            list.add(history.get(i));
        }
        StringBuilder sb = new StringBuilder();
        for (String s : list.subList(0, Math.min(list.size(), 10))) {
            sb.append(s).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }
}

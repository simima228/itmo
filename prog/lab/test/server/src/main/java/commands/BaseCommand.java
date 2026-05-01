package commands;


import network.Response;

import java.sql.SQLException;

abstract public class BaseCommand {
    private final String name;
    private final String description;
    private final String infoName;

    public BaseCommand(String name, String infoName, String description) {
        this.name = name;
        this.description = description;
        this.infoName = infoName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getInfoName() {
        return infoName;
    }

    public abstract Response execute(Object arguments, String login, String password) throws SQLException;

    public String checkUser(int id) {
        if (id == -1) {
            return "Введенный пользователь не существует!";
        }
        if (id == -2) {
            return "Неверный пароль!";
        }
        return "ok";
    }

}

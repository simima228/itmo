package commands;


import io.Database;
import network.Response;

import java.sql.SQLException;
import java.util.List;

public class Login extends BaseCommand {

    public Login() {
        super("login", "login {login} {password}", "Войти в аккаунт.");
    }

    @Override
    public Response execute(Object arguments, String user, String pass) throws SQLException {
        if (!(arguments instanceof List<?> list)) {
            return new Response(false, "Неверный формат аргументов.");
        }

        String username = (String) list.get(0);
        String password = (String) list.get(1);

        try {
            int id = Database.verifyUser(username, password);

            if (id == -1) {
                return new Response(false, "Пользователь с таким логином не существует!");
            }
            if (id == -2) {
                return new Response(false, "Неверный пароль!");
            }

            return new Response(true, "Вы успешно авторизовались.");

        } catch (SQLException e) {
            return new Response(false, "Ошибка базы данных: " + e.getMessage());
        }
    }
}

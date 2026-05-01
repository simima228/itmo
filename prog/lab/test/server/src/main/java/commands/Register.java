package commands;

import etc.Cypher;

import io.Database;
import network.Response;

import java.sql.SQLException;
import java.util.List;

public class Register extends BaseCommand {

    public Register() {
        super("register", "register {login} {password}", "Зарегистрироваться.");
    }

    @Override
    public Response execute(Object arguments, String user, String pass) throws SQLException {
        try {
            if (!(arguments instanceof List<?> list)) {
                return new Response(false, "Неверный формат аргументов.");
            }

            String login = (String) list.get(0);
            String password = (String) list.get(1);

            if (login.length() < 3) {
                return new Response(false, "Логин должен состоять хотя бы из 3 символов.");
            }
            if (password.length() < 6) {
                return new Response(false, "Пароль должен состоять хотя бы из 6 символов.");
            }

            String salt = Cypher.generateSalt();
            String hashPassword = Cypher.hashPassword(password, salt);

            if (!Database.insertUser(login, hashPassword, salt)) {
                return new Response(false, "Пользователь с таким логином уже существует.");
            }

            return new Response(true, "Вы успешно зарегистрировались! Теперь вы можете войти в аккаунт.");

        } catch (SQLException e) {
            return new Response(false, "Ошибка базы данных: " + e.getMessage());
        }
    }
}

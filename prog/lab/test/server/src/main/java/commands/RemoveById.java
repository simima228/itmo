package commands;


import io.Database;
import network.Response;
import core.CollectionRegister;

import java.sql.SQLException;

public class RemoveById extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public RemoveById(CollectionRegister collectionRegister) {
        super("remove_by_id","remove_by_id id", "удалить элемент из коллекции по его id");
        this.collectionRegister = collectionRegister;
    }

    @Override
    public Response execute(Object arguments, String login, String password) {
        try {
            int id = Database.verifyUser(login, password);
            String check = checkUser(id);
            if (!check.equals("ok")) {
                return new Response(false, check);
            }
            int idMovie = collectionRegister.getIndex((int) arguments);
            if (idMovie == -1) {
                return new Response(false, "Введенное id не найдено!");
            }
            if (!Database.deleteMovie((int) arguments, id)) {
                return new Response(false, "Не удалось удалить фильм.");
            }
            collectionRegister.delete(idMovie);
            return new Response(true, "Фильм удален успешно!");
        }
        catch (SQLException e) {
            return new Response(false, "Ошибка базы данных: " + e.getMessage());
        }
        catch (Exception e) {
            return new Response(false, e.getMessage());
        }

    }
}
package commands;


import io.Database;
import model.Movie;
import network.Response;
import core.CollectionRegister;

import java.sql.SQLException;
import java.util.List;

public class UpdateId extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public UpdateId(CollectionRegister collectionRegister) {
        super("update_id", "update_id {element}", "обновить значение" +
                " элемента коллекции, id которого равен заданному");
        this.collectionRegister = collectionRegister;
    }

    @Override
    public Response execute(Object arguments, String login, String password) {
        if (!(arguments instanceof List<?> list)) {
            return new Response(false, "Произошла ошибка: неверный формат аргументов.");
        }
        try {
            Movie movie = (Movie) list.get(0);
            int id = (int) list.get(1);

            int index = collectionRegister.getIndex(id);
            System.out.println(index);
            if (index == -1) {
                return new Response(false, "Фильм с id = " + id + " не найден!");
            }

            int userId = Database.verifyUser(login, password);
            if (userId == -1 || userId == -2) {
                return new Response(false, "Ошибка авторизации!");
            }

            boolean updated = Database.updateMovie(id, movie, userId);
            if (!updated) {
                return new Response(false, "Вы не можете изменить этот фильм, т.к. вы не владелец!");
            }

            movie.setId(id);
            collectionRegister.setStack(index, movie);

            return new Response(true, "Фильм с id = " + id + " успешно обновлён!");

        } catch (SQLException e) {
            return new Response(false, "Ошибка базы данных: " + e.getMessage());
        }
    }
}
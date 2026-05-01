package commands;


import io.Database;
import model.Movie;
import network.Response;
import core.CollectionRegister;

import java.util.List;

public class InsertAt extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public InsertAt(CollectionRegister collectionRegister) {
        super("insert_at", "insert_at index {element}", "добавить новый элемент в заданную позицию");
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
            if (arguments instanceof List<?> list) {
                Movie movie = (Movie) list.get(0);
                int idMovie = (int) list.get(1);
                if ((idMovie < 0 || idMovie > collectionRegister.getLength() - 1) && idMovie != 0) {
                    return new Response(false, "Индекс выходит за пределы количества элементов");
                }
                int movieIdd = Database.insertMovie(movie, id);
                movie.setId(movieIdd);
                collectionRegister.add(idMovie, movie);
                return new Response(true, "Фильм успешно добавлен!");}

            return new Response(false, "Произошла ошибка.");
        }
        catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }
}
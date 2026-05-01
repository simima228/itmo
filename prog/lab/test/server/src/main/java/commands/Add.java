package commands;

import io.Database;
import model.Movie;
import network.Response;
import core.*;

import java.sql.SQLException;

public class Add extends BaseCommand{
    private final CollectionRegister collectionRegister;

    public Add(CollectionRegister collectionRegister) {
        super("add", "add {element}", "добавить новый элемент в коллекцию");
        this.collectionRegister = collectionRegister;
    }


    @Override
    public Response execute(Object arguments, String login, String password) throws SQLException {
        try {
            Movie movie = (Movie) arguments;
            int id = Database.verifyUser(login, password);
            String check = checkUser(id);
            if (!check.equals("ok")) {
                return new Response(false, check);
            }
            int movieId = Database.insertMovie(movie, id);
            movie.setId(movieId);
            collectionRegister.push(movie);
            return new Response(true, "Фильм успешно добавлен!");
        }
        catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }
}

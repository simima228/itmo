package commands;

import etc.DataClass;
import model.Movie;
import network.Response;
import core.*;

public class Add extends BaseCommand{
    private final CollectionRegister collectionRegister;

    public Add(CollectionRegister collectionRegister) {
        super("add", "add {element}", "добавить новый элемент в коллекцию", DataClass.MOV);
        this.collectionRegister = collectionRegister;
    }


    @Override
    public Response execute(Object arguments) {
        Movie movie = (Movie) arguments;
        movie.setId(collectionRegister.getNewId());
        collectionRegister.push(movie);
        return new Response(true, "Фильм успешно добавлен!");
    }
}

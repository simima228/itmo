package commands;

import etc.DataClass;
import model.Movie;
import network.Response;
import core.CollectionRegister;

import java.util.ArrayList;
import java.util.List;

public class InsertAt extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public InsertAt(CollectionRegister collectionRegister) {
        super("insert_at", "insert_at index {element}", "добавить новый элемент в заданную позицию", DataClass.INT_MOV);
        this.collectionRegister = collectionRegister;
    }

    @Override
    public Response execute(Object arguments) {
        if (arguments instanceof List<?> list) {
            Movie movie = (Movie) list.get(0);
            int id = (int) list.get(1);
            if ((id < 0 || id > collectionRegister.getLength() - 1) && id != 0){
                return new Response(false, "Индекс выходит за пределы количества элементов");
            }
            movie.setId(collectionRegister.getNewId());
            collectionRegister.setStack(id, movie);
            return new Response(true, "Фильм успешно добавлен!");}

        return new Response(false, "Произошла ошибка.");
    }
}
package commands;

import etc.DataClass;
import model.Movie;
import network.Response;
import core.CollectionRegister;

import java.util.List;

public class UpdateId extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public UpdateId(CollectionRegister collectionRegister) {
        super("update_id", "update_id {element}", "обновить значение" +
                " элемента коллекции, id которого равен заданному", DataClass.INT_MOV);
        this.collectionRegister = collectionRegister;
    }

    @Override
    public Response execute(Object arguments) {
        if (arguments instanceof List<?> list) {
        Movie movie = (Movie) list.get(0);
        int id = (int) list.get(1);
        int index = collectionRegister.getIndex(id);
        if (index == -1){
            return new Response(false, "Введенное id не найдено!");
        }
        collectionRegister.delete(index);
        movie.setId(id);
        collectionRegister.setStack(index, movie);
        return new Response(true, "ID обновлен успешно!");}

        return new Response(false, "Произошла ошибка.");
    }
}
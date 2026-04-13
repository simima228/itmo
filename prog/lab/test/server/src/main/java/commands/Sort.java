package commands;

import etc.DataClass;
import model.Movie;
import network.Response;
import core.CollectionRegister;

public class Sort extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public Sort(CollectionRegister collectionRegister) {
        super("sort", "sort", "отсортировать коллекцию в естественном порядке", DataClass.NONE);
        this.collectionRegister = collectionRegister;
    }

    @Override
    public Response execute(Object arguments) {
        collectionRegister.sort();
        return new Response(true, "Коллекция отсортирована успешно!");
    }
}
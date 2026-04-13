package commands;

import etc.DataClass;
import network.Response;
import core.*;


public class Clear extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public Clear(CollectionRegister collectionRegister) {
        super("clear", "clear", "очистить коллекцию", DataClass.NONE);
        this.collectionRegister = collectionRegister;
    }

    @Override
    public Response execute(Object arguments) {
        collectionRegister.clear();
        return new Response(true, "Коллекция очищена успешно.");
    }
}
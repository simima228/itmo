package commands;

import etc.DataClass;
import network.Response;
import core.CollectionRegister;

public class Show extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public Show(CollectionRegister collectionRegister) {
        super("show", "show", "вывести в стандартный" +
                " поток вывода все элементы коллекции в строковом представлении", DataClass.NONE);
        this.collectionRegister = collectionRegister;

    }

    @Override
    public Response execute(Object arguments) {
        return new Response(true, "Элементы коллекции:\n" + collectionRegister.getInformation(collectionRegister.getStack()));
    }
}
package commands;

import etc.DataClass;
import network.Response;
import core.CollectionRegister;

public class PrintDescending extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public PrintDescending(CollectionRegister collectionRegister) {
        super("print_descending", "print_descending",
                "вывести элементы коллекции в порядке убывания", DataClass.NONE);
        this.collectionRegister = collectionRegister;
    }

    @Override
    public Response execute(Object arguments) {
        return new Response(true, "Элементы коллекции в порядке убывания:\n" +
                collectionRegister.getInformation(collectionRegister.reverseSort()));
    }
}

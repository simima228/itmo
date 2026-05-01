package commands;


import io.Database;
import network.Response;
import core.CollectionRegister;

public class PrintDescending extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public PrintDescending(CollectionRegister collectionRegister) {
        super("print_descending", "print_descending",
                "вывести элементы коллекции в порядке убывания");
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
            return new Response(true, "Элементы коллекции в порядке убывания:\n" +
                    collectionRegister.getInformation(collectionRegister.reverseSort()));
        }
        catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }
}

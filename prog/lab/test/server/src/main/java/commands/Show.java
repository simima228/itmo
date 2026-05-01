package commands;


import io.Database;
import network.Response;
import core.CollectionRegister;

public class Show extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public Show(CollectionRegister collectionRegister) {
        super("show", "show", "вывести в стандартный" +
                " поток вывода все элементы коллекции в строковом представлении");
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
            return new Response(true, "Элементы коллекции:\n" + collectionRegister.getInformation(collectionRegister.getStack()));
        }
        catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }
}
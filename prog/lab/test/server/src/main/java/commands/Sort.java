package commands;


import io.Database;
import network.Response;
import core.CollectionRegister;

public class Sort extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public Sort(CollectionRegister collectionRegister) {
        super("sort", "sort", "отсортировать коллекцию в естественном порядке");
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
            collectionRegister.sort();
            return new Response(true, "Коллекция отсортирована успешно!");
        }
        catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }
}
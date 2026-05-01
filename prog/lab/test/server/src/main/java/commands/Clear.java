package commands;


import io.Database;
import network.Response;
import core.*;


public class Clear extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public Clear(CollectionRegister collectionRegister) {
        super("clear", "clear", "очистить коллекцию");
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
            Database.deleteMovies(id);
            collectionRegister.clear(Database.getOwner(id));
            return new Response(true, "Коллекция очищена успешно.");
        }
        catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }
}
package commands;


import io.Database;
import network.Response;
import core.*;

public class CountGreaterThanOscarsCount extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public CountGreaterThanOscarsCount(CollectionRegister collectionRegister) {
        super("count_greater_than_oscars_count","count_greater_than_oscars_count oscarsCount",
                "вывести количество элементов значение поля oscarsCount " +
                "которых больше заданного");
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
            int count = (int) arguments;

            if (collectionRegister.getLength() == 0) {
                return new Response(true, "Вы не добавили фильмы!");
            }

            long elements = collectionRegister.getStack().stream()
                    .filter(movie -> movie.getOscarsCount() > count)
                    .count();

            return new Response(true, "Количество фильмов, у которых Оскаров больше заданного" + ": " + elements);
        }
        catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }
}
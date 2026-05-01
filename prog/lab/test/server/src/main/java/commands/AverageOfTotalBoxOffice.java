package commands;


import io.Database;
import model.Movie;
import network.Response;
import core.*;

import java.sql.SQLException;


public class AverageOfTotalBoxOffice extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public AverageOfTotalBoxOffice(CollectionRegister collectionRegister) {
        super("average_of_total_box_office", "average_of_total_box_office",
                "вывести среднее значение поля totalBoxOffice для всех элементов коллекции");
        this.collectionRegister = collectionRegister;
    }

    @Override
    public Response execute(Object arguments, String login, String password) throws SQLException {
        try {
            int id = Database.verifyUser(login, password);
            String check = checkUser(id);
            if (!check.equals("ok")) {
                return new Response(false, check);
            }
        }
        catch (Exception e) {
            return new Response(false, e.getMessage());
        }
        double money = 0;
        int count = 0;
        for (Movie movie : collectionRegister.getStack()) {
            money += movie.getTotalBoxOffice();
            count++;
        }
        if (count == 0) {
            return new Response(true, "Вы не добавили фильмы!");
        }
        if (money == 0) {
            return new Response(true, "У фильмов нет кассовых сборов.");
        }
        return new Response(true, String.format("Среднее значение кассовых сборов" + ": %.2f", (money / count)));
    }
}
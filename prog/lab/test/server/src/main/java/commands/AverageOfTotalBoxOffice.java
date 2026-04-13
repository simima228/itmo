package commands;

import etc.DataClass;
import model.Movie;
import network.Response;
import core.*;


public class AverageOfTotalBoxOffice extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public AverageOfTotalBoxOffice(CollectionRegister collectionRegister) {
        super("average_of_total_box_office", "average_of_total_box_office",
                "вывести среднее значение поля totalBoxOffice для всех элементов коллекции", DataClass.NONE);
        this.collectionRegister = collectionRegister;
    }

    @Override
    public Response execute(Object arguments) {
        double money = 0;
        int count = 0;
        for (Movie movie : collectionRegister.getStack()) {
            money += movie.getTotalBoxOffice();
            count++;
        }
        if (count == 0) {
            return new Response(true, "Вы не добавили фильмы!");
        }
        if (money == 0){
            return new Response(true, "У фильмов нет кассовых сборов.");
        }
        return new Response(true, String.format("Среднее значение кассовых сборов" + ": %.2f", (money / count)));
    }
}
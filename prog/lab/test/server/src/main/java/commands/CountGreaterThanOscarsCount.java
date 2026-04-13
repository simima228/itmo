package commands;

import etc.DataClass;
import network.Response;
import core.*;

public class CountGreaterThanOscarsCount extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public CountGreaterThanOscarsCount(CollectionRegister collectionRegister) {
        super("count_greater_than_oscars_count","count_greater_than_oscars_count oscarsCount",
                "вывести количество элементов значение поля oscarsCount " +
                "которых больше заданного", DataClass.INT);
        this.collectionRegister = collectionRegister;
    }

    @Override
    public Response execute(Object arguments) {
        int count = (int) arguments;

        if (collectionRegister.getLength() == 0) {
            return new Response(true, "Вы не добавили фильмы!");
        }

        long elements = collectionRegister.getStack().stream()
                .filter(movie -> movie.getOscarsCount() > count)
                .count();

        return new Response(true, "Количество фильмов, у которых Оскаров больше заданного" + ": " + elements);
    }
}
package commands;


import etc.DataClass;
import network.Response;
import core.CollectionRegister;

public class Info extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public Info(CollectionRegister collectionRegister) {
        super("info", "info", "вывести в стандартный поток вывода" +
                " информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", DataClass.NONE);
        this.collectionRegister = collectionRegister;
    }

    @Override
    public Response execute(Object arguments) {
        String changeDate = collectionRegister.getChangeDate() == null
                ? "Изменений ещё не было"
                : collectionRegister.getChangeDate().toString();

        String response = String.format(
                "Информация о коллекции\n" +
                        "Тип: %s\n" +
                        "Количество элементов: %d\n" +
                        "Дата инициализация: %s\n" +
                        "Дата последнего изменения: %s",
                collectionRegister,
                collectionRegister.getStack().size(),
                collectionRegister.getInitialDate(),
                changeDate
        );

        return new Response(true, response);
    }
}
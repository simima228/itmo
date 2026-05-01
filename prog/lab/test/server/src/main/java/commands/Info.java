package commands;



import io.Database;
import network.Response;
import core.CollectionRegister;

public class Info extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public Info(CollectionRegister collectionRegister) {
        super("info", "info", "вывести в стандартный поток вывода" +
                " информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
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
            String changeDate = collectionRegister.getChangeDate() == null
                    ? "Изменений ещё не было"
                    : collectionRegister.getChangeDate().toString();

            String response = String.format(
                    """
                            Информация о коллекции
                            Тип: %s
                            Количество элементов: %d
                            Дата инициализация: %s
                            Дата последнего изменения: %s""",
                    collectionRegister,
                    collectionRegister.getStack().size(),
                    collectionRegister.getInitialDate(),
                    changeDate
            );

            return new Response(true, response);
        }
        catch (Exception e) {
            return new Response(false, e.getMessage());
        }
    }
}
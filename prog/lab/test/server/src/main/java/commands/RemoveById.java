package commands;

import etc.DataClass;
import network.Response;
import core.CollectionRegister;

public class RemoveById extends BaseCommand {
    private final CollectionRegister collectionRegister;

    public RemoveById(CollectionRegister collectionRegister) {
        super("remove_by_id","remove_by_id id", "удалить элемент из коллекции по его id", DataClass.INT);
        this.collectionRegister = collectionRegister;
    }

    @Override
    public Response execute(Object arguments) {
        int id = collectionRegister.getIndex((int) arguments);
        if (id == -1) {
            return new Response(false, "Введенное id не найдено!");
        }
        collectionRegister.delete(id);
        return new Response(true, "Объект удален успешно!");

    }
}
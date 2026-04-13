package commands;

import etc.DataClass;
import network.Response;
import core.CollectionRegister;
import io.FileRegister;

import java.io.FileNotFoundException;

public class Save extends BaseCommand {
    private final FileRegister fileRegister;
    private final CollectionRegister collectionRegister;
    private final String fileName;

    public Save(FileRegister fileRegister, CollectionRegister collectionRegister, String fileName) {
        super("save", "save", "сохранить коллекцию в файл", DataClass.NONE);
        this.fileRegister = fileRegister;
        this.collectionRegister = collectionRegister;
        this.fileName = fileName;
    }

    @Override
    public Response execute(Object arguments) {
        try {
            fileRegister.writeCsv(collectionRegister.getStack(), fileName);
        } catch (FileNotFoundException e) {
            return new Response(false, "error");
        }
        return new Response(true, "success");
    }
}
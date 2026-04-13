import core.CollectionRegister;
import core.CommandRegister;
import core.HistoryRegister;
import etc.InitCommandRegister;
import io.FileRegister;
import network.UDPServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, FileRegister.WrongFieldException, FileRegister.WrongNumberException, FileRegister.EmptyFileException {
        final String DATABASE = "database.csv";
        HistoryRegister historyRegister = new HistoryRegister();
        CommandRegister commandRegister = new CommandRegister(historyRegister);

        FileRegister fileRegister = new FileRegister();
        CollectionRegister collectionRegister = new CollectionRegister(fileRegister.readCsv(DATABASE));
        collectionRegister.setNewId();
        new InitCommandRegister(collectionRegister, historyRegister,fileRegister, commandRegister).initialize();
        UDPServer udpServer = new UDPServer(commandRegister, fileRegister, collectionRegister, DATABASE);
        udpServer.run();
    }
}

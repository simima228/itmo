import core.CollectionRegister;
import core.CommandRegister;
import core.HistoryRegister;
import etc.InitCommandRegister;
import io.FileRegister;
import network.UDPServer;
public class Main {
    public static void main(String[] args) {
        final String DATABASE = "database.csv";
        HistoryRegister historyRegister = new HistoryRegister();
        CommandRegister commandRegister = new CommandRegister(historyRegister);

        FileRegister fileRegister = new FileRegister();
        CollectionRegister collectionRegister = new CollectionRegister(fileRegister.readCsv(DATABASE));
        collectionRegister.setNewId();
        new InitCommandRegister(collectionRegister, historyRegister,commandRegister).initialize();
        UDPServer udpServer = new UDPServer(commandRegister, fileRegister, collectionRegister, DATABASE);
        udpServer.run();
    }
}

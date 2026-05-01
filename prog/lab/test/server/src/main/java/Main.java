import core.CollectionRegister;
import core.CommandRegister;
import core.HistoryRegister;
import etc.InitCommandRegister;
import io.Database;
import network.UDPServer;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        HistoryRegister historyRegister = new HistoryRegister();
        CommandRegister commandRegister = new CommandRegister(historyRegister);
        CollectionRegister collectionRegister = new CollectionRegister(Database.loadFromDatabase());
        new InitCommandRegister(collectionRegister, historyRegister,commandRegister).initialize();
        UDPServer udpServer = new UDPServer(commandRegister);
        udpServer.run();
    }
}

import console.Console;
import console.Printer;
import core.CommandInvoker;
import core.ObjectRegister;
import io.FileRegister;
import network.UDPClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Console console = new Console();
        console.initializeScanner();
        UDPClient udpClient = new UDPClient(console);
        ObjectRegister objectRegister = new ObjectRegister();
        CommandInvoker commandInvoker = new CommandInvoker(console, objectRegister, udpClient);
        commandInvoker.initialize();
        FileRegister fileRegister = new FileRegister();
        Printer printer = new Printer(console, commandInvoker, fileRegister);
        printer.run();
    }
}

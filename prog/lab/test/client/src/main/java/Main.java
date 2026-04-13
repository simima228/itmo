import console.Console;
import console.Printer;
import core.CommandInvoker;
import core.ObjectRegister;
import io.FileRegister;
import network.UDPClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Console console = new Console();
        console.initializeScanner();
        ObjectRegister objectRegister = new ObjectRegister();
        CommandInvoker commandInvoker = new CommandInvoker(console, objectRegister);
        commandInvoker.initialize();
        UDPClient udpClient = new UDPClient(console);
        FileRegister fileRegister = new FileRegister();
        Printer printer = new Printer(console, udpClient, commandInvoker, fileRegister);
        printer.run(false);
    }
}

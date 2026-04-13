package network;

import java.io.IOException;
import java.net.*;

import etc.DataFramer;
import console.Console;
import etc.DataConverter;

public class UDPClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8888;
    private static final int BUFFER_SIZE = 2048;
    private static final int TIMEOUT = 100;
    private static final int RETRIES = 1000;
    private final Console console;
    private final DatagramSocket socket;
    private final InetAddress serverAddress;
    private final DataFramer framer = new DataFramer();

    public UDPClient(Console console) throws SocketException, UnknownHostException {
        this.console = console;
        this.socket = new DatagramSocket();
        this.socket.setSoTimeout(TIMEOUT);
        this.serverAddress = InetAddress.getByName(SERVER_HOST);
    }

    public void send(Request request) throws IOException, ClassNotFoundException {
        long id = System.currentTimeMillis();
        byte[] data = DataConverter.serialize(request);
        boolean received = false;
        byte[] receivedData = null;

        for (int i = 0; i < RETRIES && !received; i++) {
            framer.sendFramedSocket(socket, serverAddress, SERVER_PORT, id, data, BUFFER_SIZE);
            try {
                receivedData = framer.receiveFramed(socket, id, TIMEOUT);
                if (receivedData == null) {console.println("Сервер не отвечает, попытка отправки " + (i + 1) + "."); continue;}
                received = true;
            }
            catch (SocketTimeoutException e) {
                console.println("Сервер не отвечает, попытка отправки " + (i + 1) + ".");
            }
        }
        if (!received) {
            console.println("Сервер недоступен, попробуйте ещё раз.");
        }
        else {
            Response response = (Response) DataConverter.deserialize(receivedData);
            if (!response.success()) {
                console.println("Не удалось выполнить команду.");
            }
            console.println(response.message());
        }
    }
}

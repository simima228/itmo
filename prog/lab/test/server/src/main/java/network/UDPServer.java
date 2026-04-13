package network;

import etc.DataConverter;
import etc.DataFramer;
import etc.Frame;
import etc.FrameAssembler;
import core.CollectionRegister;
import core.CommandRegister;
import etc.Log;
import io.FileRegister;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Scanner;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Scanner;


public class UDPServer {
    private static final int SERVER_PORT = 8888;
    private static final int BUFFER_SIZE = 2048;
    private final CommandRegister commandRegister;
    private final FileRegister fileRegister;
    private final CollectionRegister collectionRegister;
    private volatile boolean running = true;
    private final FrameAssembler assembler = new FrameAssembler(10000);
    private final DataFramer framer = new DataFramer();
    private final String fileName;


    public UDPServer(CommandRegister commandRegister, FileRegister fileRegister, CollectionRegister collectionRegister, String fileName) {
        this.commandRegister = commandRegister;
        this.fileRegister = fileRegister;
        this.collectionRegister = collectionRegister;
        this.fileName = fileName;
    }

    public void run() {
        Thread consoleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                while (running) {
                    String command = scanner.nextLine().trim().toLowerCase();
                    if (command.equals("save")) {
                        try {
                            save();
                        } catch (IOException e) {
                            Log.error(e.getMessage());
                        }
                    } else if (command.equals("exit")) {
                        try {
                            exit();
                        } catch (IOException e) {
                            Log.error(e.getMessage());
                        }
                        break;
                    }
                }
            }
        });
        consoleThread.start();
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(SERVER_PORT));

            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
            Log.info("UDP сервер запущен на порту " + SERVER_PORT);

            ByteBuffer buffer = ByteBuffer.allocate(65535);

            while (running){
                selector.select(10);
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (!key.isReadable()){
                        continue;
                    }
                    DatagramChannel listeningChannel = (DatagramChannel) key.channel();
                    buffer.clear();
                    SocketAddress address = listeningChannel.receive(buffer);
                    if (address == null){
                        continue;
                    }
                    buffer.flip();

                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    try {

                        Frame frame = (Frame) DataConverter.deserialize(bytes);
                        byte[] data = assembler.addFrame(frame, address);
                        if (data == null){
                            continue;
                        }
                        Request request = (Request) DataConverter.deserialize(data);
                        Log.info("Получен запрос: " + request + " от " + address);
                        Response response = commandRegister.executor(request);
                        if (!response.success()){
                            Log.warn(response.message());
                        }
                        byte[] message = DataConverter.serialize(response);
                        framer.sendFramedChannel(channel, address, frame.getId(), message, BUFFER_SIZE);
                        Log.info("Отправлен ответ: " + response.message());}
                    catch (Exception e){
                        Log.error(e.getMessage());
                    }



                }
            }
            try {
                consoleThread.join();
            } catch (InterruptedException e) {
                Log.error(e.getMessage());
            }


        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }
    private void save() throws IOException {
        Log.info("Сохранение коллекции...");
        fileRegister.writeCsv(collectionRegister.getStack(), fileName);
        Log.info("Коллекция успешно сохранена!");
    }

    private void exit() throws IOException {
        running = false;
        save();
        Log.info("Закрытие сервера...");

    }


}

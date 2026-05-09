package network;

import etc.DataConverter;
import etc.DataFramer;
import etc.Frame;
import etc.FrameAssembler;
import core.CommandRegister;
import etc.Log;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.concurrent.*;

public class UDPServer {
    private static final int SERVER_PORT = 8888;
    private static final int BUFFER_SIZE = 2048;
    private final CommandRegister commandRegister;
    private volatile boolean running = true;
    private final FrameAssembler assembler = new FrameAssembler(10000);
    private final DataFramer framer = new DataFramer();

    private final ExecutorService readPool = Executors.newCachedThreadPool();

    private final ExecutorService writePool = Executors.newFixedThreadPool(4);

    public UDPServer(CommandRegister commandRegister) {
        this.commandRegister = commandRegister;
    }

    public void run() {
        ConsoleHandler consoleHandler = new ConsoleHandler(this);
        Thread consoleThread = new Thread(consoleHandler);
        consoleThread.start();

        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(SERVER_PORT));

            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
            Log.info("UDP сервер запущен на порту: " + SERVER_PORT);

            ByteBuffer buffer = ByteBuffer.allocate(65535);

            while (running) {
                selector.select(10);
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (!key.isReadable()) {
                        continue;
                    }
                    DatagramChannel listeningChannel = (DatagramChannel) key.channel();
                    buffer.clear();
                    SocketAddress address = listeningChannel.receive(buffer);
                    if (address == null) {
                        continue;
                    }
                    buffer.flip();

                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);

                    final SocketAddress finalAddress = address;
                    readPool.submit(() -> readHandler(bytes, finalAddress, channel));
                }
            }
        } catch (Exception e) {
            Log.error(e.getMessage());
        }

    try {
        consoleThread.join();
    } catch (InterruptedException e) {
        Log.error(e.getMessage());
    } finally {
        readPool.shutdown();
        writePool.shutdown();
        Thread.currentThread().interrupt();
    }
}
    private void readHandler(byte[] bytes, SocketAddress address, DatagramChannel channel) {
        try {
            Frame frame = (Frame) DataConverter.deserialize(bytes);
            byte[] data = assembler.addFrame(frame, address);
            if (data == null) {
                return;
            }
            long frameId = frame.id();

            new Thread(() -> processHandler(data, frameId, address, channel)).start();
        } catch (Exception e) {
            Log.error("Ошибка чтения: " + e.getMessage());
        }
    }

    private void processHandler(byte[] bytes, long frameId, SocketAddress address, DatagramChannel channel) {
        try {
            Request request = (Request) DataConverter.deserialize(bytes);
            Log.info("Получен запрос: " + request.commandName() + " от " + address);

            Response response = commandRegister.executor(request);

            if (!response.success()) {
                Log.warn(response.message());
            }

            byte[] responseBytes = DataConverter.serialize(response);
            writePool.submit(() -> sendHandler(responseBytes, frameId, address, channel, response));
        } catch (Exception e) {
            Log.error("Ошибка обработки: " + e.getMessage());
        }
    }

    private void sendHandler(byte[] bytes, long frameId, SocketAddress address, DatagramChannel channel, Response response) {
        try {
            framer.sendFramedChannel(channel, address, frameId, bytes, BUFFER_SIZE);
            Log.info("Отправлен ответ: " + response.message());
        } catch (IOException e) {
            Log.error("Ошибка отправки: " + e.getMessage());
        }
    }

    void exit() throws IOException {
        running = false;
        readPool.shutdownNow();
        writePool.shutdownNow();
        Log.info("Закрытие сервера...");
    }
}
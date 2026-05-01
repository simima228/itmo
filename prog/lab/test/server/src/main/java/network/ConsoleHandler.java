package network;

import etc.Log;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleHandler implements Runnable {
    private final UDPServer server;
    private volatile boolean running;

    public ConsoleHandler(UDPServer server) {
        this.server = server;
        this.running = true;
    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (running) {
                try {
                    if (!scanner.hasNextLine()) {
                        break;
                    }
                    String command = scanner.nextLine().trim().toLowerCase();

                    if (command.equals("exit")) {
                        server.exit();
                        break;
                    }

                    if (command.equals("help")) {
                        System.out.println("Доступные команды:");
                        System.out.println("exit: остановить сервер");
                        System.out.println("help: справка");
                    }

                } catch (NoSuchElementException e) {
                    break;
                }
            }
        } catch (IOException e) {
            Log.error("Ошибка сервера: " + e.getMessage());
        } finally {
            running = false;
        }
    }
}
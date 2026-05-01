package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileRegister {


    public static class EmptyFileException extends Exception {
        @Override
        public String getMessage() {
            return "Файл пуст!";
        }
    }



    public Scanner read(String name) throws FileNotFoundException, EmptyFileException {
        try {
            if (!Files.isReadable(Paths.get(name))) {
                throw new EmptyFileException();
            }
            Scanner scanner = new Scanner(new File(name));
            if (!scanner.hasNext()) {
                throw new EmptyFileException();
            }
            return scanner;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
    }

    public ArrayList<String> readScript(String scriptName) throws FileNotFoundException,
            EmptyFileException {
        ArrayList<String> commands = new ArrayList<>();
        Scanner scanner;
        try {
            scanner = read(scriptName);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (EmptyFileException e) {
            throw new EmptyFileException();
        }
        while (scanner.hasNextLine()) {
            commands.add(scanner.nextLine().trim());
        }
        return commands;
    }

}

package com.example.registers;

import com.example.console.Console;
import com.example.models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.function.Function;
import java.util.function.Predicate;


public class FileRegister {
    private final String fileName;
    private PrintWriter writer;
    private final Console console;
    private final CollectionRegister collectionRegister;
    int fieldCount;

    public static class WrongNumberException extends Exception {
        @Override
        public String getMessage() {
            return "\nВ файле неверное количество полей у объектов\n";
        }
    }

    public static class WrongFieldException extends Exception {
        public WrongFieldException(String message) {
            super(message);
        }
    }

    public static class EmptyFileException extends Exception {
        @Override
        public String getMessage() {
            return "\nФайл пуст!\n";
        }
    }


    public FileRegister(String fileName, Console console, CollectionRegister collectionRegister) {
        this.console = console;
        this.fileName = fileName;
        this.collectionRegister = collectionRegister;
        this.fieldCount = 14;
    }

    public Scanner read(String name) throws FileNotFoundException, EmptyFileException{
        try {
            if (!Files.isReadable(Paths.get(name))) {
                throw new FileNotFoundException("Файла не существует или к нему нет доступа.");
            }
            Scanner scanner = new Scanner(new File(name));
            if (!scanner.hasNext()) {
                throw new EmptyFileException();
            }
            return scanner;
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("Файл не найден, считывание информации невозможно!");
        } catch (EmptyFileException e) {
            throw new EmptyFileException();
        }
    }

    public void readCsv() throws FileNotFoundException, WrongNumberException,
            WrongFieldException, EmptyFileException {
        Scanner scanner;
        try {
            scanner = read(fileName);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
        catch (EmptyFileException e) {
            throw new EmptyFileException();
        }
        ArrayList<String> fields = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.next().trim();
            fields.addAll(Arrays.asList(line.split(",", -1)));
        }
        if (fields.size() % fieldCount != 0) {
            throw new WrongNumberException();
        }
        try {
            ArrayList<Movie> movies = parseObjects(fields);
            for (Movie movie : movies) {
                collectionRegister.push(movie);
            }
        }
        catch (WrongFieldException e) {
            throw new WrongFieldException(e.getMessage());
        }
        console.println("Файл считан успешно!");
    }

    public ArrayList<String> readScript(String scriptName) throws FileNotFoundException,
            EmptyFileException {
        ArrayList<String> commands = new ArrayList<>();
        Scanner scanner;
        try {
            scanner = read(scriptName);
        }
        catch (FileNotFoundException e) {
                throw new FileNotFoundException();
            }
        catch (EmptyFileException e) {
                throw new EmptyFileException();
            }
        while (scanner.hasNextLine()) {
            commands.add(scanner.nextLine().trim());
        }
        return commands;
    }

    public void writeCsv() throws FileNotFoundException {
        try {
            this.writer = new PrintWriter(fileName);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("Файл не найден или нет прав на запись, запись невозможна!");
        }
        writer.println("movie_name,coordinate_x," +
                "coordinate_y,date,oscars,total_box,movie_genre," +
                "mpaa_rating,person_name,person_height,person_nationality,location_x,location_y,location_z");
        for (Movie movie: collectionRegister.getStack()){
            if (movie.getDirector() == null){
            writer.println(movie.getName() + "," + movie.getCoordinates().getX() + "," + movie.getCoordinates().getY()
            + "," + movie.getCreationDate() + "," + movie.getOscarsCount() + "," + movie.getTotalBoxOffice()
            + "," + (movie.getGenre() != null ? movie.getGenre() : "") + ","
                    + (movie.getMpaaRating() != null ? movie.getMpaaRating() : "") + ","
                    + ",".repeat(fieldCount - 8 - 1));
            }
            else {
                writer.println(movie.getName() + "," + movie.getCoordinates().getX() + ","
                        + movie.getCoordinates().getY()
                        + "," + movie.getCreationDate() + "," + movie.getOscarsCount() + "," + movie.getTotalBoxOffice()
                        + "," + (movie.getGenre() != null ? movie.getGenre() : "") + ","
                        + (movie.getMpaaRating() != null ? movie.getMpaaRating() : "") + ","
                        + movie.getDirector().getName()
                        + "," + movie.getDirector().getHeight() + "," + movie.getDirector().getNationality() +
                        "," + movie.getDirector().getLocation());
            }
        }
        writer.close();
    }

    private ArrayList<Movie> parseObjects(ArrayList<String> fields) throws WrongFieldException {
        try {
            ArrayList<Movie> movies = new ArrayList<>();
            int index = 0;
            List<String> headers = List.of(("movie_name,coordinate_x,coordinate_y,date,oscars,total_box,movie_genre," +
                    "mpaa_rating,person_name,person_height,person_nationality,location_x," +
                    "location_y,location_z").split(","));
            if (fields.subList(0, fieldCount).equals(headers)){
                index = 1;
            }
            for (int i = index; i < fields.size() / fieldCount; i++) {
                try {
                    movies.add(parseObject(fields.subList(i * fieldCount, (i + 1) * fieldCount)));
                }
                catch (WrongFieldException e) {
                    throw new WrongFieldException(e.getMessage() + " на " + (i + 1) + " строке файла" +
                            ", считывание файла невозможно");
                }
            }
            return movies;
        }
        catch (WrongFieldException e) {
            throw new WrongFieldException(e.getMessage());
        }
    }

    public Movie parseObject(List<String> data) throws WrongFieldException {
        try {
            String movieName = parseString(data.get(0), "Указано пустое имя фильма");
            Coordinates coordinates = getCoordinates(data.get(1), data.get(2));
            LocalDate creationDate = getDate(data.get(3));
            Long osc = getOsc(data.get(4));
            Integer totalBox = getTotalBoxOffice(data.get(5));
            MovieGenre genre = getGenre(data.get(6));
            MpaaRating mpaaRating = getMpaaRating(data.get(7));
            Person director;
            String directorName = data.get(8);
            String height = data.get(9);
            String country = data.get(10);
            String locationX = data.get(11);
            String locationY = data.get(12);
            String locationZ = data.get(13);
            if (directorName.isEmpty() && height.isEmpty() && country.isEmpty()
                    && locationX.isEmpty() && locationY.isEmpty() && locationZ.isEmpty()){
                director = null;
            }
            else {
                director = getPerson(directorName, height, country, locationX, locationY, locationZ);
            }
            return new Movie(collectionRegister.getNewId(), movieName, coordinates, creationDate, osc, totalBox,
                    genre, mpaaRating, director);
        }
        catch (WrongFieldException e) {
            throw new WrongFieldException(e.getMessage());
        }
    }

    private String parseString(String name, String error) throws WrongFieldException {
        if (name.isEmpty()){
            throw new WrongFieldException(error);
        }
        return name;
    }

    private <T extends Number> T parseNumber(String field, String error, Function<String, T> parser,
                                            Predicate<T> validator, String validationError) throws WrongFieldException {
        T num;
        try{
            num = parser.apply(field);
            if (validator != null){
                if (!validator.test(num)){
                    throw new WrongFieldException(validationError);
                }

            }
            return num;
        }
        catch (NumberFormatException e){
            throw new WrongFieldException(error);
        }

    }

    private <T extends Enum<T>> T parseEnum(String field, String error, Class<T> enumClass,
                                            boolean emptyCheck) throws WrongFieldException {
        if (field.isEmpty()){
            if (emptyCheck){
                throw new WrongFieldException(error);
            }
            return null;
        }
        try{
            return Enum.valueOf(enumClass, field.toUpperCase());
        }
        catch (IllegalArgumentException e){
            throw new WrongFieldException(error);
        }
    }

    private Coordinates getCoordinates(String x, String y) throws WrongFieldException {
        String error = "Указана некорректная координата %s";
        float X = parseNumber(x, String.format(error, "X"), Float::parseFloat, null, "");
        long Y = parseNumber(y, String.format(error, "Y"), Long::parseLong, null, "");
        return new Coordinates(X, Y);
    }

    private LocalDate getDate(String line) throws WrongFieldException {
        try {
            return LocalDate.parse(line);
        }
        catch (DateTimeParseException e) {
            throw new WrongFieldException("Указана некорректная дата");
        }
    }

    private long getOsc(String line) throws WrongFieldException {
        return parseNumber(line, "Указано некорректное количество Оскаров", Long::parseLong, x -> x > 0,
                "Указано некорректное количество Оскаров");
    }

    private int getTotalBoxOffice(String line) throws WrongFieldException {
        return parseNumber(line, "Указано некорректное количество кассовых сборов", Integer::parseInt, x -> x > 0,
                "Указано некорректное количество кассовых сборов");
    }

    private MovieGenre getGenre(String line) throws WrongFieldException {
        return parseEnum(line, "Указан некорректный жанр", MovieGenre.class, false);
    }

    private MpaaRating getMpaaRating(String line) throws WrongFieldException {
        return parseEnum(line, "Указан некорректный MPAA рейтинг", MpaaRating.class, false);
    }

    private Person getPerson(String name, String height, String country, String x, String y
    , String z) throws WrongFieldException {
        return new Person(getPersonName(name), getPersonHeight(height), getCountry(country), getLocation(x, y, z));
    }

    private String getPersonName(String line) throws WrongFieldException {
        return line;
    }

    private int getPersonHeight(String line) throws WrongFieldException {
        return parseNumber(line, "Указан некорректный рост", Integer::parseInt, x -> x > 0,
                "Указан рост меньший 1");
    }

    private Country getCountry(String line) throws WrongFieldException {
        return parseEnum(line, "Указана некорректная страна", Country.class, true);
    }

    private Location getLocation(String x, String y, String z) throws WrongFieldException {
        String error = "Указана некорректная координата %s локации";
        long X = parseNumber(x, String.format(error, "X"), Long::parseLong, null, "");
        int Y = parseNumber(x, String.format(error, "Y"), Integer::parseInt, null, "");
        double Z = parseNumber(x, String.format(error, "Z"), Double::parseDouble, null, "");
        return new Location(X, Y, Z);
    }

}

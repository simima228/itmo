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


public class FileRegister {
    private String fileName;
    private PrintWriter writer;
    private Console console;
    private CollectionRegister collectionRegister;
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

    public static class NoRightsException extends Exception {
        @Override
        public String getMessage() {
            return "\nК файлу нет доступа\n";
        }
    }

    public FileRegister(String fileName, Console console, CollectionRegister collectionRegister) {
        this.console = console;
        this.fileName = fileName;
        this.collectionRegister = collectionRegister;
        this.fieldCount = 14;
    }

    public Scanner read(String name) throws FileNotFoundException, EmptyFileException, NoRightsException {
        try {
            String path = "src/main/java/com/example/files/" + name;
            if (!Files.isReadable(Paths.get(path))) {
                throw new NoRightsException();
            }
            Scanner scanner = new Scanner(new File(path));
            if (!scanner.hasNext()) {
                throw new EmptyFileException();
            }
            return scanner;
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("Файл не найден, считывание информации невозможно!");
        } catch (EmptyFileException e) {
            throw new EmptyFileException();
        } catch (NoRightsException e) {
            throw new NoRightsException();
        }
    }

    public void readCsv() throws FileNotFoundException, WrongNumberException,
            WrongFieldException, EmptyFileException, NoRightsException {
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
        catch (NoRightsException e) {
            throw new NoRightsException();
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
            ArrayList<Movie> movies = parseObjects(fields, 1);
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
            EmptyFileException, NoRightsException {
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
        catch (NoRightsException e) {
                throw new NoRightsException();
            }
        while (scanner.hasNextLine()) {
            commands.add(scanner.nextLine().trim());
        }
        return commands;
    }

    public void writeCsv() throws FileNotFoundException {
        try {
            this.writer = new PrintWriter(new File("src/main/java/com/example/files/" + fileName));
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("Файл не найден, запись невозможна!");
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

    private ArrayList<Movie> parseObjects(ArrayList<String> fields, int index) throws WrongFieldException {
        try {
            ArrayList<Movie> movies = new ArrayList<>();
            for (int i = index; i < fields.size() / fieldCount; i++) {
                try {
                    movies.add(parseObject(fields.subList(i * fieldCount, (i + 1) * fieldCount)));
                }
                catch (WrongFieldException e) {
                    throw new WrongFieldException(e.getMessage() + (index == 1 ? " на " + i + " строке файла" +
                            ", считывание файла невозможно": ""));}
            }
            return movies;
        }
        catch (WrongFieldException e) {
            throw new WrongFieldException(e.getMessage());
        }
    }

    public Movie parseObject(List<String> data) throws WrongFieldException {
        try {
            String movieName = getName(data.get(0));
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

    private String getName(String name) throws WrongFieldException {
        if (name.isEmpty()){
            throw new WrongFieldException("Указано пустое имя фильма");
        }
        return name;
    }

    private Coordinates getCoordinates(String x, String y) throws WrongFieldException {
        float X;
        long Y;
        try {
            X = Float.parseFloat(x);
        }
        catch (NumberFormatException e) {
            throw new WrongFieldException("Указана некорректная координата X");
        }
        try {
            Y = Long.parseLong(y);
        }
        catch (NumberFormatException e) {
            throw new WrongFieldException("Указана некорректная координата Y");
        }
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
        long osc;
        try {
            osc = Long.parseLong(line);
            if (!Movie.checkOscars(osc)){
                throw new WrongFieldException("Указано некорректное количество Оскаров");
            }
        }
        catch (NumberFormatException e) {
            throw new WrongFieldException("Указано некорректное количество Оскаров");
        }
        return osc;
    }

    private int getTotalBoxOffice(String line) throws WrongFieldException {
        int totalBox;
        try {
            totalBox = Integer.parseInt(line);
            if (!Movie.checkTotalBox(totalBox)){
                throw new WrongFieldException("Указано некорректное количество кассовых сборов");
            }
        }
        catch (NumberFormatException e) {
            throw new WrongFieldException("Указано некорректное количество кассовых сборов");
        }
        return totalBox;
    }

    private MovieGenre getGenre(String line) throws WrongFieldException {
        if (!line.isEmpty()){
            try {
                return MovieGenre.valueOf(line.toUpperCase());
            }
            catch (IllegalArgumentException e) {
                throw new WrongFieldException("Указан некорректный жанр");
            }
        }
        else {
            return null;
        }
    }

    private MpaaRating getMpaaRating(String line) throws WrongFieldException {
        if (!line.isEmpty()){
            try {
                return MpaaRating.valueOf(line.toUpperCase());
            }
            catch (IllegalArgumentException e) {
                throw new WrongFieldException("Указан некорректный MPAA рейтинг");
            }
        }
        else {
            return null;
        }
    }

    private Person getPerson(String name, String height, String country, String x, String y
    , String z) throws WrongFieldException {
        try {
            return new Person(getPersonName(name), getPersonHeight(height), getCountry(country), getLocation(x, y, z));
        }
        catch (WrongFieldException e) {
            throw new WrongFieldException(e.getMessage());
        }
    }

    private String getPersonName(String line) throws WrongFieldException {
        return line;
    }

    private int getPersonHeight(String line) throws WrongFieldException {
        int height;
        try {
            height = Integer.parseInt(line);
            if (!Person.checkHeight(height)){
                throw new WrongFieldException("Указан рост меньший 1");
            }
        }
        catch (NumberFormatException e) {
            throw new WrongFieldException("Указан некорректный рост");
        }
        return height;
    }

    private Country getCountry(String line) throws WrongFieldException {
        try {
            return Country.valueOf(line.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throw new WrongFieldException("Указана некорректная страна");
        }
    }

    private Location getLocation(String x, String y, String z) throws WrongFieldException {
        long X;
        int Y;
        Double Z;
        try {
            X = Long.parseLong(x);
        }
        catch (NumberFormatException e) {
            throw new WrongFieldException("Указана некорректная координата X локации");
        }
        try {
            Y = Integer.parseInt(y);
        }
        catch (NumberFormatException e) {
            throw new WrongFieldException("Указана некорректная координата Y локации");
        }
        try {
            Z = Double.parseDouble(z);
        }
        catch (NumberFormatException e) {
            throw new WrongFieldException("Указана некорректная координата Z локации");
        }
        return new Location(X, Y, Z);
    }

}

package com.example.registers;

import com.example.console.Console;
import com.example.models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.PrintWriter;


public class FileRegister {
    private String fileName;
    private Scanner scanner;
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

    public FileRegister(String fileName, Console console, CollectionRegister collectionRegister) {
        this.console = console;
        this.fileName = fileName;
        this.collectionRegister = collectionRegister;
        this.fieldCount = 14;
    }

    public void readCsv() throws FileNotFoundException, WrongNumberException, WrongFieldException {
        try {
            this.scanner = new Scanner(new File("src/main/java/com/example/files/" + fileName));
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("Файл не найден, считывание информации невозможно!");
        }
        ArrayList<String> fields = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.next().trim();
            fields.addAll(Arrays.asList(line.split(",", -1)));
        }
        if (fields.size() % fieldCount != 0) {
            throw new WrongNumberException();
        }
        ArrayList<Movie> movies = new ArrayList<>();
        for (int i = 1; i < fields.size() / fieldCount; i++) {
            String line;
            String movieName;
            Coordinates coordinates;
            Float coordinatesX;
            long coordinatesY;
            LocalDate creationDate;
            Long osc;
            Integer totalBox;
            MovieGenre genre;
            MpaaRating rating;
            Person director;
            String directorName;
            int height;
            Country country;
            Location location;
            Long locationX;
            Integer locationY;
            Double locationZ;
            line = fields.get(i * fieldCount);
            if (line.isEmpty()){
                throw new WrongFieldException("В строке данных файла номер " + i + " указано пустое имя фильма.");
            }
            movieName = line;
            try {
                coordinatesX = Float.parseFloat(fields.get(i * fieldCount + 1).replace(",", "."));
            }
            catch (NumberFormatException e) {
                throw new WrongFieldException("В строке данных файла номер " + i
                        + " указана некорректная координата X.");
            }
            try {
                coordinatesY = Long.parseLong(fields.get(i * fieldCount + 2));
            }
            catch (NumberFormatException e) {
                throw new WrongFieldException("В строке данных файла номер " + i
                        + " указана некорректная координата Y.");
            }
            coordinates = new Coordinates(coordinatesX, coordinatesY);
            try {
                creationDate = LocalDate.parse(fields.get(i * fieldCount + 3));
            }
            catch (DateTimeParseException e) {
                throw new WrongFieldException("В строке данных файла номер " + i + " указана некорректная дата.");
            }
            try {
                osc = Long.parseLong(fields.get(i * fieldCount + 4));
                if (!Movie.checkOscars(osc)){
                    throw new WrongFieldException("В строке данных файла номер " + i
                            + " указано некорректное количество Оскаров.");
                }
            }
            catch (NumberFormatException e) {
                throw new WrongFieldException("В строке данных файла номер " + i
                        + " указано некорректное количество Оскаров.");
            }
            try {
                totalBox = Integer.parseInt(fields.get(i * fieldCount + 5));
                if (!Movie.checkTotalBox(totalBox)){
                    throw new WrongFieldException("В строке данных файла номер " + i
                            + " указано некорректное количество кассовых сборов.");
                }
            }
            catch (NumberFormatException e) {
                throw new WrongFieldException("В строке данных файла номер " +
                        i + " указано некорректное количество кассовых сборов.");
            }
            line = fields.get(i * fieldCount + 6);
            if (!line.isEmpty()){
                try {
                    genre = MovieGenre.valueOf(line.toUpperCase());
                }
                catch (IllegalArgumentException e) {
                    throw new WrongFieldException("В строке данных файла номер " +
                            i + " указан некорректный жанр.");
                }
            }
            else {
                genre = null;
            }
            line = fields.get(i * fieldCount + 7);
            MpaaRating mpaaRating;
            if (!line.isEmpty()){
                try {
                    mpaaRating = MpaaRating.valueOf(line.toUpperCase());
                }
                catch (IllegalArgumentException e) {
                    throw new WrongFieldException("В строке данных файла номер " +
                            i + " указан некорректный MPAA рейтинг.");
                }
            }
            else {
                mpaaRating = null;
            }
            line = fields.get(i * fieldCount + 8);
            if (line.isEmpty()){
                movies.add(new Movie(collectionRegister.getNewId(), movieName, coordinates, creationDate, osc, totalBox,
                        genre, mpaaRating, null));
            }
            else {
                directorName = line;
                try {
                    height = Integer.parseInt(fields.get(i * fieldCount + 9));
                    if (!Person.checkHeight(height)){
                        throw new WrongFieldException("В строке данных файла номер " + i
                                + " указан рост меньший 1.");
                    }
                }
                catch (NumberFormatException e) {
                    throw new WrongFieldException("В строке данных файла номер " + i
                            + " указано некорректный рост.");
                }
                try {
                    country = Country.valueOf(fields.get(i * fieldCount + 10).toUpperCase());
                }
                catch (IllegalArgumentException e) {
                    throw new WrongFieldException("В строке данных файла номер " + i + " указана некорректная страна.");
                }
                try {
                    locationX = Long.parseLong(fields.get(i * fieldCount + 11));
                }
                catch (NumberFormatException e) {
                    throw new WrongFieldException("В строке данных файла номер " + i
                            + " указана некорректная координата X локации.");
                }
                try {
                    locationY = Integer.parseInt(fields.get(i * fieldCount + 12));
                }
                catch (NumberFormatException e) {
                    throw new WrongFieldException("В строке данных файла номер " + i
                            + " указана некорректная координата Y локации.");
                }
                try {
                    locationZ = Double.parseDouble(fields.get(i * fieldCount + 13).replace(",", "."));
                }
                catch (NumberFormatException e) {
                    throw new WrongFieldException("В строке данных файла номер " + i
                            + " указана некорректная координата Z локации.");
                }
                director = new Person(directorName, height, country, new Location(locationX, locationY, locationZ));
                movies.add(new Movie(collectionRegister.getNewId(), movieName, coordinates, creationDate, osc, totalBox,
                        genre, mpaaRating, director));
            }
        }
        for (Movie movie : movies){
            collectionRegister.push(movie);
        }
        console.println("Файл считан успешно!");
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
                    + (movie.getMpaaRating() != null ? movie.getMpaaRating() : "") + ",,,,,,");
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
}

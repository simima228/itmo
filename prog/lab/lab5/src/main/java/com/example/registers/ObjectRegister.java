package com.example.registers;

import com.example.console.Console;
import com.example.models.*;

import java.time.LocalDate;

public class ObjectRegister {
    public static class Break extends Exception {
        @Override
        public String getMessage() {
            return "\nКоманда прервана пользователем!\n";
        }
    }

    public Movie createMovie(Console console, int id) throws Break {
        String line;
        String name;
        Coordinates coordinates;
        Long oscars;
        Integer totalBox;
        MovieGenre genre;
        MpaaRating rating;
        Person director;
        console.println("Создание фильма...");
        console.println("Для выхода из создания напишите /exit");
        name = createMovieName(console);
        coordinates = createCoordinates(console);
        oscars = createOscars(console);
        totalBox = createTotalBox(console);
        genre = createGenre(console);
        rating = createRating(console);
        console.println("Хотите ли вы добавить режиссера фильма? Да/Нет: ");
        line = console.read().trim();
        if (line.equalsIgnoreCase("ДА")) {
            director = createPerson(console);
        } else {
            director = null;
        }
        return new Movie(id, name, coordinates, LocalDate.now(), oscars, totalBox, genre,
                rating, director);
        }

    private String createMovieName(Console console) throws Break {
        String line;
        String name;
        console.println("Введите название фильма: ");
        while (true){
            line = console.read().trim();
            if (line.equals("/exit")) {
                throw new Break();
            }
            if (line.isEmpty()){
                console.println("Название фильма не может быть пустым, введите корректное название: ");
                continue;
            }
            name = line;
            console.println("Название фильма успешно добавлено!");
            return name;
        }
    }

    private Long createOscars(Console console) throws Break {
        String line;
        Long oscars;
        console.println("Введите количество Оскаров (целое число больше нуля): ");
        while (true){
            line = console.read().trim();
            if (line.equals("/exit")) {
                throw new Break();
            }
            if (line.isEmpty()){
                console.println("Количество Оскаров не может быть пустым, введите корректное число: ");
                continue;
            }
            try {
                oscars = Long.parseLong(line);
                if (oscars <= 0){
                    console.println("Число должно быть положительным, введите корректное число: ");
                    continue;
                }
                console.println("Количество Оскаров успешно добавлено!");
                return oscars;
            }
            catch (NumberFormatException e){
                console.println("Введите корректное число Оскаров: ");
            }
        }
    }

    private Integer createTotalBox(Console console) throws Break {
        String line;
        Integer totalBox;
        console.println("Введите размер кассовых сборов (целое число больше нуля): ");
        while (true){
            line = console.read().trim();
            if (line.equals("/exit")) {
                throw new Break();
            }
            if (line.isEmpty()){
                console.println("Размер кассовых сборов не может быть пустым, введите корректное число: ");
            }
            try {
                totalBox = Integer.parseInt(line);
                if (totalBox <= 0){
                    console.println("Число должно быть положительным, введите корректное число: ");
                    continue;
                }
                console.println("Кассовые сборы успешно добавлен!");
                return totalBox;
            }
            catch (NumberFormatException e){
                console.println("Введите корректные кассовые сборы: ");
            }
        }
    }

    private Coordinates createCoordinates(Console console) throws Break {
        String line;
        Float x;
        long y;
        console.println("Создание координат...");
        console.println("Введите координату x в формате числа с плавающей точкой: ");
        while (true){
            line = console.read().trim();
            if (line.equals("/exit")) {
                throw new Break();
            }
            if (line.isEmpty()){
                console.println("Координата x не может быть пустой, введите корректное число: ");
                continue;
            }
            try {
                x = Float.parseFloat(line.replace(",", "."));
                console.println("Координата x успешно добавлена!");
                break;
            }
            catch (NumberFormatException e){
                console.println("Введите корректное число: ");
            }
        }
        console.println("Введите координату y в формате целого числа: ");
        while (true){
            line = console.read().trim();
            if (line.equals("/exit")) {
                throw new Break();
            }
            if (line.isEmpty()){
                console.println("Координата y не может быть пустой, введите корректное число: ");
                continue;
            }
            try {
                y = Long.parseLong(line);
                console.println("Координата y успешно добавлена!");
                break;
            }
            catch (NumberFormatException e){
                console.println("Введите корректное число: ");
            }
        }
        console.println("Координаты успешно добавлены!");
        return new Coordinates(x, y);
    }

    private MovieGenre createGenre(Console console) throws Break {
        String line;
        MovieGenre genre;
        console.println("Введите жанр фильма (если его нет, нажмите Enter)");
        console.println(MovieGenre.getGenre());
        while (true) {
            line = console.read().trim();
            if (line.equals("/exit")) {
                throw new Break();
            }
            if (line.isEmpty()) {
                return null;
            }
            try {
                genre = MovieGenre.valueOf(line.toUpperCase());
                console.println("Жанр успешно добавлен!");
                return genre;
            }
            catch (IllegalArgumentException e){
                console.println("Введите корректный жанр: ");
            }
        }
    }

    private MpaaRating createRating(Console console) throws Break {
        String line;
        MpaaRating rating;
        console.println("Введите MPAA рейтинг фильма (если его нет, нажмите Enter)");
        console.println(MpaaRating.getRatings());
        while (true) {
            line = console.read().trim();
            if (line.equals("/exit")) {
                throw new Break();
            }
            if (line.isEmpty()) {
                return null;
            }
            try {
                rating = MpaaRating.valueOf(line.toUpperCase());
                console.println("MPAA рейтинг успешно добавлен!");
                return rating;
            }
            catch (IllegalArgumentException e){
                console.println("Введите корректный MPAA рейтинг: ");
            }
        }
    }

    private Person createPerson(Console console) throws Break {
        return new Person(createPersonName(console), createPersonHeight(console), createPersonCountry(console),
                createPersonLocation(console));
    }

    private String createPersonName(Console console) throws Break {
        String line;
        String name;
        console.println("Введите имя человека: ");
        while (true){
            line = console.read().trim();
            if (line.equals("/exit")) {
                throw new Break();
            }
            if (line.isEmpty()){
                console.println("У человека не может быть имени, введите корректное имя: ");
                continue;
            }
            name = line;
            console.println("Имя успешно добавлено!");
            return name;
        }
    }

    private int createPersonHeight(Console console) throws Break {
        String line;
        int height;
        console.println("Введите его рост (целое число больше нуля): ");
        while (true){
            line = console.read().trim();
            if (line.equals("/exit")) {
                throw new Break();
            }
            if (line.isEmpty()){
                console.println("У человека не может не быть роста, введите корректный рост: ");
                continue;
            }
            try {
                height = Integer.parseInt(line);
                if (height <= 0){
                    console.println("Число должно быть положительным, введите корректное число: ");
                    continue;
                }
                console.println("Рост успешно добавлен!");
                return height;
            }
            catch (NumberFormatException e){
                console.println("Введите корректный рост: ");
            }
        }
    }

    private Country createPersonCountry(Console console) throws Break {
        String line;
        Country nationality;
        console.println("Введите его национальность: ");
        console.println(Country.getCountry());
        while (true) {
            line = console.read().trim();
            if (line.equals("/exit")) {
                throw new Break();
            }
            if (line.isEmpty()) {
                console.println("У человека не может не быть национальности, введите корректную страну: ");
                continue;
            }
            try {
                nationality = Country.valueOf(line.toUpperCase());
                console.println("Национальность успешно добавлена!");
                return nationality;
            }
            catch (IllegalArgumentException e){
                console.println("Введите корректную национальность: ");
            }
        }
    }

    private Location createPersonLocation(Console console) throws Break {
        String line;
        long x;
        int y;
        double z;
        console.println("Создание локации...");
        console.println("Введите координату x в формате целого числа: ");
        while (true){
            line = console.read().trim();
            if (line.equals("/exit")) {
                throw new Break();
            }
            if (line.isEmpty()){
                console.println("Координата x не может быть пустой, введите корректное число: ");
                continue;
            }
            try {
                x = Long.parseLong(line);
                console.println("Координата x успешно добавлена!");
                break;
            }
            catch (NumberFormatException e){
                console.println("Введите корректное число: ");
            }
        }
        console.println("Введите координату y в формате целого числа: ");
        while (true){
            line = console.read().trim();
            if (line.equals("/exit")) {
                throw new Break();
            }
            if (line.isEmpty()){
                console.println("Координата y не может быть пустой, введите корректное число: ");
                continue;
            }
            try {
                y = Integer.parseInt(line);
                console.println("Координата y успешно добавлена!");
                break;
            }
            catch (NumberFormatException e){
                console.println("Введите корректное число: ");
            }
        }
        console.println("Координаты успешно добавлены!");
        console.println("Введите координату z в формате числа с плавающей точкой: ");
        while (true){
            line = console.read().trim();
            if (line.equals("/exit")) {
                throw new Break();
            }
            if (line.isEmpty()){
                console.println("Координата z не может быть пустой, введите корректное число: ");
                continue;
            }
            try {
                z = Double.parseDouble(line.replace(",", "."));
                console.println("Координата z успешно добавлена!");
                break;
            }
            catch (NumberFormatException e){
                console.println("Введите корректное число: ");
            }
        }
        console.println("Координаты успешно добавлены!");
        return new Location(x, y, z);
    }
}

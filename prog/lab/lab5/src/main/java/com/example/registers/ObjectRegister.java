package com.example.registers;

import com.example.console.Console;
import com.example.models.*;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

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
            director = createDirector(console);
        } else {
            director = null;
        }
        return new Movie(id, name, coordinates, LocalDate.now(), oscars, totalBox, genre,
                rating, director);
    }


    private String parseField(Console console)
            throws Break {
        String line;
        line = console.read().trim();
        if (line.equalsIgnoreCase("/exit")) {
            throw new Break();
        }
        if (line.isEmpty()){
            return null;
        }
        return line;
    }

    private <T extends Enum<T>> T parseEnum(Console console, String request, String error,
                                            String emptyError, String success, String enumerate,
                                            Class<T> enumClass, boolean emptyCheck) throws Break {
        String line;
        T enumValue;
        console.println(request);
        console.println(enumerate);
        while (console.getScanner().hasNextLine()) {
            line = parseField(console);
            if (line != null) {
                try {
                    enumValue = Enum.valueOf(enumClass, line.toUpperCase());
                    console.println(success);
                    return enumValue;
                }
                catch (IllegalArgumentException e) {
                    console.println(error);
                }
            }
            else {
                if (!emptyCheck) {
                    return null;
                }
                console.println(emptyError);
            }

        }
        throw new NoSuchElementException();
    }

    private String parseString(Console console, String request, String emptyError,
                               String success) throws Break {
        String line;
        console.println(request);
        while (console.getScanner().hasNextLine()) {
            line = parseField(console);
            if (line == null){
                console.println(emptyError);
                continue;
            }
            console.println(success);
            return line;
        }
        throw new NoSuchElementException();
    }

    private <T extends Number> T parseNumber(Console console, String request, String emptyError,
                                             String success, Function<String, T> parser, Predicate<T> validator,
                                             String validationError) throws Break {
        String line;
        T number;
        console.println(request);
        while (console.getScanner().hasNextLine()) {
            line = parseField(console);
            if (line == null){
                console.println(emptyError);
                continue;
            }
            try {
                number = parser.apply(line.replace(",", "."));
                if (validator != null){
                    if (!validator.test(number)){
                        console.println(validationError);
                        continue;
                    }
                }
                console.println(success);
                return number;
            }
            catch (NumberFormatException e) {
                console.println("Введите корректное число: ");
            }
        }
        throw new NoSuchElementException();
    }

    private String createMovieName(Console console) throws Break {
        return parseString(console, "Введите название фильма: ",
                "Название фильма не может быть пустым, введите корректное название: ",
                "Название фильма успешно добавлено!");
    }

    private Coordinates createCoordinates(Console console) throws Break {
        String request = "Введите координату %s в формате ";
        String emptyError = "Координата %s не может быть пустой, введите корректное число: ";
        String success = "Координата %s успешно добавлена!";
        Float x = parseNumber(console, String.format(request, "x") + "числа с плавающей точкой: ",
                String.format(emptyError, "x"), String.format(success, "x"),
                Float::parseFloat, null, "");
        long y = parseNumber(console, String.format(request, "y") + "целого числа: ",
                String.format(emptyError, "y"), String.format(success, "y"),
                Long::parseLong, null, "");
        return new Coordinates(x, y);
    }

    private Long createOscars(Console console) throws Break {
        return parseNumber(console, "Введите количество Оскаров (целое число больше нуля): ",
                "Количество Оскаров не может быть пустым, введите корректное число: ",
                "Количество Оскаров успешно добавлено!",
                Long::parseLong, x -> x > 0,
                "Число должно быть положительным, введите корректное число: ");
    }

    private Integer createTotalBox(Console console) throws Break {
        return parseNumber(console, "Введите размер кассовых сборов (целое число больше нуля): ",
                "Размер кассовых сборов не может быть пустым, введите корректное число: ",
                "Кассовые сборы успешно добавлены!",
                Integer::parseInt, x -> x > 0,
                "Число должно быть положительным, введите корректное число: ");
    }

    private MovieGenre createGenre(Console console) throws Break {
        return parseEnum(console, "Введите жанр фильма (если его нет, нажмите Enter)",
                "Введите корректный жанр: ", "",
                "Жанр успешно добавлен!",
                MovieGenre.getGenre(), MovieGenre.class, false);
    }

    private MpaaRating createRating(Console console) throws Break {
        return parseEnum(console, "Введите MPAA рейтинг фильма (если его нет, нажмите Enter)",
                "Введите корректный MPAA рейтинг: ", "",
                "MPAA рейтинг успешно добавлен!",
                MpaaRating.getRatings(), MpaaRating.class, false);
    }

    private Person createDirector(Console console) throws Break {
        String name = parseString(console, "Введите имя человека: ",
                "У человека не может не быть имени, введите корректное имя: ",
                "Имя успешно добавлено!");
        Integer age = parseNumber(console, "Введите его рост (целое число больше нуля): ",
                "У человека не может не быть роста, введите корректный рост: ",
                "Рост успешно добавлен!", Integer::parseInt, x -> x > 0,
                "Число должно быть положительным, введите корректное число: ");
        Country nationality = parseEnum(console, "Введите его национальность: ",
                "Введите корректную национальность: ",
                "У человека не может не быть национальности, введите корректную страну: ",
                "Национальность успешно добавлена!",
                Country.getCountry(), Country.class, true);
        String request = "Введите координату %s в формате ";
        String emptyError = "Координата локации %s не может быть пустой, введите корректное число: ";
        String success = "Координата локации %s успешно добавлена!";
        Long x = parseNumber(console, String.format(request, "x") + "целого числа: ",
                String.format(emptyError, "x"), String.format(success, "x"),
                Long::parseLong, null, "");
        Integer y = parseNumber(console, String.format(request, "y") + "целого числа: ",
                String.format(emptyError, "y"), String.format(success, "y"),
                Integer::parseInt, null, "");
        Double z = parseNumber(console, String.format(request, "z") + "числа с плавающей точкой: ",
                String.format(emptyError, "z"), String.format(success, "z"),
                Double::parseDouble, null, "");
        return new Person(name, age, nationality, new Location(x, y, z));
    }


}

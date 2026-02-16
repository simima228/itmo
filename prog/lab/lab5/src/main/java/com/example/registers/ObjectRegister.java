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


    private <T> T parseField(Console console, String request, String empty, String success,
                             Function<String, T> parser, String error, String r1, String r2, Predicate<T> condition,
    String cond, String enumerate, boolean checkEmpty)
            throws Break {
        String line;
        console.println(request);
        if (!enumerate.isEmpty()){
            console.println(enumerate);
        }
        while (console.getScanner().hasNextLine()) {
            line = console.read().trim();
            if (line.equalsIgnoreCase("/exit")) {
                throw new Break();
            }

            if (line.isEmpty()){
                if (!checkEmpty){
                    return null;
                }
                console.println(empty);
                continue;
            }
            try {
                T result = parser.apply(line.replace(r1, r2));
                if (condition != null) {
                    if (!condition.test(result)) {
                        console.println(cond);
                        continue;
                    }
                }
                console.println(success);
                return result;
            }
            catch (IllegalArgumentException e) {
                console.println(error);
            }
        }
        throw new NoSuchElementException();
    }




    private String createMovieName(Console console) throws Break {
        return parseField(console, "Введите название фильма: ",
                "Название фильма не может быть пустым, введите корректное название: ",
                "Название фильма успешно добавлено!", Function.identity(), "", "", "",
                null, "", "", true);
    }

    private Coordinates createCoordinates(Console console) throws Break {
        return new Coordinates(parseField(console,
                "Введите координату x в формате числа с плавающей точкой: ",
                "Координата x не может быть пустой, введите корректное число: ",
                "Координата x успешно добавлена!",
                Float::parseFloat,"Введите корректное число: ", ",", ".", null, "", "", true),
                parseField(console,
                        "Введите координату y в формате целого числа: ",
                        "Координата y не может быть пустой, введите корректное число: ",
                        "Координата y успешно добавлена!",
                        Long::parseLong,"Введите корректное число: ", ",", ".", null, "", "", true));

    }

    private Long createOscars(Console console) throws Break {
        return parseField(console, "Введите количество Оскаров (целое число больше нуля): ",
                "Количество Оскаров не может быть пустым, введите корректное число: ",
                "Количество Оскаров успешно добавлено!",
                Long::parseLong, "Введите корректное число Оскаров: ", "", "", x -> x > 0,
                "Число должно быть положительным, введите корректное число: ", "", true);
    }

    private Integer createTotalBox(Console console) throws Break {
        return parseField(console, "Введите размер кассовых сборов (целое число больше нуля): ",
                "Размер кассовых сборов не может быть пустым, введите корректное число: ",
                "Кассовые сборы успешно добавлен!",
                Integer::parseInt, "Введите корректные кассовые сборы: ", "", "", x -> x > 0,
                "Число должно быть положительным, введите корректное число: ", "", true);
    }

    private MovieGenre createGenre(Console console) throws Break {
        return parseField(console,
                "Введите жанр фильма (если его нет, нажмите Enter)",
                "", "Жанр успешно добавлен!", x -> MovieGenre.valueOf(x.toUpperCase()),
                "Введите корректный жанр: ", "", "", null, "", MovieGenre.getGenre(),
                false);
    }

    private MpaaRating createRating(Console console) throws Break {
        return parseField(console, "Введите MPAA рейтинг фильма (если его нет, нажмите Enter)",
                "", "MPAA рейтинг успешно добавлен!", x -> MpaaRating.valueOf(x.toUpperCase()),
                "Введите корректный MPAA рейтинг: ", "", "", null, "", MpaaRating.getRatings(),
                false);
    }

    private Person createDirector(Console console) throws Break {
        return new Person(
                parseField(console, "Введите имя человека: ",
                        "У человека не может быть имени, введите корректное имя: ",
                        "Имя успешно добавлено!",
                        Function.identity(), "", "", "", null, "", "",
                        true),
                parseField(console, "Введите его рост (целое число больше нуля): ",
                        "У человека не может не быть роста, введите корректный рост: ",
                        "Рост успешно добавлен!",
                        Integer::parseInt, "Введите корректный рост: ", "", "",
                        x -> x > 0, "Число должно быть положительным, введите корректное число: ",
                        "", true),
                parseField(console, "Введите его национальность: ",
                        "У человека не может не быть национальности, введите корректную страну: ",
                        "Национальность успешно добавлена!",
                        x -> Country.valueOf(x.toUpperCase()), "Введите корректную национальность: ",
                        "", "", null, "", Country.getCountry(), true),
                new Location(
                        parseField(console, "Введите координату x в формате целого числа: ",
                                "Координата x не может быть пустой, введите корректное число: ",
                                "Координата x успешно добавлена!",
                                Long::parseLong, "Введите корректное число: ", "", "", null,
                                "", "", true),
                        parseField(console, "Введите координату y в формате целого числа: ",
                                "Координата y не может быть пустой, введите корректное число: ",
                                "Координата y успешно добавлена!",
                                Integer::parseInt, "Введите корректное число: ", "", "", null,
                                "", "", true),
                        parseField(console, "Введите координату z в формате числа с плавающей точкой: ",
                                "Координата z не может быть пустой, введите корректное число: ",
                                "Координата z успешно добавлена!",
                                Double::parseDouble, "Введите корректное число: ", ",", ".", null,
                                "", "", true)));
    }

}

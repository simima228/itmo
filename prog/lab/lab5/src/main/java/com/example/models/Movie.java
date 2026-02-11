package com.example.models;

import java.time.LocalDate;

public class Movie implements Comparable<Movie> {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long oscarsCount; //Значение поля должно быть больше 0, Поле не может быть null
    private Integer totalBoxOffice; //Поле не может быть null, Значение поля должно быть больше 0
    private MovieGenre genre; //Поле может быть null
    private MpaaRating mpaaRating; //Поле может быть null
    private Person director; //Поле может быть null

    public Movie(int id, String name, Coordinates coordinates, LocalDate creationDate,
                 Long oscarsCount, Integer totalBoxOffice, MovieGenre genre, MpaaRating mpaaRating, Person director) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarsCount = oscarsCount;
        this.totalBoxOffice = totalBoxOffice;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.director = director;
    }

    public Movie(int id, String name, Coordinates coordinates,
                 Long oscarsCount, Integer totalBoxOffice, MovieGenre genre, MpaaRating mpaaRating, Person director) {
        this(id, name, coordinates, LocalDate.now(), oscarsCount, totalBoxOffice, genre, mpaaRating, director);
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public java.time.LocalDate getCreationDate() {
        return creationDate;
    }

    public Long getOscarsCount() {
        return oscarsCount;
    }

    public Integer getTotalBoxOffice() {
        return totalBoxOffice;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public Person getDirector() {
        return director;
    }

    public static boolean checkOscars(Long count) {
        return count != null && count > 0;
    }

    public static boolean checkName(String checkName) {
        return checkName != null && !checkName.isEmpty();
    }

    public static boolean checkTotalBox(Integer box) {
        return box != null && box > 0;
    }

    @Override
    public String toString(){
        return "Фильм" + ": " + "[" +
                "id" + ": " + id +
                ", " + "name" + ": " + name +
                ", " + coordinates +
                ", " + "Дата создания" + ": " + creationDate +
                ", " + "Количество Оскаров" + ": " + oscarsCount +
                ", " + "Кассовые сборы" + ": " + totalBoxOffice +
                ", " + "Жанр" + ": " + (genre == null ? "Отсутствует": genre) +
                ", " + "Рейтинг MPAA" + ": " + (mpaaRating == null ? "Отсутствует": mpaaRating) +
                ", " + "Режиссер" + ": " + (director == null ? "Отсутствует": director) + "]";
    }

    @Override
    public int compareTo(Movie movie) {
        return this.getName().compareTo(movie.getName());
    }
}
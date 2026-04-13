package model;

import enums.MovieGenre;
import enums.MpaaRating;

import java.io.Serializable;
import java.time.LocalDate;

public class Movie implements Comparable<Movie>, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final Long oscarsCount; //Значение поля должно быть больше 0, Поле не может быть null
    private final Integer totalBoxOffice; //Поле не может быть null, Значение поля должно быть больше 0
    private final MovieGenre genre; //Поле может быть null
    private final MpaaRating mpaaRating; //Поле может быть null
    private final Person director; //Поле может быть null

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



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
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
        return this.getCoordinates().compareTo(movie.getCoordinates());
    }
}
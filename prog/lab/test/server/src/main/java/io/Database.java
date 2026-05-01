package io;

import enums.Country;
import enums.MovieGenre;
import enums.MpaaRating;
import etc.Cypher;
import model.Coordinates;
import model.Location;
import model.Movie;
import model.Person;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/studs";
    private static final String USER = "netu";
    private static final String PASSWORD = "netu";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static ArrayList<Movie> loadFromDatabase() throws SQLException {
        ArrayList<Movie> movies = new ArrayList<>();

        String sql = """
            SELECT m.id, m.name, m.creation_date, m.oscars, m.total, m.genre, m.rating, m.owner_id,
                   c.id as coord_id, c.x as coord_x, c.y as coord_y,
                   p.id as person_id, p.name as person_name, p.height, p.nationality,
                   l.id as location_id, l.x as loc_x, l.y as loc_y, l.z as loc_z
            FROM movies m
            JOIN coordinates c ON m.coords = c.id
            LEFT JOIN person p ON m.person = p.id
            LEFT JOIN location l ON p.locat = l.id
            WHERE m.deleted = FALSE
            ORDER BY m.id
       """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Movie movie = buildMovie(rs);
                movies.add(movie);
            }
        }

        return movies;
    }

    private static Movie buildMovie(ResultSet rs) throws SQLException {
        Coordinates coordinates = new Coordinates(
                rs.getFloat("coord_x"),
                rs.getLong("coord_y")
        );

        Person person = null;
        rs.getInt("person_id");
        if (!rs.wasNull()) {
            Location location = new Location(
                    rs.getLong("loc_x"),
                    rs.getInt("loc_y"),
                    rs.getDouble("loc_z")
            );
            Country nationality = null;
            if (rs.getString("nationality") != null) {
            nationality = Country.valueOf(rs.getString("nationality"));
            }

            person = new Person(
                    rs.getString("person_name"),
                    rs.getInt("height"),
                    nationality,
                    location
            );
        }

        MovieGenre genre = rs.getString("genre") != null
                ? MovieGenre.valueOf(rs.getString("genre"))
                : null;

        MpaaRating rating = rs.getString("rating") != null
                ? MpaaRating.valueOf(rs.getString("rating"))
                : null;

        return new Movie(rs.getInt("id"),
                rs.getString("name"),
                coordinates,
                rs.getDate("creation_date").toLocalDate(),
                rs.getLong("oscars"),
                rs.getInt("total"),
                genre,
                rating,
                person,
                getOwner(rs.getInt("owner_id"))
        );
    }

    public static String getOwner(int id) throws SQLException {
        String sql = "SELECT login FROM users WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("login");
            }
            return null;
        }
    }

    public static boolean insertUser(String login, String password, String salt) throws SQLException {
        String sql = "INSERT INTO users (login, password_hash, salt) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, password);
            ps.setString(3, salt);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                return false;
            }
            throw e;
        }
    }

    public static int verifyUser(String login, String password) throws SQLException {
        String sql = "SELECT id, password_hash, salt FROM users WHERE login = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                String storedHash = rs.getString("password_hash");
                String storedSalt = rs.getString("salt");

                if (Cypher.verifyPassword(password, storedHash, storedSalt)) {
                    return userId;
                }
                return -2;
            }
            return -1;
        }
    }

    public static int insertMovie(Movie movie, int userId) throws SQLException {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try {
                int coordinatesId = insertCoordinates(conn, movie.getCoordinates());
                Integer personId = insertPerson(conn, movie.getDirector());

                String sql = """
                    INSERT INTO movies (name, coords, oscars, total, genre, rating, person, owner_id)
                    VALUES (?, ?, ?, ?, ?::MOVIEGENRE, ?::MPAARATING, ?, ?)
                    RETURNING id
                """;

                int movieId;
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, movie.getName());
                    ps.setInt(2, coordinatesId);
                    ps.setLong(3, movie.getOscarsCount());
                    ps.setInt(4, movie.getTotalBoxOffice());
                    checkOnNull(movie, ps, 5, 6);

                    if (personId != null) {
                        ps.setInt(7, personId);
                    } else {
                        ps.setNull(7, Types.INTEGER);
                    }
                    ps.setInt(8, userId);

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        movieId = rs.getInt("id");
                    } else {
                        throw new SQLException("Не удалось создать фильм.");
                    }
                }

                conn.commit();
                return movieId;

            } catch (Exception e) {
                conn.rollback();
                throw new SQLException("Ошибка при создании фильма: " + e.getMessage(), e);
            }
        }
    }

    private static int insertCoordinates(Connection conn, Coordinates coordinates) throws SQLException {
        String sql = "INSERT INTO coordinates (x, y) VALUES (?, ?) RETURNING id";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setFloat(1, coordinates.x());
            ps.setLong(2, coordinates.y());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new SQLException("Не удалось создать координаты.");
        }
    }

    private static Integer insertPerson(Connection conn, Person person) throws SQLException {
        if (person == null) {
            return null;
        }

        int locationId = insertLocation(conn, person.getLocationAsLocation());

        String sql = "INSERT INTO person (name, height, nationality, locat) VALUES (?, ?, ?::COUNTRY, ?) RETURNING id";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, person.getName());
            ps.setInt(2, person.getHeight());
            ps.setString(3, person.getNationality().toString());
            ps.setInt(4, locationId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new SQLException("Не удалось создать человека.");
        }
    }

    private static int insertLocation(Connection conn, Location location) throws SQLException {
        String sql = "INSERT INTO location (x, y, z) VALUES (?, ?, ?) RETURNING id";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, location.x());
            ps.setInt(2, location.y());
            ps.setDouble(3, location.z());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new SQLException("Не удалось создать локацию.");
        }
    }

    public static boolean deleteMovie(int id, int userId) throws SQLException {
        String sql = "UPDATE movies SET deleted = TRUE WHERE id = ? AND owner_id = ? AND deleted = FALSE";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, userId);

            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;
        }

    }
    public static void deleteMovies(int userId) throws SQLException {
        String sql = "UPDATE movies SET deleted = TRUE WHERE owner_id = ? AND deleted = FALSE";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);

            ps.executeUpdate();
        }

    }

    public static boolean updateMovie(int movieId, Movie newMovie, int userId) throws SQLException {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try {
                int coordsId, personId;
                String getIdsSql = "SELECT coords, person FROM movies WHERE id = ? AND owner_id = ? AND deleted = FALSE";

                try (PreparedStatement ps = conn.prepareStatement(getIdsSql)) {
                    ps.setInt(1, movieId);
                    ps.setInt(2, userId);
                    ResultSet rs = ps.executeQuery();

                    if (!rs.next()) {
                        conn.rollback();
                        return false;
                    }
                    coordsId = rs.getInt("coords");
                    personId = rs.getInt("person");
                }

                updateCoordinates(conn, coordsId, newMovie.getCoordinates());

                Person person = newMovie.getDirector();
                if (person != null) {
                    updatePerson(conn, personId, person);
                }

                updateMovieData(conn, movieId, newMovie);

                conn.commit();
                return true;

            } catch (Exception e) {
                conn.rollback();
                throw new SQLException("Ошибка при обновлении фильма: " + e.getMessage(), e);
            }
        }
    }

    private static void updateCoordinates(Connection conn, int coordsId, Coordinates coords) throws SQLException {
        String sql = "UPDATE coordinates SET x = ?, y = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setFloat(1, coords.x());
            ps.setLong(2, coords.y());
            ps.setInt(3, coordsId);
            ps.executeUpdate();
        }
    }

    private static void updatePerson(Connection conn, int personId, Person person) throws SQLException {
        int locationId;
        String getLocationSql = "SELECT locat FROM person WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(getLocationSql)) {
            ps.setInt(1, personId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new SQLException("Режиссер с id = " + personId + " не найден.");
            }
            locationId = rs.getInt("locat");
        }

        updateLocation(conn, locationId, person.getLocationAsLocation());

        String updatePersonSql = "UPDATE person SET name = ?, height = ?, nationality = ?::COUNTRY WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(updatePersonSql)) {
            ps.setString(1, person.getName());
            ps.setInt(2, person.getHeight());
            ps.setString(3, person.getNationality().toString());
            ps.setInt(4, personId);
            ps.executeUpdate();
        }
    }

    private static void updateLocation(Connection conn, int locationId, Location location) throws SQLException {
        String sql = "UPDATE location SET x = ?, y = ?, z = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, location.x());
            ps.setInt(2, location.y());
            ps.setDouble(3, location.z());
            ps.setInt(4, locationId);
            ps.executeUpdate();
        }
    }

    private static void updateMovieData(Connection conn, int movieId, Movie movie) throws SQLException {
        String sql = """
            UPDATE movies
            SET name = ?, oscars = ?, total = ?, genre = ?::MOVIEGENRE, rating = ?::MPAARATING
            WHERE id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, movie.getName());
            ps.setLong(2, movie.getOscarsCount());
            ps.setInt(3, movie.getTotalBoxOffice());
            checkOnNull(movie, ps, 4, 5);
            ps.setInt(6, movieId);
            ps.executeUpdate();
        }
    }

    private static void checkOnNull(Movie movie, PreparedStatement ps, int index1, int index2) throws SQLException {
        if (movie.getGenre() == null) {
            ps.setNull(index1, Types.VARCHAR);
        }
        else {
            ps.setString(index1, movie.getGenre().toString());
        }
        if (movie.getMpaaRating() == null) {
            ps.setNull(index2, Types.VARCHAR);
        }
        else {
            ps.setString(index2, movie.getMpaaRating().toString());
        }
    }
}
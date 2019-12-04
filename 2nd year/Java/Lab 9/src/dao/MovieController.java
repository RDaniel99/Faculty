package dao;
import database.Database;
import model.Actor;
import model.Director;
import model.Movie;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MovieController {
    public void create(Movie movie) throws SQLException {
        Connection conn = Database.getConnection();
        int id=getId();
        movie.setId(id);
        try (PreparedStatement statement = conn.prepareStatement("insert into movies values (?,?,?)")) {
            statement.setInt(1, movie.getId());
            statement.setString(2,movie.getName());
            statement.setInt(3,movie.getDirector().getId());
            statement.executeUpdate();
        }
    }

    private int getId() throws SQLException {
        Connection conn = Database.getConnection();
        try (Statement statement = conn.createStatement();
             ResultSet movieRecord = statement.executeQuery("select count(*) from movies")) {
            if (!movieRecord.next()) return 1;
            return movieRecord.getInt(1)+1;
        }
    }

    public Movie findByName(String name) throws SQLException {
        Connection conn = Database.getConnection();
        try (Statement statement = conn.createStatement();
             ResultSet movieRecord = statement.executeQuery("select * from movies where name like '%" + name + "%'")) {
            if (!movieRecord.next()) return null;
            return getMovie(statement, movieRecord);
        }
    }

    public Movie findById(int id) throws SQLException {
        Connection conn = Database.getConnection();
        try (Statement statement = conn.createStatement();
             ResultSet movieRecord = statement.executeQuery("select * from movies where id="+id)) {
            if (!movieRecord.next()) return null;
            return getMovie(statement, movieRecord);
        }
    }

    private Movie getMovie(Statement statement, ResultSet movieRecord) throws SQLException {
        int movieId=movieRecord.getInt(1);
        String movieName=movieRecord.getString(2);
        try (ResultSet director = statement.executeQuery("select * from persons where id="+movieRecord.getInt(3))){
            if (!director.next()) return null;
            return new Movie(movieId,movieName,new Director(director.getInt(1),director.getString(2)));
        }
    }

    public List<Movie> findAll() throws SQLException {
        Connection conn = Database.getConnection();
        List<Movie> moviesList=new ArrayList<>();
        try (Statement statement = conn.createStatement();
             ResultSet movieRecord = statement.executeQuery("select * from movies")) {
            while (movieRecord.next()) {
                moviesList.add(getMovie(statement,movieRecord));
            }
            return moviesList;
        }
    }

    public List<Actor> getActors(Movie movie) throws SQLException {
        List<Actor> actorsList=new ArrayList<>();
        Connection conn=Database.getConnection();
        try (Statement statement = conn.createStatement();
             ResultSet actorRecord = statement.executeQuery("select actor_id from movie_actors where id=" + movie.getId())) {
            while (actorRecord.next()) {
                try (ResultSet personRecord=statement.executeQuery("select * from persons where id="+actorRecord.getInt(1))) {
                    if (!personRecord.next()) break;
                    actorsList.add(new Actor(personRecord.getInt(1),personRecord.getString(2)));
                }
            }
            return actorsList;
        }
    }

    public void add(Movie movie, Actor actor) throws SQLException {
        Connection conn = Database.getConnection();
        try (PreparedStatement statement = conn.prepareStatement("insert into movie_actors values (?,?)")) {
            statement.setInt(1,movie.getId());
            statement.setInt(2,actor.getId());
            statement.executeUpdate();
        }
    }
}

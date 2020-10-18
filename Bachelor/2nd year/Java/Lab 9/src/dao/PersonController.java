package dao;

import database.Database;
import model.Actor;
import model.Director;
import model.Movie;
import model.Person;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonController {
    public void create(Person person) throws SQLException {
        Connection con = Database.getConnection();
        person.setId(this.getId());
        try (PreparedStatement statement = con.prepareStatement("insert into persons values (?,?,?,?)")) {
            statement.setInt(1, person.getId());
            statement.setString(2,person.getName());
            statement.setInt(3,person.getClass().toString().equals("class model.Actor")?1:0);
            statement.setInt(4,person.getClass().toString().equals("class model.Director")?1:0);
            statement.executeUpdate();
        }
    }

    private int getId() throws SQLException {
        Connection conn = Database.getConnection();
        try (Statement statement = conn.createStatement();
             ResultSet personRecord = statement.executeQuery("select count(*) from persons")) {
            if (!personRecord.next()) return 1;
            return personRecord.getInt(1)+1;
        }
    }

    public Person findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement statement = con.createStatement();
             ResultSet personRecord = statement.executeQuery("select * from persons where name like '%" + name + "%'")) {
            if (!personRecord.next()) return null;
            if (personRecord.getInt(3)==1) return new Actor(personRecord.getInt(1),personRecord.getString(2));
            return new Director(personRecord.getInt(1),personRecord.getString(2));
        }
    }
    public Person findById(int id) throws SQLException {
        Connection conn = Database.getConnection();
        try (Statement statement = conn.createStatement();
             ResultSet personRecord = statement.executeQuery("select * from persons where id=" + id)) {
            if (!personRecord.next()) return null;
            if (personRecord.getInt(3)==1) return new Actor(personRecord.getInt(1),personRecord.getString(2));
            return new Director(personRecord.getInt(1),personRecord.getString(2));
        }
    }
    public List<Person> findAll() throws SQLException {
        Connection con = Database.getConnection();
        List<Person> personsList=new ArrayList<>();
        try (Statement statement = con.createStatement();
             ResultSet personRecord = statement.executeQuery("select * from persons")) {
            while (personRecord.next()) {
                if (personRecord.getInt(3)==1) personsList.add(new Actor(personRecord.getInt(1),personRecord.getString(2)));
                else personsList.add(new Director(personRecord.getInt(1),personRecord.getString(2)));
            }
            return personsList;
        }
    }

    public List<Movie> getMovies(Actor actor) throws SQLException {
        List<Movie> moviesList=new ArrayList<>();
        Connection conn= Database.getConnection();
        try (Statement statement = conn.createStatement();
             ResultSet movieIdRecord = statement.executeQuery("select movie_id from movie_actors where actor_id=" + actor.getId())) {
            while (movieIdRecord.next()) {
                try (ResultSet movieRecord=statement.executeQuery("select * from movies where id="+movieIdRecord.getInt(1))) {
                    if (!movieRecord.next()) break;
                    try (ResultSet director=statement.executeQuery("select * from persons where id="+movieRecord.getInt(3))){
                        if (!director.next()) break;
                        moviesList.add(new Movie(movieRecord.getInt(1),movieRecord.getString(2),new Director(director.getInt(1),director.getString(2))));
                    }

                }
            }
            return moviesList;
        }
    }

    public List<Movie> getMovies(Director director) throws SQLException {
        List<Movie> moviesList=new ArrayList<>();
        Connection conn= Database.getConnection();
        try (Statement statement = conn.createStatement();
             ResultSet movieRecord = statement.executeQuery("select * from movies where director_id=" + director.getId())) {
            while (movieRecord.next()) {
                moviesList.add(new Movie(movieRecord.getInt(1),movieRecord.getString(2),director));
            }
            return moviesList;
        }
    }
}

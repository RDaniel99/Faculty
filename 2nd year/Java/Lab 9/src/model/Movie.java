package model;

public class Movie {
    private int id;
    private String name;
    private Director director;

    public Movie(int id, String name, Director director) {
        this.setDirector(director);
        this.setName(name);
        this.setId(id);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Director getDirector() {
        return director;
    }

    private void setDirector(Director director) {
        this.director = director;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

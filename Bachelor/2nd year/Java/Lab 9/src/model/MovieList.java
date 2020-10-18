package model;

import java.util.ArrayList;
import java.util.List;

public class MovieList {
    private List<Movie> list;
    private String name;

    public MovieList(String name){
        this.setName(name);
        list = new ArrayList<>();
    }

    public List<Movie> getList() {
        return list;
    }

    public void setList(List<Movie> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(Movie movie){
        list.add(movie);
    }
}

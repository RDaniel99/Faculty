package model;

import database.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Director extends Person {
    public Director(int id, String name) {
        this.setName(name);
        this.setId(id);
    }

}

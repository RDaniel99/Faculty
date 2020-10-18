package database;

import java.sql.*;

public class Database {
    private static Connection connection = null;
    private Database() { }
    public static Connection getConnection() {
        if (connection == null) {
            createConnection();
        }
        return connection;
    }

    public static void createConnection(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection=DriverManager.getConnection(Constants.URL,Constants.USER,Constants.PASSWORD);
        }
        catch (Exception except){
            System.out.println(except.getMessage());
        }
    }

    public static void closeConnection(){
        try {
            connection.close();
        }
        catch (SQLException SQLExcept){
            System.out.println(SQLExcept.getMessage());
        }
    }

    public static void commit() {
        try {
            connection.commit();
        } catch (SQLException SQLExcept) {
            System.out.println(SQLExcept.getMessage());
        }
    }

    public static void rollback() {
        try {
            connection.rollback();
        } catch (SQLException SQLExcept) {
            System.out.println(SQLExcept.getMessage());
        }
    }
}
package nsu.medvedev.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    public Connection connectToDB() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        final String url = "jdbc:postgresql://localhost:5432/aston_db";
        final String user = "admin";
        final String password = "admin";
        return DriverManager.getConnection(url, user, password);
    }
}

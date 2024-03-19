package nsu.medvedev.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    public Connection connectToDB() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER_CLASS_NAME);
        final String url = "jdbc:postgresql://localhost:5432/aston_db";
        final String user = "admin";
        final String password = "admin";
        return DriverManager.getConnection(url, user, password);
    }
}

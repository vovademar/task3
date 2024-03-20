package nsu.medvedev.database;

import nsu.medvedev.exception.DataBaseException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
    static {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            throw new DataBaseException("Failed to register PostgresSQL JDBC driver: " + e.getMessage());
        }
    }

    public Connection connectToDB() throws SQLException {
        Properties properties = loadProperties();
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }

    public Connection connectToDB(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private Properties loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Unable to find config.properties");
            }
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (IOException e) {
            throw new DataBaseException("Failed to load config.properties: " + e.getMessage());
        }
    }

}

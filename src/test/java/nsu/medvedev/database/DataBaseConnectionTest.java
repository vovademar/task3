package nsu.medvedev.database;

import nsu.medvedev.database.DataBaseConnection;
import nsu.medvedev.exception.DataBaseException;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseConnectionTest {

    @Test
    void testConnectToDB_Success() {
        DataBaseConnection dbConnection = new DataBaseConnection();
        try {
            Connection connection = dbConnection.connectToDB();
            assertNotNull(connection);
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            fail("Unexpected SQLException: " + e.getMessage());
        }
    }

    @Test
    void testConnectToDB_WithInvalidCredentials() {
        DataBaseConnection dbConnection = new DataBaseConnection();
        assertThrows(SQLException.class, () -> {
            dbConnection.connectToDB("jdbc:postgresql://localhost:5432/testdb", "invalid_user", "invalid_password");
        });
    }

    @Test
    void testConnectToDB_WithNullURL() {
        DataBaseConnection dbConnection = new DataBaseConnection();
        assertThrows(SQLException.class, () -> {
            dbConnection.connectToDB(null, "user", "password");
        });
    }

    @Test
    void testConnectToDB_WithNullUser() {
        DataBaseConnection dbConnection = new DataBaseConnection();
        assertThrows(SQLException.class, () -> {
            dbConnection.connectToDB("jdbc:postgresql://localhost:5432/testdb", null, "password");
        });
    }

    @Test
    void testConnectToDB_WithNullPassword() {
        DataBaseConnection dbConnection = new DataBaseConnection();
        assertThrows(SQLException.class, () -> {
            dbConnection.connectToDB("jdbc:postgresql://localhost:5432/testdb", "user", null);
        });
    }

    @Test
    void testConnectToDB_WithInvalidURL() {
        DataBaseConnection dbConnection = new DataBaseConnection();
        assertThrows(SQLException.class, () -> {
            dbConnection.connectToDB("jdbc:mysql://localhost:3306/testdb", "user", "password");
        });
    }
}

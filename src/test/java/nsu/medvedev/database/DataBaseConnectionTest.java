package nsu.medvedev.database;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DataBaseConnectionTest {


    @Test
    void testSuccessfulConnection() {
        DataBaseConnection dbConnection = new DataBaseConnection();
        assertDoesNotThrow(dbConnection::connectToDB);
    }


}

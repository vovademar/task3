package nsu.medvedev.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DataBaseExceptionTest {
    @Test
    void testConstructorWithMessage() {
        String errorMessage = "Test error message";
        DataBaseException exception = new DataBaseException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testConstructorWithNullMessage() {
        DataBaseException exception = new DataBaseException(null);
        assertNull(exception.getMessage());
    }

    @Test
    void testConstructorWithEmptyMessage() {
        DataBaseException exception = new DataBaseException("");
        assertEquals("", exception.getMessage());
    }
}
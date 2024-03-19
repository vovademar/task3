package nsu.medvedev.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DaoExceptionTest {
    @Test
    void testConstructorWithMessage() {
        String errorMessage = "Test error message";
        DaoException exception = new DaoException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testConstructorWithNullMessage() {
        DaoException exception = new DaoException(null);
        assertNull(exception.getMessage());
    }

    @Test
    void testConstructorWithEmptyMessage() {
        DaoException exception = new DaoException("");
        assertEquals("", exception.getMessage());
    }

}
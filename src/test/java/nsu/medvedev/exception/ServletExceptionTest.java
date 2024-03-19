package nsu.medvedev.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServletExceptionTest {
    @Test
    void testConstructorWithMessage() {
        String errorMessage = "Test error message";
        ServletException exception = new ServletException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testConstructorWithNullMessage() {
        ServletException exception = new ServletException(null);
        assertNull(exception.getMessage());
    }

    @Test
    void testConstructorWithEmptyMessage() {
        ServletException exception = new ServletException("");
        assertEquals("", exception.getMessage());
    }

}
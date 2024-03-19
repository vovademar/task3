package nsu.medvedev.entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    @Test
    void testSettersAndGetters() {
        Author author = new Author();
        author.setName("Lermontov");
        author.setId(1L);
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L,"Title", author));
        author.setBooks(books);

        assertEquals(1L,author.getId());
        assertEquals("Lermontov", author.getName());
        assertEquals(books, author.getBooks());
    }

    @Test
    void testConstructor() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        Author author = new Author(2L, "Dostoevskiy", books);

        assertEquals(2L, author.getId());
        assertEquals("Dostoevskiy", author.getName());
    }
}
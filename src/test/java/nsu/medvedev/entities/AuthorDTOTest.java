package nsu.medvedev.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class AuthorDTOTest {
    private AuthorDTO author;

    @BeforeEach
    public void setUp() {
        author = new AuthorDTO();
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        author.setId(id);
        assertEquals(id, author.getId());
    }

    @Test
    void testSetAndGetName() {
        String name = "John Doe";
        author.setName(name);
        assertEquals(name, author.getName());
    }

    @Test
    void testSetAndGetBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        author.setBooks(books);
        assertEquals(books, author.getBooks());
    }

    @Test
    void testEmptyConstructor() {
        AuthorDTO emptyAuthor = new AuthorDTO();
        assertNull(emptyAuthor.getId());
        assertNull(emptyAuthor.getName());
        assertNull(emptyAuthor.getBooks());
    }

    @Test
    void testParameterizedConstructor() {
        Long id = 1L;
        String name = "Jane Smith";
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        AuthorDTO paramAuthor = new AuthorDTO(id, name, books);
        assertEquals(id, paramAuthor.getId());
        assertEquals(name, paramAuthor.getName());
        assertEquals(books, paramAuthor.getBooks());
    }

}
package nsu.medvedev.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BookDTOTest {

    private BookDTO book;
    private Author author;

    @BeforeEach
    public void setUp() {
        author = new Author();
        book = new BookDTO();
    }

    @Test
    public void testSetAndGetId() {
        Long id = 1L;
        book.setId(id);
        assertEquals(id, book.getId());
    }

    @Test
    public void testSetAndgetTitle() {
        String title = "Book Title";
        book.setTitle(title);
        assertEquals(title, book.getTitle());
    }

    @Test
    public void testSetAndGetAuthor() {
        book.setAuthor(author);
        assertEquals(author, book.getAuthor());
    }

    @Test
    public void testSetAndGetShops() {
        List<Shop> shops = new ArrayList<>();
        shops.add(new Shop());
        shops.add(new Shop());
        book.setShops(shops);
        assertEquals(shops, book.getShops());
    }

    @Test
    public void testEmptyConstructor() {
        BookDTO emptyBook = new BookDTO();
        assertNull(emptyBook.getId());
        assertNull(emptyBook.getTitle());
        assertNull(emptyBook.getAuthor());
        assertNull(emptyBook.getShops());
    }

    @Test
    public void testParameterizedConstructor() {
        Long id = 1L;
        String title = "Book Title";
        List<Shop> shops = new ArrayList<>();
        shops.add(new Shop());
        Author author = new Author();
        BookDTO paramBook = new BookDTO(id, title, author, shops);
        assertEquals(id, paramBook.getId());
        assertEquals(title, paramBook.getTitle());
        assertEquals(author, paramBook.getAuthor());
        assertEquals(shops, paramBook.getShops());
    }

    @Test
    public void testOneMoreParameterizedConstructor() {
        Long id = 1L;
        String title = "Book Title";
        Author author = new Author();
        BookDTO paramBook = new BookDTO(id, title, author);
        assertEquals(id, paramBook.getId());
        assertEquals(title, paramBook.getTitle());
        assertEquals(author, paramBook.getAuthor());
    }
}


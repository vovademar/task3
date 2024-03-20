package nsu.medvedev.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BookTest {

    private Book book;
    private Author author;

    @BeforeEach
    public void setUp() {
        author = new Author();
        book = new Book();
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        book.setId(id);
        assertEquals(id, book.getId());
    }

    @Test
    void testSetAndgetTitle() {
        String title = "Book Title";
        book.setTitle(title);
        assertEquals(title, book.getTitle());
    }

    @Test
    void testSetAndGetAuthor() {
        book.setAuthor(author);
        assertEquals(author, book.getAuthor());
    }

    @Test
    void testSetAndGetShops() {
        List<Shop> shops = new ArrayList<>();
        shops.add(new Shop());
        shops.add(new Shop());
        book.setShops(shops);
        assertEquals(shops, book.getShops());
    }

    @Test
    void testEmptyConstructor() {
        Book emptyBook = new Book();
        assertNull(emptyBook.getId());
        assertNull(emptyBook.getTitle());
        assertNull(emptyBook.getAuthor());
        assertNull(emptyBook.getShops());
    }

    @Test
    void testParameterizedConstructor() {
        Long id = 1L;
        String title = "Book Title";
        List<Shop> shops = new ArrayList<>();
        shops.add(new Shop());
        Author author = new Author();
        Book paramBook = new Book(id, title, author, shops);
        assertEquals(id, paramBook.getId());
        assertEquals(title, paramBook.getTitle());
        assertEquals(author, paramBook.getAuthor());
        assertEquals(shops, paramBook.getShops());
    }
}

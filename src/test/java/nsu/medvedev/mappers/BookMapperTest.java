package nsu.medvedev.mappers;

import nsu.medvedev.entities.Author;
import nsu.medvedev.entities.Book;
import nsu.medvedev.entities.BookDTO;
import nsu.medvedev.entities.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    private Book book;
    private BookDTO bookDTO;

    @BeforeEach
    public void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        Author author = new Author();
        author.setId(1L);
        author.setName("Author Name");
        book.setAuthor(author);
        List<Shop> shops = new ArrayList<>();
        shops.add(new Shop());
        shops.add(new Shop());
        book.setShops(shops);

        bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Book Title");
        bookDTO.setAuthor(author);
        bookDTO.setShops(shops);
    }

    @Test
    public void testToBookDTO() {
        BookDTO mappedBookDTO = BookMapper.toBookDTO(book);
        assertEquals(bookDTO.getId(), mappedBookDTO.getId());
        assertEquals(bookDTO.getTitle(), mappedBookDTO.getTitle());
        assertEquals(bookDTO.getAuthor(), mappedBookDTO.getAuthor());
        assertEquals(bookDTO.getShops(), mappedBookDTO.getShops());
    }

    @Test
    public void testToBookEntity() {
        Book mappedBook = BookMapper.toBookEntity(bookDTO);
        assertEquals(book.getId(), mappedBook.getId());
        assertEquals(book.getTitle(), mappedBook.getTitle());
        assertEquals(book.getAuthor(), mappedBook.getAuthor());
        assertEquals(book.getShops(), mappedBook.getShops());
    }

}
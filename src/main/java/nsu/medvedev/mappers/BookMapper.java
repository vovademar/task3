package nsu.medvedev.mappers;

import nsu.medvedev.entities.Book;
import nsu.medvedev.entities.BookDTO;

public class BookMapper {
    public static BookDTO toBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setShopsDTO(book.getShops());
        bookDTO.setTitle(book.getTitle());
        return bookDTO;
    }

    public static Book toBookEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setAuthor(bookDTO.getAuthor());
        book.setId(bookDTO.getId());
        book.setShops(bookDTO.getShopsDTO());
        book.setTitle(bookDTO.getTitle());
        return book;
    }

}

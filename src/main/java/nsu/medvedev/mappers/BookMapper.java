package nsu.medvedev.mappers;

import nsu.medvedev.entities.Book;
import nsu.medvedev.entities.BookDTO;

public class BookMapper {
    private BookMapper() {
    }

    public static BookDTO toBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setShops(book.getShops());
        bookDTO.setTitle(book.getTitle());
        return bookDTO;
    }

    public static Book toBookEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setAuthor(bookDTO.getAuthor());
        book.setId(bookDTO.getId());
        book.setShops(bookDTO.getShops());
        book.setTitle(bookDTO.getTitle());
        return book;
    }

}

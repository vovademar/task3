package nsu.medvedev.mappers;

import nsu.medvedev.entities.Author;
import nsu.medvedev.entities.AuthorDTO;
import nsu.medvedev.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorMapperTest {
    private Author author;
    private AuthorDTO authorDTO;

    @BeforeEach
    public void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("Author Name");
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        author.setBooks(books);

        authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("Author Name");
        authorDTO.setBooks(books);
    }

    @Test
    void testToAuthorDTO() {
        AuthorDTO mappedAuthorDTO = AuthorMapper.toAuthorDTO(author);
        assertEquals(authorDTO.getId(), mappedAuthorDTO.getId());
        assertEquals(authorDTO.getName(), mappedAuthorDTO.getName());
        assertEquals(authorDTO.getBooks(), mappedAuthorDTO.getBooks());
    }

    @Test
    void testToAuthorModel() {
        Author mappedAuthor = AuthorMapper.toAuthorModel(authorDTO);
        assertEquals(author.getId(), mappedAuthor.getId());
        assertEquals(author.getName(), mappedAuthor.getName());
        assertEquals(author.getBooks(), mappedAuthor.getBooks());
    }

}
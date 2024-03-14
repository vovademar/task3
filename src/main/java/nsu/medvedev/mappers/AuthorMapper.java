package nsu.medvedev.mappers;

import nsu.medvedev.entities.Author;
import nsu.medvedev.entities.AuthorDTO;

public class AuthorMapper {
    public static AuthorDTO toAuthorDTO(Author author){
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setBooksDTO(author.getBooks());
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        return authorDTO;
    }

    public static Author toAuthorModel(AuthorDTO authorDTO){
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setBooks(authorDTO.getBooksDTO());
        author.setName(authorDTO.getName());
        return author;
    }
}

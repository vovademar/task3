package nsu.medvedev.entities;

import java.util.List;

public class AuthorDTO {
    private Long id;
    private String name;
    private List<Book> booksDTO;

    public AuthorDTO(Long id, String name, List<Book> booksDTO) {
        this.id = id;
        this.name = name;
        this.booksDTO = booksDTO;
    }

    public AuthorDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooksDTO() {
        return booksDTO;
    }

    public void setBooksDTO(List<Book> booksDTO) {
        this.booksDTO = booksDTO;
    }
}

package nsu.medvedev.entities;

import java.util.ArrayList;
import java.util.List;

public class AuthorDTO {
    private Long id;
    private String name;
    private List<Book> books;

    public AuthorDTO(Long id, String name, List<Book> books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }

    public AuthorDTO() {
    }

    public AuthorDTO(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }
    public AuthorDTO(long id, String name) {
        this.id = id;
        this.name = name;
        this.books = new ArrayList<>();
    }

    public AuthorDTO(String name, List<Book> books) {
        this.name = name;
        this.books = books;
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

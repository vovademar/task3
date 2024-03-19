package nsu.medvedev.entities;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private Long id;
    private String name;
    private List<Book> books;

    public Shop() {
    }

    public Shop(Long id, String name) {
        this.id = id;
        this.name = name;
        this.books = new ArrayList<>();
    }

    public Shop(Long id, String name, List<Book> books) {
        this.id = id;
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

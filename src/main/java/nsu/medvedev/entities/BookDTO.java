package nsu.medvedev.entities;

import java.util.ArrayList;
import java.util.List;

public class BookDTO {
    private Long id;
    private String title;

    private List<Shop> shops;

    private Author author;

    public Long getId() {
        return id;
    }

    public BookDTO() {
    }

    public BookDTO(Long id, String title, Author author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.shops = new ArrayList<>();
    }

    public BookDTO(Long id, String title, Author author, List<Shop> shops) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.shops = shops;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}


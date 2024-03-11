package nsu.medvedev.entities;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private Long id;
    private String title;
    private List<Shop> shops;
    private Long authorId;


    public Book() {
    }

    public Book(Long id, String title, Long author) {
        this.id = id;
        this.title = title;
        this.authorId = author;
        this.shops = new ArrayList<>();
    }


    public Long getId() {
        return id;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}

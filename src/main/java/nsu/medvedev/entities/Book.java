package nsu.medvedev.entities;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private Long id;
    private String title;
    private Author author;
    private List<Shop> shops;


    public Book() {
    }

    public Book(Long id, String title, Author author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.shops = new ArrayList<>();
    }

    public Book(Long id, String title, Author author, List<Shop> shops) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.shops = shops;
    }

    public Book(Long id, String title) {
        this.id = id;
        this.title = title;
        this.author = null;
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


    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}

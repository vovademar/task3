package nsu.medvedev.entities;

import java.util.List;

public class BookDTO {
    private Long id;
    private String title;

    private List<Shop> shopsDTO;

    private Author author;

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

    public List<Shop> getShopsDTO() {
        return shopsDTO;
    }

    public void setShopsDTO(List<Shop> shopsDTO) {
        this.shopsDTO = shopsDTO;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}


package nsu.medvedev.entities;

public class Book {
    private Long id;
    private String title;
    private Long author_id;

    public Book() {
    }

    public Book(Long id, String title, Long author_id) {
        this.id = id;
        this.title = title;
        this.author_id = author_id;
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

    public Long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }
}

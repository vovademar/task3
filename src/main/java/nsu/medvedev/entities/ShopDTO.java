package nsu.medvedev.entities;

import java.util.List;

public class ShopDTO {
    private Long id;
    private String name;

    private List<Book> booksDTO;

    public ShopDTO() {
    }

    public ShopDTO(Long id, String name, List<Book> bookDTO) {
        this.id = id;
        this.name = name;
        this.booksDTO = bookDTO;
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

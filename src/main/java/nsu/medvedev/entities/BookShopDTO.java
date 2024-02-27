package nsu.medvedev.entities;

// Класс BookShopDTO
public class BookShopDTO {
    private Long bookId;
    private Long shopId;

    // Конструкторы
    public BookShopDTO() {
    }

    public BookShopDTO(Long bookId, Long shopId) {
        this.bookId = bookId;
        this.shopId = shopId;
    }

    // Геттеры и сеттеры
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}


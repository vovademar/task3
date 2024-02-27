package nsu.medvedev.entities;

public class BookShopDTO {
    private Long bookId;
    private Long shopId;

    public BookShopDTO() {
    }

    public BookShopDTO(Long bookId, Long shopId) {
        this.bookId = bookId;
        this.shopId = shopId;
    }

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


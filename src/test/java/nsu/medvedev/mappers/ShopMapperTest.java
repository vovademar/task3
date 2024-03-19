package nsu.medvedev.mappers;

import nsu.medvedev.entities.Book;
import nsu.medvedev.entities.Shop;
import nsu.medvedev.entities.ShopDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShopMapperTest {
    private Shop shop;
    private ShopDTO shopDTO;

    @BeforeEach
    public void setUp() {
        shop = new Shop();
        shop.setId(1L);
        shop.setName("Shop Name");
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        shop.setBooks(books);

        shopDTO = new ShopDTO();
        shopDTO.setId(1L);
        shopDTO.setName("Shop Name");
        shopDTO.setBooks(books);
    }

    @Test
    void testToShopDto() {
        ShopDTO mappedShopDTO = ShopMapper.toShopDto(shop);
        assertEquals(shopDTO.getId(), mappedShopDTO.getId());
        assertEquals(shopDTO.getName(), mappedShopDTO.getName());
        assertEquals(shopDTO.getBooks(), mappedShopDTO.getBooks());
    }

    @Test
    void testToShopEntity() {
        Shop mappedShop = ShopMapper.toShopEntity(shopDTO);
        assertEquals(shop.getId(), mappedShop.getId());
        assertEquals(shop.getName(), mappedShop.getName());
        assertEquals(shop.getBooks(), mappedShop.getBooks());
    }

}
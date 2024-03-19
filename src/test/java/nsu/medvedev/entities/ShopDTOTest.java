package nsu.medvedev.entities;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ShopDTOTest {

    private ShopDTO shop;

    @BeforeEach
    public void setUp() {
        shop = new ShopDTO();
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        shop.setId(id);
        assertEquals(id, shop.getId());
    }

    @Test
    void testSetAndgetName() {
        String name = "Shop Name";
        shop.setName(name);
        assertEquals(name, shop.getName());
    }

    @Test
    void testSetAndGetBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        shop.setBooks(books);
        assertEquals(books, shop.getBooks());
    }

    @Test
    void testEmptyConstructor() {
        Shop emptyShop = new Shop();
        assertNull(emptyShop.getId());
        assertNull(emptyShop.getName());
        assertNull(emptyShop.getBooks());
    }

    @Test
    void testParameterizedConstructor() {
        Long id = 1L;
        String name = "Shop Name";
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        ShopDTO paramShop = new ShopDTO(id, name, books);
        assertEquals(id, paramShop.getId());
        assertEquals(name, paramShop.getName());
        assertEquals(books, paramShop.getBooks());
    }
}


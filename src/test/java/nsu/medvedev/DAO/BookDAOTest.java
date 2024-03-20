package nsu.medvedev.DAO;

import nsu.medvedev.database.DataBaseConnection;
import nsu.medvedev.entities.AuthorDTO;
import nsu.medvedev.entities.BookDTO;
import nsu.medvedev.entities.Shop;
import nsu.medvedev.mappers.AuthorMapper;
import nsu.medvedev.mappers.ShopMapper;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookDAOTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.2-alpine3.19"))
            .withInitScript("test-schema.sql");

    BookDAO bookDAO;
    AuthorDAO authorDAO;
    ShopDAO shopDAO;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        bookDAO = new BookDAO(dataBaseConnection.connectToDB(postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()));
        authorDAO = new AuthorDAO(dataBaseConnection.connectToDB(postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()));
        shopDAO = new ShopDAO(dataBaseConnection.connectToDB(postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()));

    }


    @Test
    @Order(1)
    void getBooks() {
        AuthorDTO author1 = new AuthorDTO(1, "Author1");
        AuthorDTO author2 = new AuthorDTO(2, "Author2");
        authorDAO.addAuthor(author1);
        authorDAO.addAuthor(author2);
        List<Shop> shops = new ArrayList<>();
        Shop shop = new Shop(1L, "Shop1");
        Shop shop1 = new Shop(2L, "Shop2");
        shops.add(shop);
        shops.add(shop1);
        shopDAO.addShop(ShopMapper.toShopDto(shop));
        shopDAO.addShop(ShopMapper.toShopDto(shop1));
        BookDTO book1 = new BookDTO(1L, "Book1", AuthorMapper.toAuthorModel(author1), shops);
        BookDTO book2 = new BookDTO(2L, "Book2", AuthorMapper.toAuthorModel(author2), shops);

        bookDAO.addBook(book1);
        bookDAO.addBook(book2);
        List<BookDTO> books = bookDAO.getAllBooks();
        assertEquals(2, books.size());
    }

    @Test
    @Order(2)
    void updateBooks() {
        AuthorDTO author3 = new AuthorDTO(1, "Author3");
        AuthorDTO author4 = new AuthorDTO(2, "Author4");
        authorDAO.addAuthor(author3);
        authorDAO.addAuthor(author4);
        List<Shop> shops = new ArrayList<>();
        Shop shop = new Shop(1L, "Shop3");
        Shop shop1 = new Shop(2L, "Shop4");
        shops.add(shop);
        shops.add(shop1);
        shopDAO.addShop(ShopMapper.toShopDto(shop));
        shopDAO.addShop(ShopMapper.toShopDto(shop1));
        BookDTO book1 = new BookDTO(3L, "Book3", AuthorMapper.toAuthorModel(author3), shops);
        BookDTO book2 = new BookDTO(4L, "Book4", AuthorMapper.toAuthorModel(author3), shops);

        bookDAO.addBook(book1);
        bookDAO.addBook(book2);

        BookDTO bookToUpdate = new BookDTO(1L, "UpdatedBook", AuthorMapper.toAuthorModel(author3), shops);


        bookDAO.updateBook(bookToUpdate);
        assertEquals("UpdatedBook", bookDAO.getAllBooks().getLast().getTitle());
    }

    @Test
    @Order(3)
    void deleteBook(){
        bookDAO.deleteBook(2L);
        assertEquals(3, bookDAO.getAllBooks().size());
    }

}
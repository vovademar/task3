package nsu.medvedev.servlets;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsu.medvedev.DAO.AuthorDAO;
import nsu.medvedev.DAO.BookDAO;
import nsu.medvedev.DAO.ShopDAO;
import nsu.medvedev.database.DataBaseConnection;
import nsu.medvedev.entities.AuthorDTO;
import nsu.medvedev.entities.BookDTO;
import nsu.medvedev.entities.Shop;
import nsu.medvedev.mappers.AuthorMapper;
import nsu.medvedev.mappers.ShopMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookServletTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.2-alpine3.19"))
            .withInitScript("test-schema.sql");


    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    private BookServlet bookServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;


    private BookDAO bookDAO;

    private AuthorDAO authorDAO;

    private ShopDAO shopDAO;

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

        MockitoAnnotations.openMocks(this);
        bookServlet = new BookServlet();
        bookServlet.bookDAO = bookDAO;
    }


    @Test
    @Order(1)
    void testDoGet() throws IOException {
        List<Shop> shops = new ArrayList<>();
        Shop shop = new Shop(1L, "Shop1");
        Shop shop1 = new Shop(2L, "Shop2");
        shops.add(shop);
        shopDAO.addShop(ShopMapper.toShopDto(shop));
        shopDAO.addShop(ShopMapper.toShopDto(shop1));

        AuthorDTO author = new AuthorDTO(1L, "John Doe", null);
        authorDAO.addAuthor(author);
        List<BookDTO> books = new ArrayList<>();

        BookDTO book = new BookDTO();
        book.setTitle("Book1");
        book.setId(1L);
        book.setAuthor(AuthorMapper.toAuthorModel(author));
        book.setShops(shops);
        books.add(book);


        bookDAO.addBook(book);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        bookServlet.doGet(request, response);

        Gson gson = new Gson();
        String expectedJson = gson.toJson(books);
        assertEquals(expectedJson, stringWriter.toString().trim());

    }

    @Test
    @Order(2)
    void testDoPost() throws IOException {
        List<Shop> shops = new ArrayList<>();
        Shop shop = new Shop(3L, "Shop3");
        Shop shop1 = new Shop(4L, "Shop4");
        shops.add(shop);
        shopDAO.addShop(ShopMapper.toShopDto(shop));
        shopDAO.addShop(ShopMapper.toShopDto(shop1));

        AuthorDTO author = new AuthorDTO(2L, "John Doe1", null);
        authorDAO.addAuthor(author);

        BookDTO book = new BookDTO();
        book.setTitle("Book2");
        book.setId(2L);
        book.setAuthor(AuthorMapper.toAuthorModel(author));
        book.setShops(shops);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new Gson().toJson(book))));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        bookServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    @Order(3)
    void testDoPut() throws IOException {
        List<Shop> shops = new ArrayList<>();
        Shop shop = new Shop(5L, "Shop5");
        Shop shop1 = new Shop(6L, "Shop6");
        shops.add(shop);
        shopDAO.addShop(ShopMapper.toShopDto(shop));
        shopDAO.addShop(ShopMapper.toShopDto(shop1));

        AuthorDTO author = new AuthorDTO(3L, "John Doe1", null);
        authorDAO.addAuthor(author);

        BookDTO book = new BookDTO();
        book.setTitle("Book2");
        book.setId(5L);
        book.setAuthor(AuthorMapper.toAuthorModel(author));
        book.setShops(shops);


        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new Gson().toJson(book))));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        bookServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    @Order(4)
    void testDoDelete() throws IOException {
        long bookId = 1L;
        when(request.getParameter("id")).thenReturn(String.valueOf(bookId));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        bookServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }


}
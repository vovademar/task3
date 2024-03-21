package nsu.medvedev.servlets;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsu.medvedev.DAO.AuthorDAO;
import nsu.medvedev.DAO.BookDAO;
import nsu.medvedev.DAO.ShopDAO;
import nsu.medvedev.database.DataBaseConnection;
import nsu.medvedev.entities.ShopDTO;
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
class ShopServletTest {


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

    private ShopServlet shopServlet;

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
        shopServlet = new ShopServlet();
        shopServlet.shopDAO = shopDAO;
    }

    @Test
    @Order(1)
    void testDoGet() throws IOException {
        List<ShopDTO> shops = new ArrayList<>();
        ShopDTO shop = new ShopDTO(1L, "Shop1");
        ShopDTO shop1 = new ShopDTO(2L, "Shop2");
        shops.add(shop);
        shops.add(shop1);
        shopDAO.addShop(shop);
        shopDAO.addShop(shop1);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        shopServlet.doGet(request, response);

        Gson gson = new Gson();
        String expectedJson = gson.toJson(shops);
        assertEquals(expectedJson, stringWriter.toString().trim());
    }

    @Test
    @Order(2)
    void testDoPost() throws IOException {
        List<ShopDTO> shops = new ArrayList<>();
        ShopDTO shop = new ShopDTO(3L, "Shop1");
        shops.add(shop);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new Gson().toJson(shop))));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        shopServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    @Order(3)
    void testDoPut() throws IOException {
        ShopDTO shop = new ShopDTO(3L, "Shop5");

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new Gson().toJson(shop))));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        shopServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    @Order(4)
    void testDoDelete() throws IOException {

        long shopId = 1L;

        when(request.getParameter("id")).thenReturn(String.valueOf(shopId));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        shopServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

}
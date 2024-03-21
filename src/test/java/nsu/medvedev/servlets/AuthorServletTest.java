package nsu.medvedev.servlets;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsu.medvedev.DAO.AuthorDAO;
import nsu.medvedev.database.DataBaseConnection;
import nsu.medvedev.entities.AuthorDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthorServletTest {

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

    private AuthorServlet authorServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;


    private AuthorDAO authorDAO;

    @BeforeEach
    void setUp() throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        authorDAO = new AuthorDAO(dataBaseConnection.connectToDB(postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()));

        MockitoAnnotations.openMocks(this);
        authorServlet = new AuthorServlet();
        authorServlet.authorDAO = authorDAO;
    }


    @Test
    void testDoGet() throws IOException {
        List<AuthorDTO> authors = new ArrayList<>();
        authors.add(new AuthorDTO(1,"John Doe"));
        authorDAO.addAuthor(new AuthorDTO("John Doe"));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        authorServlet.doGet(request, response);

        Gson gson = new Gson();
        String expectedJson = gson.toJson(authors);
        assertEquals(expectedJson, stringWriter.toString().trim());

    }

    @Test
    void testDoPost() throws IOException {
        AuthorDTO author = new AuthorDTO("Jane Smith");

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new Gson().toJson(author))));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        authorServlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void testDoPut() throws IOException {
        authorDAO.addAuthor(new AuthorDTO(1L, "Author"));
        AuthorDTO author = new AuthorDTO(1L,"Jane Smith");

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new Gson().toJson(author))));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        authorServlet.doPut(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testDoDelete() throws IOException {
        long authorId = 1L;
        when(request.getParameter("id")).thenReturn(String.valueOf(authorId));

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        authorServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

}

package nsu.medvedev.DAO;

import nsu.medvedev.database.DataBaseConnection;
import nsu.medvedev.entities.AuthorDTO;
import nsu.medvedev.entities.Book;
import nsu.medvedev.mappers.AuthorMapper;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthorDAOTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.2-alpine3.19"))
            .withInitScript("test-schema.sql");

    AuthorDAO authorDAO;

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
        authorDAO = new AuthorDAO(dataBaseConnection.connectToDB(postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()));
    }


    @Test
    @Order(1)
    void shouldGetAuthors() {
        List<Book> books = new ArrayList<>();
        AuthorDTO author1 = new AuthorDTO("Author1", books);
        AuthorDTO author2 = new AuthorDTO("Author2", books);
        Book book1 = new Book(1L, "Book1", AuthorMapper.toAuthorModel(author1));
        Book book2 = new Book(1L, "Book2", AuthorMapper.toAuthorModel(author2));
        books.add(book1);
        books.add(book2);
        author1.setBooks(books);
        authorDAO.addAuthor(author1);
        authorDAO.addAuthor(author2);

        List<AuthorDTO> authors = authorDAO.getAllAuthors();
        assertEquals(2, authors.size());
    }

    @Test
    @Order(2)
    void updateCustomers() {
        authorDAO.addAuthor(new AuthorDTO("Author3"));
        authorDAO.addAuthor(new AuthorDTO("Author4"));
        authorDAO.updateAuthor(new AuthorDTO(1, "Updated Author"));
        assertEquals("Updated Author", authorDAO.getAllAuthors().getLast().getName());
    }

    @Test
    @Order(3)
    void deleteCustomers() {
        authorDAO.addAuthor(new AuthorDTO("Author5"));
        authorDAO.addAuthor(new AuthorDTO("Author6"));
        authorDAO.deleteAuthor(1);
        List<AuthorDTO> authors = authorDAO.getAllAuthors();
        assertEquals(5, authors.size());
    }


}
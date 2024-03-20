package nsu.medvedev.DAO;

import nsu.medvedev.database.DataBaseConnection;
import nsu.medvedev.entities.ShopDTO;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class ShopDAOTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.2-alpine3.19"))
            .withInitScript("test-schema.sql");

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
        shopDAO = new ShopDAO(dataBaseConnection.connectToDB(postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()));
    }

    @Test
    @Order(1)
    void getAllShops(){
        ShopDTO shopDTO = new ShopDTO(1L, "Shop");
        shopDAO.addShop(shopDTO);
        assertEquals(1,shopDAO.getAllShops().size());
    }

    @Test
    @Order(2)
    void updateShops(){
        ShopDTO shop1 = new ShopDTO(2L, "Shop1");
        shopDAO.addShop(shop1);
        ShopDTO toUpdate = new ShopDTO(1L, "Updated Shop");
        shopDAO.updateShop(toUpdate);
        assertEquals(toUpdate.getName(), shopDAO.getAllShops().getLast().getName());
    }

    @Test
    @Order(3)
    void deleteShop(){
        shopDAO.deleteShop(1L);
        assertEquals(1, shopDAO.getAllShops().size());
    }
}
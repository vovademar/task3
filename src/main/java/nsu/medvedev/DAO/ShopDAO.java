package nsu.medvedev.DAO;

import nsu.medvedev.entities.Author;
import nsu.medvedev.entities.Book;
import nsu.medvedev.entities.ShopDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopDAO {
    public ShopDAO(Connection connection) {
        this.connection = connection;
    }

    private final Connection connection;


    public List<ShopDTO> getAllShops() {
        List<ShopDTO> shops = new ArrayList<>();
        String query = """
                select id, name from Shop
                """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                boolean shopExists = shops.stream().anyMatch(shop -> shop.getId() == id);
                if (!shopExists) {
                    List<Book> books = getBooksByShopId(id);
                    ShopDTO shop = new ShopDTO(id, name, books);
                    shops.add(shop);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return shops;
    }


    private List<Book> getBooksByShopId(long shopId) {
        List<Book> books = new ArrayList<>();
        String query = """
                select B.id, title, A.name, A.id as author_id from bookshop
                inner join Book B on B.id = bookshop.book_id
                inner join Author A on A.id = B.author_id
                where shop_id = ?;""";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, shopId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String authorName = resultSet.getString("name");
                Long authorId = resultSet.getLong("author_id");
                Author author = new Author(authorId, authorName, null);
                Book book = new Book(id, title, author);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch books from the database", e);
        }
        return books;
    }

    public void addShop(ShopDTO shop) {
        String query = "INSERT INTO Shop (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, shop.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add shop to the database", e);
        }
    }

    public void updateShop(ShopDTO shop) {
        String query = "UPDATE Shop SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, shop.getName());
            statement.setLong(2, shop.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update shop in the database", e);
        }
    }


    public void deleteShop(long shopId) {
        String query = "DELETE FROM Shop WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, shopId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete shop from the database", e);
        }
    }
}

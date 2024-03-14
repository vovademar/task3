package nsu.medvedev.DAO;

import nsu.medvedev.entities.Author;
import nsu.medvedev.entities.Book;
import nsu.medvedev.entities.Shop;

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


    public List<Shop> getAllShops() {
        List<Shop> shops = new ArrayList<>();
        String query = """
                select shop_id, S.name, B.title from bookshop
                inner join Shop S on S.id = bookshop.shop_id
                inner join Book B on B.id = bookshop.book_id
                """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("shop_id");
                String name = resultSet.getString("name");
                String title = resultSet.getString("title");
                List<Book> books = getBooksByShopId(id);
                Shop shop = new Shop(id, name, books);
                shops.add(shop);
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
}

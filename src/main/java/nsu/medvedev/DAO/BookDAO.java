package nsu.medvedev.DAO;

import nsu.medvedev.entities.Author;
import nsu.medvedev.entities.BookDTO;
import nsu.medvedev.entities.Shop;
import nsu.medvedev.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    private final Connection connection;

    public List<BookDTO> getAllBooks() {
        List<BookDTO> books = new ArrayList<>();
        String query = "SELECT id, title, author_id FROM Book";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                long authorId = resultSet.getLong("author_id");
                List<Shop> shops = getShopsByID(id);
                Author author = getAuthorById(authorId);

                BookDTO book = new BookDTO(id, title, author, shops);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to fetch books from the database" + e.getMessage());
        }
        return books;
    }

    public List<Shop> getShopsByID(long bookId) {
        List<Shop> shops = new ArrayList<>();
        String query = """
                SELECT shop_id, name FROM Book
                inner join BookShop BS on Book.id = BS.book_id
                inner join Shop S on S.id = BS.shop_id
                where book_id = ?;
                """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("shop_id");
                String name = resultSet.getString("name");
                Shop shop = new Shop(id, name);
                shops.add(shop);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to get shop by id: " + e.getMessage());
        }
        return shops;
    }

    public void updateBook(BookDTO book) {
        String query = "UPDATE Book SET title = ?, author_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setLong(2, book.getAuthor().getId());
            statement.setLong(3, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update book in the database: " + e.getMessage());
        }
    }

    public void deleteBook(long bookId) {
        String query = "DELETE FROM Book WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bookId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to delete book from the database: " + e.getMessage());
        }
    }

    private Author getAuthorById(long authorId) {
        String query = "SELECT id, name FROM Author WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, authorId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                return new Author(id, name, null);
            } else {
                throw new IllegalArgumentException("Author with id " + authorId + " not found");
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to fetch author from the database: " + e.getMessage());
        }
    }

    public void addBook(BookDTO book) {
        String query = "INSERT INTO Book (title, author_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setLong(2, book.getAuthor().getId());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new SQLException("Failed to insert book into database");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Failed to get generated key for inserted book");
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to add book to the database: " + e.getMessage());
        }

        String query1 = """
                INSERT INTO bookshop (book_id, shop_id)
                VALUES ((SELECT id from Book where title = ?), (SELECT id from shop where name = ?));
                """;
        try (PreparedStatement statement = connection.prepareStatement(query1)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getShops().getFirst().getName());
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted == 0) {
                throw new SQLException("Failed to insert book into database");
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to add book to the database: " + e.getMessage());
        }

    }
}



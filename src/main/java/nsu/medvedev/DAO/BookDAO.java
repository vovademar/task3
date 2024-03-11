package nsu.medvedev.DAO;

import nsu.medvedev.entities.Author;
import nsu.medvedev.entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    private final Connection connection;

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT id, title, author_id FROM Book";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                long authorId = resultSet.getLong("author_id");
                Author author = getAuthorById(authorId); // Получаем автора книги из базы данных
                Book book = new Book(id, title, authorId);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch books from the database", e);
        }
        return books;
    }

    public void updateBook(Book book) {
        String query = "UPDATE Book SET title = ?, author_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setLong(2, book.getAuthorId());
            statement.setLong(3, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update book in the database", e);
        }
    }

    public void deleteBook(long bookId) {
        String query = "DELETE FROM Book WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bookId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete book from the database", e);
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
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch author from the database", e);
        }
    }

    public void addBook(Book book) {
        String query = "INSERT INTO Book (title, author_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setLong(2, book.getAuthorId());
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
            e.printStackTrace();
            throw new RuntimeException("Failed to add book to the database", e);
        }
    }
}



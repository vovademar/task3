package nsu.medvedev.DAO;

import nsu.medvedev.entities.Author;
import nsu.medvedev.entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    public AuthorDAO(Connection connection) {
        this.connection = connection;
    }

    private final Connection connection;

    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT id, name FROM Author";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                List<Book> books = getBooksByAuthorId(id); // Получаем список книг автора из базы данных
                Author author = new Author(id, name, books);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch authors from the database", e);
        }
        return authors;
    }

    private List<Book> getBooksByAuthorId(long authorId) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT id, title FROM Book WHERE author_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, authorId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                Book book = new Book(id, title, null); // Возвращаем книгу без ссылки на автора
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch books from the database", e);
        }
        return books;
    }
}

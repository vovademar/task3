package nsu.medvedev.dao;

import nsu.medvedev.entities.AuthorDTO;
import nsu.medvedev.entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    public AuthorDAO(Connection connection) {
        this.connection = connection;
    }

    private final Connection connection;

    public List<AuthorDTO> getAllAuthors() {
        List<AuthorDTO> authors = new ArrayList<>();
        String query = "SELECT id, name FROM Author";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                List<Book> books = getBooksByAuthorId(id);
                AuthorDTO author = new AuthorDTO(id, name, books);
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
                Book book = new Book(id, title, null);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch books from the database", e);
        }
        return books;
    }

    public void addAuthor(AuthorDTO author) {
        String query = "INSERT INTO Author (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, author.getName());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new SQLException("Failed to insert author into database");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                author.setId(generatedKeys.getLong(1)); // Устанавливаем ID автора, сгенерированный базой данных
            } else {
                throw new SQLException("Failed to get generated key for inserted author");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add author to the database", e);
        }
    }

    public void updateAuthor(AuthorDTO author) {
        String query = "UPDATE Author SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, author.getName());
            statement.setLong(2, author.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Failed to update author with id " + author.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update author in the database", e);
        }
    }


    public void deleteAuthor(long authorId) {
        String query = "DELETE FROM Author WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, authorId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update author in the database", e);
        }
    }


}

package nsu.medvedev.DAO;

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
        String query = "SELECT * FROM Book";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                Long authorId = resultSet.getLong("author_id");

                Book book = new Book(id, title, authorId);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch books from the database", e);
        }

        return books;
    }

}


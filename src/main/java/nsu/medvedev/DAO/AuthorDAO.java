package nsu.medvedev.DAO;

import nsu.medvedev.entities.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    public AuthorDAO(Connection connection){
        this.connection = connection;
    }

    private final Connection connection;

    public List<Author> getAllAuthors() throws SQLException {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM Author";
        Statement statement = connection.createStatement();
        ResultSet queryRes = statement.executeQuery(query);
        while (queryRes.next()) {
            Long id = queryRes.getLong("id");
            String name = queryRes.getString("name");
            Author author = new Author(id, name);
            authors.add(author);
        }
        return authors;
    }
}
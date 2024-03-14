package nsu.medvedev.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsu.medvedev.DAO.AuthorDAO;
import nsu.medvedev.DataBaseConnection;
import nsu.medvedev.entities.Author;
import nsu.medvedev.entities.Book;

@WebServlet("/authors")
public class AuthorServlet extends HttpServlet {
    private AuthorDAO authorDAO;

    @Override
    public void init() {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        try {
            authorDAO = new AuthorDAO(dataBaseConnection.connectToDB());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Author> authors;
        authors = authorDAO.getAllAuthors();

        Gson gson = new Gson();
        String jsonAuthors = gson.toJson(authors);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonAuthors);
        out.flush();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        Gson gson = new Gson();
        Author newAuthor = gson.fromJson(jsonBuilder.toString(), Author.class);

        authorDAO.addAuthor(newAuthor);

        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_CREATED);
        PrintWriter out = response.getWriter();
        out.println("Author added successfully");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        Gson gson = new Gson();
        Author updatedAuthor = gson.fromJson(jsonBuilder.toString(), Author.class);

        authorDAO.updateAuthor(updatedAuthor);

        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        out.println("Author updated successfully");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long authorId = Long.parseLong(request.getParameter("id"));

        authorDAO.deleteAuthor(authorId);

        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        out.println("Book deleted successfully");
    }
}

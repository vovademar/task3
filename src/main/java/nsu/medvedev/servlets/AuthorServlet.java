package nsu.medvedev.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsu.medvedev.DAO.AuthorDAO;
import nsu.medvedev.DataBaseConnection;
import nsu.medvedev.entities.Author;

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

        List<Author> authors = null;
        authors = authorDAO.getAllAuthors();

        Gson gson = new Gson();
        String jsonAuthors = gson.toJson(authors);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonAuthors);
        out.flush();
    }
}

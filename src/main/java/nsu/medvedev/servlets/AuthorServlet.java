package nsu.medvedev.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsu.medvedev.DAO.AuthorDAO;
import nsu.medvedev.database.DataBaseConnection;
import nsu.medvedev.entities.AuthorDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/authors")
public class AuthorServlet extends HttpServlet {
    private static final String PLAIN = "text/plain";
    private transient AuthorDAO authorDAO;

    @Override
    public void init() throws ServletException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        try {
            authorDAO = new AuthorDAO(dataBaseConnection.connectToDB());
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize AuthorDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        List<AuthorDTO> authors;
        authors = authorDAO.getAllAuthors();

        Gson gson = new Gson();
        String jsonAuthors = gson.toJson(authors);

        response.setContentType("application/json");
        try {
            PrintWriter out = response.getWriter();
            out.print(jsonAuthors);
            out.flush();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            AuthorDTO newAuthor = getReader(request);
            authorDAO.addAuthor(newAuthor);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        response.setContentType(PLAIN);
        response.setStatus(HttpServletResponse.SC_CREATED);
        try {
            PrintWriter out = response.getWriter();
            out.println("Author added successfully");
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private AuthorDTO getReader(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        Gson gson = new Gson();
        return gson.fromJson(jsonBuilder.toString(), AuthorDTO.class);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            authorDAO.updateAuthor(getReader(request));
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        response.setContentType(PLAIN);
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            PrintWriter out = response.getWriter();
            out.println("Author updated successfully");
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            long authorId = Long.parseLong(request.getParameter("id"));
            authorDAO.deleteAuthor(authorId);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        response.setContentType(PLAIN);
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            PrintWriter out = response.getWriter();
            out.println("Author deleted successfully");
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }
}

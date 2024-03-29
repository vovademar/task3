package nsu.medvedev.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsu.medvedev.DAO.BookDAO;
import nsu.medvedev.database.DataBaseConnection;
import nsu.medvedev.entities.BookDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private static final String PLAIN = "text/plain";
    transient BookDAO bookDAO;

    @Override
    public void init() throws ServletException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        try {
            bookDAO = new BookDAO(dataBaseConnection.connectToDB());
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize BookDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<BookDTO> books = bookDAO.getAllBooks();

        Gson gson = new Gson();
        String jsonBooks = gson.toJson(books);

        response.setContentType("application/json");
        try {
            PrintWriter out = response.getWriter();
            out.print(jsonBooks);
            out.flush();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            BookDTO newBook = getReader(request);
            bookDAO.addBook(newBook);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        response.setContentType(PLAIN);
        response.setStatus(HttpServletResponse.SC_CREATED);
        try {
            PrintWriter out = response.getWriter();
            out.println("Book added successfully");
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            BookDTO updatedBook = getReader(request);
            bookDAO.updateBook(updatedBook);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        response.setContentType(PLAIN);
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            PrintWriter out = response.getWriter();
            out.println("Book updated successfully");
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private BookDTO getReader(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        Gson gson = new Gson();
        return gson.fromJson(jsonBuilder.toString(), BookDTO.class);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            long bookId = Long.parseLong(request.getParameter("id"));
            bookDAO.deleteBook(bookId);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        response.setContentType(PLAIN);
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            PrintWriter out = response.getWriter();
            out.println("Book deleted successfully");
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}


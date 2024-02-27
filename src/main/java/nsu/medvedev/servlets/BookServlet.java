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
import nsu.medvedev.DAO.BookDAO;
import nsu.medvedev.DataBaseConnection;
import nsu.medvedev.entities.Book;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private BookDAO bookDAO;

    @Override
    public void init() {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        try {
            bookDAO = new BookDAO(dataBaseConnection.connectToDB());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем список книг из базы данных
        List<Book> books = bookDAO.getAllBooks();

        // Преобразуем список книг в формат JSON
        Gson gson = new Gson();
        String jsonBooks = gson.toJson(books);

        // Устанавливаем тип контента и отправляем JSON клиенту
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonBooks);
        out.flush();
    }
}


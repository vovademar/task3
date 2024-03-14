package nsu.medvedev.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsu.medvedev.DAO.BookDAO;
import nsu.medvedev.DAO.ShopDAO;
import nsu.medvedev.DataBaseConnection;
import nsu.medvedev.entities.Book;
import nsu.medvedev.entities.Shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/shops")
public class ShopServlet extends HttpServlet {
    private ShopDAO shopDAO;

    @Override
    public void init() {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        try {
            shopDAO = new ShopDAO(dataBaseConnection.connectToDB());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Shop> shops = shopDAO.getAllShops();

        Gson gson = new Gson();
        String jsonBooks = gson.toJson(shops);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonBooks);
        out.flush();
    }

}

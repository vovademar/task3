package nsu.medvedev.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nsu.medvedev.DAO.ShopDAO;
import nsu.medvedev.database.DataBaseConnection;
import nsu.medvedev.entities.ShopDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/shops")
public class ShopServlet extends HttpServlet {
    private static final String PLAIN = "text/plain";
    private transient ShopDAO shopDAO;

    @Override
    public void init() throws ServletException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        try {
            shopDAO = new ShopDAO(dataBaseConnection.connectToDB());
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize ShopDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<ShopDTO> shops = shopDAO.getAllShops();

        Gson gson = new Gson();
        String jsonBooks = gson.toJson(shops);

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
            ShopDTO newShop = getReader(request);
            shopDAO.addShop(newShop);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        response.setContentType(PLAIN);
        response.setStatus(HttpServletResponse.SC_CREATED);
        try {
            PrintWriter out = response.getWriter();
            out.println("Shop added successfully");
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    private ShopDTO getReader(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        Gson gson = new Gson();
        return gson.fromJson(jsonBuilder.toString(), ShopDTO.class);
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {

        try {
            shopDAO.updateShop(getReader(request));
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        }

        response.setContentType(PLAIN);
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            PrintWriter out = response.getWriter();
            out.println("Shop updated successfully");
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            long shopId = Long.parseLong(request.getParameter("id"));
            shopDAO.deleteShop(shopId);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        response.setContentType(PLAIN);
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            PrintWriter out = response.getWriter();
            out.println("Shop deleted successfully");
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }


}

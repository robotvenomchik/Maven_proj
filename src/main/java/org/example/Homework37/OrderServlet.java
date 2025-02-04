package org.example.Homework37;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Order order = objectMapper.readValue(req.getReader(), Order.class);
            orderDAO.addOrder(order);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(objectMapper.writeValueAsString(order));
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating order");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        if (idParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order ID is required");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            Order order = orderDAO.getOrderById(id);

            if (order == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
            } else {
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(order));
            }
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving order");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Order order = objectMapper.readValue(req.getReader(), Order.class);
            orderDAO.updateOrder(order);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(objectMapper.writeValueAsString(order));
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating order");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        if (idParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Order ID is required");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            orderDAO.deleteOrder(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting order");
        }
    }
}

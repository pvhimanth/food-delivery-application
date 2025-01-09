package com.foodapp.servlets;

import java.io.IOException;
import java.util.ArrayList;

import com.foodapp.daoimpl.OrdersDAOImpl;
import com.foodapp.daoimpl.RestaurantDAOImpl;
import com.foodapp.model.Orders;
import com.foodapp.model.Restaurant;
import com.foodapp.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("user") != null) {
			// Get userId from session
			int userId = ((User) session.getAttribute("user")).getUserId();

			// Fetch orders for the user
			OrdersDAOImpl ordersDAO = new OrdersDAOImpl();
			ArrayList<Orders> userOrders = ordersDAO.fetchByUser(userId); // Use the new fetchByUser method

			// Fetch restaurant list
			RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();
			ArrayList<Restaurant> restaurantList = restaurantDAO.fetchAll();

			// Set attributes for JSP
			request.setAttribute("userOrders", userOrders);
			request.setAttribute("restaurantList", restaurantList);

			// Forward to OrdersList.jsp
			request.getRequestDispatcher("OrdersList.jsp").forward(request, response);
		} else {
			response.sendRedirect("login.jsp"); // Redirect to login if session is invalid
		}
	}
}

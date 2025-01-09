package com.foodapp.servlets;

import java.io.IOException;

import com.foodapp.cart.Cart;
import com.foodapp.cart.CartItem;
import com.foodapp.dao.OrderHistoryDAO;
import com.foodapp.dao.OrderitemsDAO;
import com.foodapp.dao.OrdersDAO;
import com.foodapp.daoimpl.OrderHistoryDAOImpl;
import com.foodapp.daoimpl.OrderitemsDAOImpl;
import com.foodapp.daoimpl.OrdersDAOImpl;
import com.foodapp.model.OrderHistory;
import com.foodapp.model.Orderitems;
import com.foodapp.model.Orders;
import com.foodapp.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CheckOutServlet")
public class CheckOutServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		int resId = (int) session.getAttribute("restaurantId");
		Cart cart = (Cart) session.getAttribute("cart");

		if (cart != null && user != null && !cart.getItems().isEmpty()) {

			String paymentMode = (String) req.getParameter("paymentMode");

			Orders orders = new Orders();
			orders.setUserId(user.getUserId());
			orders.setRestaurantId(resId);
			orders.setStatus("pending");
			
			orders.setPaymentMode(paymentMode);

			float totalAmount = 0;
			for (CartItem item : cart.getItems().values()) {

				totalAmount += (float) (item.getPrice() * item.getQuantity());

			}
			orders.setTotalAmount(totalAmount);

			OrdersDAO odao = new OrdersDAOImpl();
			int orderId = odao.insert(orders);

			if (orderId > 0) {
				for (CartItem c : cart.getItems().values()) {
					OrderitemsDAO oiDao = new OrderitemsDAOImpl();
					int isInserted = oiDao.insert(new Orderitems(orderId, c.getItemId(), c.getQuantity(),
							(int) (c.getPrice() * c.getQuantity())));
//
//					if (isInserted == 0) {
//						System.out.println("Failed to insert OrderItem for Item ID: " + c.getItemId());
//					}
				}

				OrderHistoryDAO ohDAO = new OrderHistoryDAOImpl();
				ohDAO.insert(new OrderHistory(orderId, user.getUserId(), totalAmount, "pending"));

				session.removeAttribute("cart");
				session.setAttribute("orders", orders);

				resp.sendRedirect("OrderConfirm/OrderConfirmation.jsp");

			} else {
				resp.sendRedirect("Home.jsp");
			}

		}
	}
}

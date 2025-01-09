package com.foodapp.servlets;

import java.io.IOException;

import com.foodapp.cart.Cart;
import com.foodapp.cart.CartItem;
import com.foodapp.dao.MenuDAO;
import com.foodapp.daoimpl.MenuDAOImpl;
import com.foodapp.model.Menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // STEP 1: Get the session for the cart
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        // STEP 2: Check if the cart exists; if not, create one
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // STEP 3: Fetch request parameters
        String action = req.getParameter("action");
        int itemId = Integer.parseInt(req.getParameter("itemId"));

        // STEP 4: Handle different actions
        try {
            if ("add".equals(action)) {
                MenuDAO menuDao = new MenuDAOImpl();
                Menu menuItem = menuDao.fetchOne(itemId);

                if (menuItem != null) {
                    int quantity = Integer.parseInt(req.getParameter("quantity"));
                    CartItem cartItem = new CartItem(menuItem.getMenuId(), menuItem.getRestaurantId(),
                            menuItem.getName(), quantity, menuItem.getPrice());
                    cart.addItem(cartItem);
                 // Set restaurantId in the session
                    session.setAttribute("restaurantId", menuItem.getRestaurantId());
                }
            } else if ("update".equals(action)) {
                int quantity = Integer.parseInt(req.getParameter("quantity"));
                cart.updateItem(itemId, quantity);
            } else if ("remove".equals(action)) {
                cart.removeItem(itemId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Update the session attribute and redirect
        session.setAttribute("cart", cart);
        resp.sendRedirect("Cart.jsp");
    }
}

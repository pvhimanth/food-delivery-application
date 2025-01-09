package com.foodapp.servlets;

import java.io.IOException;
import java.util.ArrayList;

import com.foodapp.daoimpl.RestaurantDAOImpl;
import com.foodapp.model.Restaurant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/GetAllRestaurants")
public class GetAllRestaurants extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Check if `resList` is already in the session
        @SuppressWarnings("unchecked")
        ArrayList<Restaurant> resList = (ArrayList<Restaurant>) session.getAttribute("resList");

        if (resList == null) {
            // Only fetch data if `resList` is not already present
            RestaurantDAOImpl rdao = new RestaurantDAOImpl();
            resList = rdao.fetchAll();
            session.setAttribute("resList", resList);
        }

        resp.sendRedirect("Home.jsp");
    }
}

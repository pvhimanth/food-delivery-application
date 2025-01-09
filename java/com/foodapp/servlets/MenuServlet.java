package com.foodapp.servlets;

import java.io.IOException;
import java.util.ArrayList;

import com.foodapp.dao.MenuDAO;
import com.foodapp.daoimpl.MenuDAOImpl;
import com.foodapp.model.Menu;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MenuServlet")
public class MenuServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MenuDAO mdao = new MenuDAOImpl();
		int restaurantId = Integer.parseInt( req.getParameter("restaurantId"));
		ArrayList<Menu> menus = mdao.fetchMenuByRestaurant(restaurantId);
		req.setAttribute("menus", menus);
		RequestDispatcher rd = req.getRequestDispatcher("Menu.jsp");
		rd.forward(req, resp);

	}
}

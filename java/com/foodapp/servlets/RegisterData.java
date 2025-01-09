package com.foodapp.servlets;

import java.io.IOException;

import com.foodapp.dao.UserDAO;
import com.foodapp.daoimpl.UserDAOImpl;
import com.foodapp.model.User;
import com.foodapp.secure.MyEncrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterData")
public class RegisterData extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String address = req.getParameter("address");

		try {

			if (password.equals(req.getParameter("cpassword"))) {

				UserDAO u = new UserDAOImpl();
				int x = u.insert(new User(MyEncrypt.encrypt(req.getParameter("username")),
						MyEncrypt.encrypt(req.getParameter("password")), MyEncrypt.encrypt(req.getParameter("email")),
						MyEncrypt.encrypt(req.getParameter("address"))));

				if (x == 0) {
	                resp.sendRedirect("RegistrationFailure.html");
				} else {
	                resp.sendRedirect("Login.html");
				}
			} else {
				resp.sendRedirect("PasswordMismatch.html");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

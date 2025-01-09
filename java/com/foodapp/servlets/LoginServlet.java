package com.foodapp.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.foodapp.dao.UserDAO;
import com.foodapp.daoimpl.UserDAOImpl;
import com.foodapp.dbutil.DBConnection;
import com.foodapp.model.User;
import com.foodapp.secure.MyEncrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private Connection con = null;
	private String checkMail = "SELECT * FROM USER WHERE EMAIL=?";
	private PreparedStatement pstmt = null;
	private ResultSet resultSet = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String email = MyEncrypt.encrypt(req.getParameter("email"));
		String password = MyEncrypt.encrypt(req.getParameter("password"));

		try {

			con = DBConnection.connect();
			pstmt = con.prepareStatement(checkMail);
			pstmt.setString(1, email);
			resultSet = pstmt.executeQuery();

			if (resultSet.next()) {

				if (password.equals(resultSet.getString("password"))) {

					UserDAO udao = new UserDAOImpl();
					User user = udao.fetchWithEmail(email);
					session.setAttribute("user", user);

					req.getRequestDispatcher("GetAllRestaurants").forward(req, resp);

				} else {
					resp.sendRedirect("PasswordMismatch.html");
				}

			} else {
				resp.sendRedirect("InvalidUser.html");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

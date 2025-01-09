package com.foodapp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.foodapp.dao.OrdersDAO;
import com.foodapp.dbutil.DBConnection;
import com.foodapp.model.Orders;

public class OrdersDAOImpl implements OrdersDAO {
	private static Connection con;
	static ArrayList<Orders> ordersList = new ArrayList<Orders>();

	private static final String INSERTQUERY = "INSERT INTO orders(userId, restaurantId, totalAmount, status, paymentMode) VALUES(?,?,?,?,?)";
	private static final String FETCHALL = "SELECT * FROM orders";
	private static final String FETCHBYUSER = "SELECT * FROM orders WHERE userId = ?"; // New query for fetching by user
	private static final String FETCHONE = "SELECT * FROM orders WHERE orderId = ?";
	private static final String UPDATE = "UPDATE orders SET status = ? WHERE orderId = ?";
	private static final String DELETE = "DELETE FROM orders WHERE orderId = ?";

	static {
		con = DBConnection.connect();
	}

	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet resultSet;

	@Override
	public int insert(Orders order) {
		int orderId = 0;
		try {
			pstmt = con.prepareStatement(INSERTQUERY, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, order.getUserId());
			pstmt.setInt(2, order.getRestaurantId());
			pstmt.setFloat(3, order.getTotalAmount());
			pstmt.setString(4, order.getStatus());
			pstmt.setString(5, order.getPaymentMode());

			int effectedRows = pstmt.executeUpdate();

			ResultSet res = pstmt.getGeneratedKeys();

			while (res.next()) {
				orderId = res.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderId;
	}

	@Override
	public ArrayList<Orders> fetchAll() {
		try {
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(FETCHALL);

			while (resultSet.next()) {
				ordersList.add(new Orders(resultSet.getInt("orderId"), resultSet.getInt("userId"),
						resultSet.getInt("restaurantId"), resultSet.getFloat("totalAmount"),
						resultSet.getString("status"), resultSet.getString("paymentMode")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ordersList;
	}

	// New method to fetch orders by userId
	public ArrayList<Orders> fetchByUser(int userId) {
		ArrayList<Orders> userOrdersList = new ArrayList<Orders>();
		try {
			pstmt = con.prepareStatement(FETCHBYUSER);
			pstmt.setInt(1, userId);
			resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				userOrdersList.add(new Orders(resultSet.getInt("orderId"), resultSet.getInt("userId"),
						resultSet.getInt("restaurantId"), resultSet.getFloat("totalAmount"),
						resultSet.getString("status"), resultSet.getString("paymentMode")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userOrdersList;
	}

	@Override
	public Orders fetchOne(int id) {
		try {
			pstmt = con.prepareStatement(FETCHONE);
			pstmt.setInt(1, id);
			resultSet = pstmt.executeQuery();

			ArrayList<Orders> localOrdersList = extractOrdersListFromResultSet(resultSet);

			if (!localOrdersList.isEmpty()) {
				return localOrdersList.get(0);
			} else {
				System.out.println("No Order found with ID " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	ArrayList<Orders> extractOrdersListFromResultSet(ResultSet resultSet) {
		ArrayList<Orders> localOrdersList = new ArrayList<Orders>();
		try {
			while (resultSet.next()) {
				localOrdersList.add(new Orders(resultSet.getInt("orderId"), resultSet.getInt("userId"),
						resultSet.getInt("restaurantId"), resultSet.getFloat("totalAmount"),
						resultSet.getString("status"), resultSet.getString("paymentMode")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localOrdersList;
	}

	@Override
	public int update(int id, String status) {
		try {
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, status);
			pstmt.setInt(2, id);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int delete(int id) {
		try {
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, id);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}

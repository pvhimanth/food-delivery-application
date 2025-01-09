package com.foodapp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.foodapp.dao.OrderHistoryDAO;
import com.foodapp.dbutil.DBConnection;
import com.foodapp.model.OrderHistory;

public class OrderHistoryDAOImpl implements OrderHistoryDAO {
	private static Connection con;
	static ArrayList<OrderHistory> orderHistoryList = new ArrayList<OrderHistory>();

	private static final String INSERTQUERY = "INSERT INTO orderhistory(orderId, userId, totalAmount, status) VALUES(?,?,?,?)";
	private static final String FETCHALL = "SELECT * FROM orderhistory";
	private static final String FETCHONE = "SELECT * FROM orderhistory WHERE orderHistoryId = ?";
	private static final String UPDATE = "UPDATE orderhistory SET status = ? WHERE orderHistoryId = ?";
	private static final String DELETE = "DELETE FROM orderhistory WHERE orderHistoryId = ?";

	static {
		con = DBConnection.connect();
	}

	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet resultSet;

	@Override
	public int insert(OrderHistory orderhistory) {

		try {
			pstmt = con.prepareStatement(INSERTQUERY);
			pstmt.setInt(1, orderhistory.getOrderId());
			pstmt.setInt(2, orderhistory.getUserId());
			pstmt.setFloat(3, orderhistory.getTotalAmount());
			pstmt.setString(4, orderhistory.getStatus());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ArrayList<OrderHistory> fetchAll() {

		try {
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(FETCHALL);

			while (resultSet.next()) {
				orderHistoryList.add(new OrderHistory(resultSet.getInt("orderHistoryId"), resultSet.getInt("orderId"),
						resultSet.getInt("userId"), resultSet.getFloat("totalAmount"), resultSet.getString("status")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderHistoryList;
	}

	@Override
	public OrderHistory fetchOne(int id) {
		try {
			pstmt = con.prepareStatement(FETCHONE);
			pstmt.setInt(1, id);
			resultSet = pstmt.executeQuery();

			ArrayList<OrderHistory> localOrderHistoryList = extractOrderHistoryListFromResultSet(resultSet);

			if (!localOrderHistoryList.isEmpty()) {
				return localOrderHistoryList.get(0);
			} else {
				System.out.println("No Order History found with ID " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	ArrayList<OrderHistory> extractOrderHistoryListFromResultSet(ResultSet resultSet) {
		ArrayList<OrderHistory> localOrderHistoryList = new ArrayList<OrderHistory>();
		try {
			while (resultSet.next()) {
				localOrderHistoryList.add(new OrderHistory(resultSet.getInt("orderHistoryId"),
						resultSet.getInt("orderId"), resultSet.getInt("userId"), resultSet.getFloat("totalAmount"),
						resultSet.getString("status")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localOrderHistoryList;
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

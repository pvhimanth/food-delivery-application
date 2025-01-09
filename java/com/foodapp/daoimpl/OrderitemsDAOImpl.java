package com.foodapp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.foodapp.dao.OrderitemsDAO;
import com.foodapp.dbutil.DBConnection;
import com.foodapp.model.Orderitems;

public class OrderitemsDAOImpl implements OrderitemsDAO {
	private static Connection con;
	static ArrayList<Orderitems> orderItemsList = new ArrayList<Orderitems>();

	private static final String INSERTQUERY = "INSERT INTO orderitems(orderId, menuId, quantity, itemTotal) VALUES(?,?,?,?)";
	private static final String FETCHALL = "SELECT * FROM orderitems";
	private static final String FETCHONE = "SELECT * FROM orderitems WHERE orderItemId = ?";
	private static final String UPDATE = "UPDATE orderitems SET quantity = ? WHERE orderItemId = ?";
	private static final String DELETE = "DELETE FROM orderitems WHERE orderItemId = ?";
	private static final String FETCHBYORDERID = "SELECT * FROM orderitems WHERE orderId = ?";

	static {
		con = DBConnection.connect();
	}

	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet resultSet;

	@Override
	public int insert(Orderitems orderitems) {

		try {
			pstmt = con.prepareStatement(INSERTQUERY);
			pstmt.setInt(1, orderitems.getOrderId());
			pstmt.setInt(2, orderitems.getMenuId());
			pstmt.setInt(3, orderitems.getQuantity());
			pstmt.setInt(4, orderitems.getItemTotal());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ArrayList<Orderitems> fetchAll() {

		try {
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(FETCHALL);

			while (resultSet.next()) {
				orderItemsList.add(new Orderitems(resultSet.getInt("orderItemId"), resultSet.getInt("orderId"),
						resultSet.getInt("menuId"), resultSet.getInt("quantity"), resultSet.getInt("itemTotal")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderItemsList;
	}

	@Override
	public Orderitems fetchOne(int id) {
		try {
			pstmt = con.prepareStatement(FETCHONE);
			pstmt.setInt(1, id);
			resultSet = pstmt.executeQuery();

			ArrayList<Orderitems> localOrderItemsList = extractOrderItemsListFromResultSet(resultSet);

			if (!localOrderItemsList.isEmpty()) {
				return localOrderItemsList.get(0);
			} else {
				System.out.println("No Order Item found with ID " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	ArrayList<Orderitems> extractOrderItemsListFromResultSet(ResultSet resultSet) {
		ArrayList<Orderitems> localOrderItemsList = new ArrayList<Orderitems>();
		try {
			while (resultSet.next()) {
				localOrderItemsList.add(new Orderitems(resultSet.getInt("orderItemId"), resultSet.getInt("orderId"),
						resultSet.getInt("menuId"), resultSet.getInt("quantity"), resultSet.getInt("itemTotal")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localOrderItemsList;
	}

	@Override
	public int update(int id, int quantity) {

		try {
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, quantity);
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

	@Override
	public ArrayList<Orderitems> fetchByOrderId(int orderId) {
		ArrayList<Orderitems> orderItemsByOrderId = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(FETCHBYORDERID);
			pstmt.setInt(1, orderId);
			resultSet = pstmt.executeQuery();

			orderItemsByOrderId = extractOrderItemsListFromResultSet(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderItemsByOrderId;
	}

}

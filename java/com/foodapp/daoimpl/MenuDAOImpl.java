package com.foodapp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.foodapp.dao.MenuDAO;
import com.foodapp.dbutil.DBConnection;
import com.foodapp.model.Menu;

public class MenuDAOImpl implements MenuDAO {
	private static Connection con;
	static ArrayList<Menu> menuList = new ArrayList<Menu>();

	private static final String INSERTQUERY = "INSERT INTO menu(menuId, restaurantId, name, description, price, isAvailable, imagePath) VALUES(?,?,?,?,?,?,?)";
	private static final String FETCHALL = "SELECT * FROM menu";
	private static final String FETCHONE = "SELECT * FROM menu WHERE menuId = ?";
	private static final String UPDATE = "UPDATE menu SET description = ? WHERE menuId = ?";
	private static final String DELETE = "DELETE FROM menu WHERE menuId = ?";
	private static final String FETCHMENUBYRESTAURANTID = "SELECT * FROM menu WHERE restaurantId = ?";

	static {
		con = DBConnection.connect();
	}

	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet resultSet;

	@Override
	public int insert(Menu menu) {
		try {
			pstmt = con.prepareStatement(INSERTQUERY);
			pstmt.setInt(1, menu.getMenuId());
			pstmt.setInt(2, menu.getRestaurantId());
			pstmt.setString(3, menu.getName());
			pstmt.setString(4, menu.getDescription());
			pstmt.setFloat(5, menu.getPrice());
			pstmt.setBoolean(6, menu.isAvailable());
			pstmt.setString(7, menu.getImagePath());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ArrayList<Menu> fetchAll() {
		try {
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(FETCHALL);

			while (resultSet.next()) {
				menuList.add(new Menu(resultSet.getInt("menuId"), resultSet.getInt("restaurantId"),
						resultSet.getString("name"), resultSet.getString("description"), resultSet.getFloat("price"),
						resultSet.getBoolean("isAvailable"), resultSet.getString("imagePath")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menuList;
	}

	@Override
	public Menu fetchOne(int id) {
		try {
			pstmt = con.prepareStatement(FETCHONE);
			pstmt.setInt(1, id);
			resultSet = pstmt.executeQuery();

			ArrayList<Menu> localMenuList = extractMenuListFromResultSet(resultSet);

			if (!localMenuList.isEmpty()) {
				return localMenuList.get(0);
			} else {
				System.out.println("No Menu found with ID " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int update(int id, String description) {
		try {
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, description);
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
	public ArrayList<Menu> fetchMenuByRestaurant(int restaurantId) {

		try {

			pstmt = con.prepareStatement(FETCHMENUBYRESTAURANTID);
			pstmt.setInt(1, restaurantId);
			resultSet = pstmt.executeQuery();

			ArrayList<Menu> localMenuList = extractMenuListFromResultSet(resultSet);

			if (!localMenuList.isEmpty()) {
				return localMenuList;
			} else {
				System.out.println("No Menu found with ID " + restaurantId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	ArrayList<Menu> extractMenuListFromResultSet(ResultSet resultSet) {
		ArrayList<Menu> localMenuList = new ArrayList<Menu>();
		try {
			while (resultSet.next()) {
				localMenuList.add(new Menu(resultSet.getInt("menuId"), resultSet.getInt("restaurantId"),
						resultSet.getString("name"), resultSet.getString("description"), resultSet.getFloat("price"),
						resultSet.getBoolean("isAvailable"), resultSet.getString("imagePath")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localMenuList;
	}
}

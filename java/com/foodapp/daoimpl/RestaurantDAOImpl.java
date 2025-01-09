package com.foodapp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.foodapp.dao.RestaurantDAO;
import com.foodapp.dbutil.DBConnection;
import com.foodapp.model.Restaurant;

public class RestaurantDAOImpl implements RestaurantDAO {
	private static Connection con;
	static ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();

	private static final String INSERTQUERY = "INSERT INTO restaurant(restaurantId, name, cuisineType, deliveryTime, address, ratings, isActive, imagePath) VALUES(?,?,?,?,?,?,?,?)";
	private static final String FETCHALL = "SELECT * FROM restaurant";
	private static final String FETCHONE = "SELECT * FROM restaurant WHERE restaurantId = ?";
	private static final String UPDATE = "UPDATE restaurant SET address = ? WHERE restaurantId = ?";
	private static final String DELETE = "DELETE FROM restaurant WHERE restaurantId = ?";

	static {
		con = DBConnection.connect();
	}

	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet resultSet;

	@Override
	public int insert(Restaurant restaurant) {

		try {
			pstmt = con.prepareStatement(INSERTQUERY);
			pstmt.setInt(1, restaurant.getRestaurantId());
			pstmt.setString(2, restaurant.getName());
			pstmt.setString(3, restaurant.getCuisineType());
			pstmt.setInt(4, restaurant.getDeliveryTime());
			pstmt.setString(5, restaurant.getAddress());
			pstmt.setFloat(6, restaurant.getRatings());
			pstmt.setBoolean(7, restaurant.getIsActive());
			pstmt.setString(8, restaurant.getImagePath());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ArrayList<Restaurant> fetchAll() {

		try {
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(FETCHALL);

			while (resultSet.next()) {
				restaurantList.add(new Restaurant(resultSet.getInt("restaurantId"), resultSet.getString("name"),
						resultSet.getString("cuisineType"), resultSet.getInt("deliveryTime"),
						resultSet.getString("address"), resultSet.getFloat("ratings"), resultSet.getBoolean("isActive"),
						resultSet.getString("imagePath")));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return restaurantList;
	}

	@Override
	public Restaurant fetchOne(int id) {
		try {
			pstmt = con.prepareStatement(FETCHONE);
			pstmt.setInt(1, id);
			resultSet = pstmt.executeQuery();

			// Extract user list from ResultSet
			ArrayList<Restaurant> localRestaurantList = extractRestaurantListFromResultSet(resultSet);

			// If the list is not empty, return the first user
			if (!localRestaurantList.isEmpty()) {
				return localRestaurantList.get(0);
			} else {
				System.out.println("No Restaurant found with ID " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // Return null if no user is found
	}

	ArrayList<Restaurant> extractRestaurantListFromResultSet(ResultSet resultSet) {
		ArrayList<Restaurant> localRestaurantList = new ArrayList<Restaurant>(); // Local list to avoid shared state
		try {
			while (resultSet.next()) {
				localRestaurantList.add(new Restaurant(resultSet.getInt("restaurantId"), resultSet.getString("name"),
						resultSet.getString("cuisineType"), resultSet.getInt("deliveryTime"),
						resultSet.getString("address"), resultSet.getFloat("ratings"), resultSet.getBoolean("isActive"),
						resultSet.getString("imagePath")));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localRestaurantList; // Return the local list, even if it's empty
	}

	@Override
	public int update(int id, String address) {

		try {
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, address);
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

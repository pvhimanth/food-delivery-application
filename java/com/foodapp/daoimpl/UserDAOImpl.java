package com.foodapp.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.foodapp.dao.UserDAO;
import com.foodapp.dbutil.DBConnection;
import com.foodapp.model.User;
import com.foodapp.secure.MyDecrypt;

public class UserDAOImpl implements UserDAO {

	private static Connection con;
	static ArrayList<User> userList = new ArrayList<User>();

	private static final String INSERTQUERY = "INSERT INTO user (userId, username, password, email, address) VALUES (?, ?, ?, ?, ?);";
	private static final String FETCHALL = "SELECT * FROM user;";
	private static final String FETCHONE = "SELECT * FROM user WHERE userId = ?;";
	private static final String UPDATE = "UPDATE user SET address = ? WHERE userId = ?;";
	private static final String DELETE = "DELETE FROM user WHERE userId = ?;";
	private static final String FETCHWITHEMAIL = "SELECT * FROM user WHERE email = ?;";

	static {
		con = DBConnection.connect();
	}

	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet resultSet;

	@Override
	public int insert(User user) {

		try {
			pstmt = con.prepareStatement(INSERTQUERY);
			pstmt.setInt(1, user.getUserId());
			pstmt.setString(2, user.getUsername());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getAddress());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ArrayList<User> fetchAll() {

		try {
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(FETCHALL);

			while (resultSet.next()) {
				userList.add(new User(resultSet.getInt("userId"), MyDecrypt.decrypt(resultSet.getString("username")),
						MyDecrypt.decrypt(resultSet.getString("password")),
						MyDecrypt.decrypt(resultSet.getString("email")),
						MyDecrypt.decrypt(resultSet.getString("address"))));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	@Override
	public User fetchOne(int id) {
		try {
			pstmt = con.prepareStatement(FETCHONE);
			pstmt.setInt(1, id);
			resultSet = pstmt.executeQuery();

			// Extract user list from ResultSet
			ArrayList<User> localUserList = extractUserListFromResultSet(resultSet);

			// If the list is not empty, return the first user
			if (!localUserList.isEmpty()) {
				return localUserList.get(0);
			} else {
				System.out.println("No User found with ID " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // Return null if no user is found
	}

	ArrayList<User> extractUserListFromResultSet(ResultSet resultSet) {
		ArrayList<User> localUserList = new ArrayList<>(); // Local list to avoid shared state
		try {
			while (resultSet.next()) {
				localUserList
						.add(new User(resultSet.getInt("userId"), MyDecrypt.decrypt(resultSet.getString("username")),
								MyDecrypt.decrypt(resultSet.getString("password")),
								MyDecrypt.decrypt(resultSet.getString("email")),
								MyDecrypt.decrypt(resultSet.getString("address"))));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localUserList; // Return the local list, even if it's empty
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

	@Override
	public User fetchWithEmail(String email) {

		try {

			pstmt = con.prepareStatement(FETCHWITHEMAIL);
			pstmt.setString(1, email);
			resultSet = pstmt.executeQuery();

			ArrayList<User> localUserList = extractUserListFromResultSet(resultSet);

			if (!localUserList.isEmpty()) {
				return localUserList.get(0);
			} else {
				System.out.println("No User found with ID " + email);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

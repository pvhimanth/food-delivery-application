package com.foodapp.dao;

import java.util.ArrayList;

import com.foodapp.model.User;

public interface UserDAO {
	int insert (User user);
	ArrayList<User> fetchAll();
	User fetchOne(int i);
	int update(int id, String address);
	int delete(int id);
	User fetchWithEmail(String email);
}

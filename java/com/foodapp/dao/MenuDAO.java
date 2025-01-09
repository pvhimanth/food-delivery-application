package com.foodapp.dao;

import java.util.ArrayList;

import com.foodapp.model.Menu;

public interface MenuDAO {
	int insert (Menu menu);
	ArrayList<Menu> fetchAll();
	Menu fetchOne(int i);
	int update(int id, String description);
	int delete(int id);
	ArrayList<Menu> fetchMenuByRestaurant(int restaurantId);
}

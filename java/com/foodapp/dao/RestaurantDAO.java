package com.foodapp.dao;

import java.util.ArrayList;

import com.foodapp.model.Restaurant;

public interface RestaurantDAO {
	int insert (Restaurant restaurant);
	ArrayList<Restaurant> fetchAll();
	Restaurant fetchOne(int i);
	int update(int id, String address);
	int delete(int id);
}

package com.foodapp.dao;

import java.util.ArrayList;

import com.foodapp.model.Orders;

public interface OrdersDAO {
	
	int insert(Orders menu);
	ArrayList<Orders> fetchAll();
	Orders fetchOne(int i);
	int update(int id, String status);
	int delete(int id);
}

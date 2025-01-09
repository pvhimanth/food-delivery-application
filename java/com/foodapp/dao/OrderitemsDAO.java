package com.foodapp.dao;

import java.util.ArrayList;

import com.foodapp.model.Orderitems;

public interface OrderitemsDAO {
	
	int insert (Orderitems orderitems);
	ArrayList<Orderitems> fetchAll();
	Orderitems fetchOne(int i);
	int update(int id, int quantity);
	int delete(int id);
	ArrayList<Orderitems> fetchByOrderId(int orderId);
}


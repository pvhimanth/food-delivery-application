package com.foodapp.dao;

import java.util.ArrayList;

import com.foodapp.model.OrderHistory;

public interface OrderHistoryDAO {
	int insert (OrderHistory orderhistory);
	ArrayList<OrderHistory> fetchAll();
	OrderHistory fetchOne(int i);
	int update(int id, String status);
	int delete(int id);
}

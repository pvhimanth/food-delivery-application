package com.foodapp.main;

import java.util.ArrayList;
import java.util.Scanner;

import com.foodapp.dao.OrderHistoryDAO;
import com.foodapp.daoimpl.OrderHistoryDAOImpl;
import com.foodapp.model.OrderHistory;

public class OrderHistoryLaunch {

	public static void main(String[] args) {

		OrderHistoryDAO orderHistoryDAO = new OrderHistoryDAOImpl();
		System.out.println("Welcome to Order History App\nEnter your Choice:\n" + 
				"1. Insert Order History\n" + 
				"2. View Order History List\n" + 
				"3. View Specific Order History Details\n" + 
				"4. Update Order History Status\n" + 
				"5. Delete Order History");

		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();

		switch (choice) {

		case 1:
			System.out.println("Enter the Order History ID:");
			int orderHistoryId = scan.nextInt();
			System.out.println("Enter the Order ID:");
			int orderId = scan.nextInt();
			System.out.println("Enter the User ID:");
			int userId = scan.nextInt();
			System.out.println("Enter the Total Amount:");
			float totalAmount = scan.nextFloat();
			System.out.println("Enter the Status:");
			String status = scan.next();

			OrderHistory orderhistory = new OrderHistory(orderHistoryId, orderId, userId, totalAmount, status);
			System.out.println(orderHistoryDAO.insert(orderhistory) == 1 ? "Order History added successfully."
					: "Failed to add order history.");
			break;

		case 2:
			ArrayList<OrderHistory> orderHistoryList = orderHistoryDAO.fetchAll();

			for (OrderHistory oh : orderHistoryList) {
				System.out.println(oh);
			}
			break;

		case 3:
			System.out.println("Enter the Order History ID: ");
			orderHistoryId = scan.nextInt();
			OrderHistory fetchedOrderHistory = orderHistoryDAO.fetchOne(orderHistoryId);
			if (fetchedOrderHistory != null) {
				System.out.println(fetchedOrderHistory);
			} else {
				System.out.println("No Order History found with the given ID.");
			}
			break;

		case 4:
			System.out.println("Enter the Order History ID: ");
			orderHistoryId = scan.nextInt();
			System.out.println("Enter the new Status: ");
			status = scan.next();
			System.out.println(orderHistoryDAO.update(orderHistoryId, status) == 1 ? "Status updated successfully."
					: "Failed to update status.");
			break;

		case 5:
			System.out.println("Enter the Order History ID: ");
			orderHistoryId = scan.nextInt();
			System.out.println(orderHistoryDAO.delete(orderHistoryId) == 1 ? "Order History deleted successfully."
					: "Failed to delete order history.");
			break;

		default:
			System.out.println("Invalid choice. Please try again.");
		}

		scan.close();
	}
}

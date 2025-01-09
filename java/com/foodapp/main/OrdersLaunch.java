package com.foodapp.main;

import java.util.ArrayList;
import java.util.Scanner;

import com.foodapp.dao.OrdersDAO;
import com.foodapp.daoimpl.OrdersDAOImpl;
import com.foodapp.model.Orders;

public class OrdersLaunch {

	public static void main(String[] args) {

		OrdersDAO ordersDAO = new OrdersDAOImpl();
		System.out.println("Welcome to Orders App\nEnter your Choice:\n" + "1. Insert Order\n"
				+ "2. View Orders List\n" + "3. View Specific Order Details\n" + "4. Update Order Status\n"
				+ "5. Delete Order Record");

		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();

		switch (choice) {

		case 1:
			System.out.println("Enter the Order ID:");
			int orderId = scan.nextInt();
			System.out.println("Enter the User ID:");
			int userId = scan.nextInt();
			System.out.println("Enter the Restaurant ID:");
			int restaurantId = scan.nextInt();
			System.out.println("Enter the Total Amount:");
			float totalAmount = scan.nextFloat();
			scan.nextLine(); // Consume newline character
			System.out.println("Enter the Status:");
			String status = scan.nextLine();
			System.out.println("Enter the Payment Mode:");
			String paymentMode = scan.nextLine();

			Orders order = new Orders(orderId, userId, restaurantId, totalAmount, status, paymentMode);
			System.out.println(ordersDAO.insert(order) == 1 ? "Order added successfully." : "Failed to add order.");
			break;

		case 2:
			ArrayList<Orders> ordersList = ordersDAO.fetchAll();

			for (Orders o : ordersList) {
				System.out.println(o);
			}
			break;

		case 3:
			System.out.println("Enter the Order ID: ");
			orderId = scan.nextInt();
			Orders fetchedOrder = ordersDAO.fetchOne(orderId);
			if (fetchedOrder != null) {
				System.out.println(fetchedOrder);
			} else {
				System.out.println("No Order found with the given ID.");
			}
			break;

		case 4:
			System.out.println("Enter the Order ID: ");
			orderId = scan.nextInt();
			scan.nextLine(); // Consume newline character
			System.out.println("Enter the new Status: ");
			status = scan.nextLine();
			System.out.println(ordersDAO.update(orderId, status) == 1 ? "Status updated successfully."
					: "Failed to update status.");
			break;

		case 5:
			System.out.println("Enter the Order ID: ");
			orderId = scan.nextInt();
			System.out.println(ordersDAO.delete(orderId) == 1 ? "Order deleted successfully." : "Failed to delete order.");
			break;

		default:
			System.out.println("Invalid choice. Please try again.");
		}

		scan.close();
	}
}

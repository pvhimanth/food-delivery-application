package com.foodapp.main;

import java.util.ArrayList;
import java.util.Scanner;

import com.foodapp.dao.OrderitemsDAO;
import com.foodapp.daoimpl.OrderitemsDAOImpl;
import com.foodapp.model.Orderitems;

public class OrderitemsLaunch {

	public static void main(String[] args) {

		OrderitemsDAO orderItemsDAO = new OrderitemsDAOImpl();
		System.out.println("Welcome to Order Items App\nEnter your Choice:\n" + 
				"1. Insert Order Item\n" + 
				"2. View Order Items List\n" + 
				"3. View Specific Order Item Details\n" + 
				"4. Update Order Item Quantity\n" + 
				"5. Delete Order Item");

		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();

		switch (choice) {

		case 1:
			System.out.println("Enter the Order Item ID:");
			int orderItemId = scan.nextInt();
			System.out.println("Enter the Order ID:");
			int orderId = scan.nextInt();
			System.out.println("Enter the Menu ID:");
			int menuId = scan.nextInt();
			System.out.println("Enter the Quantity:");
			int quantity = scan.nextInt();
			System.out.println("Enter the Item Total:");
			int itemTotal = scan.nextInt();

			Orderitems orderitems = new Orderitems(orderItemId, orderId, menuId, quantity, itemTotal);
			System.out.println(orderItemsDAO.insert(orderitems) == 1 ? "Order Item added successfully."
					: "Failed to add order item.");
			break;

		case 2:
			ArrayList<Orderitems> orderItemsList = orderItemsDAO.fetchAll();

			for (Orderitems oi : orderItemsList) {
				System.out.println(oi);
			}
			break;

		case 3:
			System.out.println("Enter the Order Item ID: ");
			orderItemId = scan.nextInt();
			Orderitems fetchedOrderItem = orderItemsDAO.fetchOne(orderItemId);
			if (fetchedOrderItem != null) {
				System.out.println(fetchedOrderItem);
			} else {
				System.out.println("No Order Item found with the given ID.");
			}
			break;

		case 4:
			System.out.println("Enter the Order Item ID: ");
			orderItemId = scan.nextInt();
			System.out.println("Enter the new Quantity: ");
			quantity = scan.nextInt();
			System.out.println(orderItemsDAO.update(orderItemId, quantity) == 1 ? "Quantity updated successfully."
					: "Failed to update quantity.");
			break;

		case 5:
			System.out.println("Enter the Order Item ID: ");
			orderItemId = scan.nextInt();
			System.out.println(orderItemsDAO.delete(orderItemId) == 1 ? "Order Item deleted successfully."
					: "Failed to delete order item.");
			break;

		default:
			System.out.println("Invalid choice. Please try again.");
		}

		scan.close();
	}
}

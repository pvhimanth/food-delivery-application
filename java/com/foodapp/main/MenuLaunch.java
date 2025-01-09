package com.foodapp.main;

import java.util.ArrayList;
import java.util.Scanner;

import com.foodapp.dao.MenuDAO;
import com.foodapp.daoimpl.MenuDAOImpl;
import com.foodapp.model.Menu;

public class MenuLaunch {

	public static void main(String[] args) {

		MenuDAO menuDAO = new MenuDAOImpl();
		System.out.println("Welcome to Menu App\nEnter your Choice:\n" + "1. Insert Menu\n" + "2. View Menu List\n"
				+ "3. View Specific Menu Details\n" + "4. Update Menu Description\n" + "5. Delete Menu Record");

		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();

		switch (choice) {

		case 1:
			System.out.println("Enter the Menu ID:");
			int menuId = scan.nextInt();
			System.out.println("Enter the Restaurant ID:");
			int restaurantId = scan.nextInt();
			scan.nextLine();
			System.out.println("Enter the Name:");
			String name = scan.nextLine();
			System.out.println("Enter the Description:");
			String description = scan.nextLine();
			System.out.println("Enter the Price:");
			float price = scan.nextFloat();
			System.out.println("Is the Menu Item Available? (true/false):");
			boolean isAvailable = scan.nextBoolean();
			scan.nextLine(); // Consume the newline character
			System.out.println("Enter the Image Path:");
			String imagePath = scan.nextLine();

			Menu menu = new Menu(menuId, restaurantId, name, description, price, isAvailable, imagePath);
			System.out.println(menuDAO.insert(menu) == 1 ? "Menu added successfully." : "Failed to add menu.");
			break;

		case 2:
			ArrayList<Menu> menuList = menuDAO.fetchAll();

			for (Menu m : menuList) {
				System.out.println(m);
			}
			break;

		case 3:
			System.out.println("Enter the Menu ID: ");
			menuId = scan.nextInt();
			Menu fetchedMenu = menuDAO.fetchOne(menuId);
			if (fetchedMenu != null) {
				System.out.println(fetchedMenu);
			} else {
				System.out.println("No Menu found with the given ID.");
			}
			break;

		case 4:
			System.out.println("Enter the Menu ID: ");
			menuId = scan.nextInt();
			scan.nextLine(); // Consume the newline character
			System.out.println("Enter the new Description: ");
			description = scan.nextLine();
			System.out.println(menuDAO.update(menuId, description) == 1 ? "Description updated successfully."
					: "Failed to update description.");
			break;

		case 5:
			System.out.println("Enter the Menu ID: ");
			menuId = scan.nextInt();
			System.out.println(menuDAO.delete(menuId) == 1 ? "Menu deleted successfully."
					: "Failed to delete menu.");
			break;

		default:
			System.out.println("Invalid choice. Please try again.");
		}

		scan.close();
	}
}

package com.foodapp.main;

import java.util.ArrayList;
import java.util.Scanner;

import com.foodapp.dao.RestaurantDAO;
import com.foodapp.daoimpl.RestaurantDAOImpl;
import com.foodapp.model.Restaurant;

public class RestaurantLaunch {

	public static void main(String[] args) {

		RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
		System.out.println("Welcome to Restaurant App\nEnter your Choice:\n" + "1. Insert Restaurant\n"
				+ "2. View Restaurant List\n" + "3. View Specific Restaurant Details\n"
				+ "4. Update Restaurant Address\n" + "5. Delete Restaurant Record");

		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();

		switch (choice) {

		case 1:
			System.out.println("Enter the Restaurant ID:");
			int restaurantId = scan.nextInt();
			scan.nextLine();
			System.out.println("Enter the Name:");
			String name = scan.nextLine();
			System.out.println("Enter the Cuisine Type:");
			String cuisineType = scan.nextLine();
			System.out.println("Enter the Delivery Time (in minutes):");
			int deliveryTime = scan.nextInt();
			scan.nextLine();
			System.out.println("Enter the Address:");
			String address = scan.nextLine();
			System.out.println("Enter the Ratings:");
			float ratings = scan.nextFloat();
			System.out.println("Is the Restaurant Active? (true/false):");
			boolean isActive = scan.nextBoolean();
			scan.nextLine(); // Consume the newline character
			System.out.println("Enter the Image Path:");
			String imagePath = scan.nextLine();

			Restaurant restaurant = new Restaurant(restaurantId, name, cuisineType, deliveryTime, address, ratings,
					isActive, imagePath);
			System.out.println(restaurantDAO.insert(restaurant) == 1 ? "Restaurant added successfully."
					: "Failed to add restaurant.");
			break;

		case 2:
			ArrayList<Restaurant> restaurantList = restaurantDAO.fetchAll();

			for (Restaurant r : restaurantList) {
				System.out.println(r);
			}
			break;

		case 3:
			System.out.println("Enter the Restaurant ID: ");
			restaurantId = scan.nextInt();
			Restaurant fetchedRestaurant = restaurantDAO.fetchOne(restaurantId);
			if (fetchedRestaurant != null) {
				System.out.println(fetchedRestaurant);
			} else {
				System.out.println("No Restaurant found with the given ID.");
			}
			break;

		case 4:
			System.out.println("Enter the Restaurant ID: ");
			restaurantId = scan.nextInt();
			scan.nextLine(); // Consume the newline character
			System.out.println("Enter the new Address: ");
			address = scan.nextLine();
			System.out.println(restaurantDAO.update(restaurantId, address) == 1 ? "Address updated successfully."
					: "Failed to update address.");
			break;

		case 5:
			System.out.println("Enter the Restaurant ID: ");
			restaurantId = scan.nextInt();
			System.out.println(restaurantDAO.delete(restaurantId) == 1 ? "Restaurant deleted successfully."
					: "Failed to delete restaurant.");
			break;

		default:
			System.out.println("Invalid choice. Please try again.");
		}

		scan.close();
	}
}

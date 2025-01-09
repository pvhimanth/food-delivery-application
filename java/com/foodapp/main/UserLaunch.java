package com.foodapp.main;

import java.util.ArrayList;
import java.util.Scanner;

import com.foodapp.dao.UserDAO;
import com.foodapp.daoimpl.UserDAOImpl;
import com.foodapp.model.User;

public class UserLaunch {

	public static void main(String[] args) {

		UserDAO sdao = new UserDAOImpl();
		System.out.println("Welocme to Student App\nEnter your Choice:\n" + "1. Insert User\n" + "2. View User List\n"
				+ "3. View Specific User Details Based On ID\n" + "4. Update User Address\n" + "5. Delete User Record\n"
				+ "6. View Specific User Details Based On Email");

		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();

		switch (choice) {

		case 1:
			System.out.println("Enter the id :");
			int id = scan.nextInt();
			scan.nextLine();
			System.out.println("Enter the username :");
			String name = scan.nextLine();
			System.out.println("Enter the password :");
			String password = scan.nextLine();
			System.out.println("Enter the email :");
			String email = scan.nextLine();
			System.out.println("Enter the address ");
			String address = scan.nextLine();

			User s = new User(name, password, email, address);
			System.out.println(sdao.insert(s) == 1 ? "Data inserted" : "Data not inserted");
			break;

		case 2:
			ArrayList<User> sList = sdao.fetchAll();

			for (User sd : sList) {
				System.out.println(sd);
			}
			break;

		case 3:
			System.out.println("Enter the id: ");
			id = scan.nextInt();
			System.out.println(sdao.fetchOne(id));
			break;

		case 4:
			System.out.println("Enter the id: ");
			id = scan.nextInt();
			System.out.println("Enter the address: ");
			address = scan.nextLine();
			System.out.println(sdao.update(id, address) == 1 ? "Update Success" : "Update Failure");
			break;

		case 5:
			System.out.println("Enter the id: ");
			id = scan.nextInt();
			System.out.println(sdao.delete(id) == 1 ? "Delete Success" : " Deletion Failed");
			break;

		case 6:
			System.out.println("Enter the Email: ");
			scan.nextLine();
			email = scan.nextLine();
			System.out.println(sdao.fetchWithEmail(email));
			break;

		}
	}

}

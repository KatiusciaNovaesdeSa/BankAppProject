package com.revature;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.revature.dao.AccDao;
import com.revature.dao.AccTypeDao;
import com.revature.dao.UserDao;
import com.revature.model.Account;
import com.revature.model.User;
import com.revature.service.AccService;


public class BankDriver {
	static Scanner read = new Scanner(System.in);
	static String firstName;
	static String lastName;
	static String usrName;
	static String pwd;

	static AccService accSer = new AccService();
	static UserDao usrDao = new UserDao();
	static AccTypeDao typeDao = new AccTypeDao();
	static AccService aservice = new AccService();

	public static void main(String[] args) {
		run();
	}

	static void run() {
			System.out.println("*..................................*");
			System.out.println("*	Welcome to Bank App	   *");
			System.out.println("*1. New Client                     *");
			System.out.println("*2. Existing Client                *");
			System.out.println("*..................................*");
			
		
		Scanner scan = new Scanner(System.in);
		int option = 0;
		try {
			option = scan.nextInt();
			scan.nextLine();
			switch (option) {
			case 1:
				makeNewUser();
				break;
			case 2:
				logIn();
				break;
			default:
				System.out.println("Please enter a number from the menu");
				run();
				break;
			}
			// read.close();
		} catch (InputMismatchException e) {
			System.out.println("Sorry please enter a number");
			run();
		}
	}

	static void logIn() {
		System.out.println("Enter Username: ");
		usrName = read.nextLine();
		System.out.println("Enter Password: ");
		pwd = read.nextLine();
		boolean uniqueUser = false;

		List<User> allUsers = aservice.findAllUsers();
		for (User usr : allUsers) {
			if (usr.getUsrName().equals(usrName) && usr.getPws().equals(pwd)) {
				uniqueUser = true;
				accountMenu(usr);
				break;
			}
		}
		if (uniqueUser == false) {
			System.out.println("Incorrect Username/Password");
			logIn();
		}
	}

	private static void makeNewUser() {
		String firstName = "a";
		String lastName = "a";
		String usrName = "a";
		String pwd = "a";

		System.out.println("Please enter your First name: ");
		firstName = read.nextLine();
		System.out.println("Enter your Last name: ");
		lastName = read.nextLine();

		do {
			List<User> u = aservice.findAllUsers();

			if (usrName.equals("")) {
				System.out.println("Create Username");
			}
			System.out.println("Make a username: ");
			usrName = read.nextLine();
			for (User usr : u) {
				if (usr.getUsrName().equals(usrName)) {
					System.out.println("Username already in the system!");
					usrName = "";
				}
			}
		} while (usrName.equals(""));
		do {
			if (pwd.equals("")) {
				System.out.println("Create Password");
			}
			System.out.println("Enter a password: ");
			pwd = read.nextLine();
		} while (pwd.equals(""));
		User u = AccService.newUserAccount(firstName, lastName, usrName, pwd);
		newAccount(u);
		accountMenu(u);

	}

	static void accountMenu(User u) {

		int option = 0;

		while (option != 5) {
			Scanner read = new Scanner(System.in);
			System.out.println("*..................................*");
			System.out.println("*	          MENU               *");
			System.out.println("*1. Check balance                  *");
			System.out.println("*2. Open account                   *");
			System.out.println("*3. Deposit                        *");
			System.out.println("*4. Withdraw                       *");
			System.out.println("*5. Exit                           *");
			System.out.println("*..................................*");
			

			try {
				option = read.nextInt();
				read.nextLine();
				switch (option) {
				case 1:
					checkBalance(u);
					break;
				case 2:
					deposit(u);
					break;
				case 3:
					withdraw(u);
					break;
				case 4:
					newAccount(u);
					break;
				case 5:
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("Application has ended. Thank you for choosing us!");
					System.exit(0);
					break;
				default:
					System.out.println("Please enter a number from the menu!");
					accountMenu(u);
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Enter a number from the menu");
			}

		}
	}

	public static Account findAccount(User u) {
		System.out.println("Which account would you like?");
		System.out.println("1. Savings");
		System.out.println("2. Checking");
		Scanner read = new Scanner(System.in);
		int option = read.nextInt();

		AccDao aDao = new AccDao();
		List<Account> accounts = aDao.findAll();
		try {
			for (Account a : accounts) {
				if (a.getOwnerID() == (u.getUserID()) && a.getAccType() == (option)) {
					return a;
				}
			}
			throw new InvalidAccountException();
		} catch (InvalidAccountException e) {
			System.out.println("No account");;
			String a = null;
		}
		return null;
	}

	public static void checkBalance(User u) {
		Account a = findAccount(u);
		if (a == null) {
		} else {
			double balance = a.getBalance();
			System.out.println("Balance: " + balance);
		}
	}

	public static void deposit(User u) {
		Account a = findAccount(u);
		if (a == null) {
		} else {
			System.out.println("How much would you like to deposit?");
			Scanner scan = new Scanner(System.in);
			double amount = scan.nextDouble();
			double bl = a.getBalance();
			a.setBalance(bl + amount);
			AccDao aDao = new AccDao();
			aDao.update(a);
		}
	}

	public static void withdraw(User u) {
		Account a = findAccount(u);
		if (a == null) {
		} else {
			System.out.println("How much would you like to withdraw?");
			Scanner scan = new Scanner(System.in);
			double amount = scan.nextDouble();
			double bl = a.getBalance();
			if (bl >= amount) {
				a.setBalance(bl - amount);
				AccDao aDao = new AccDao();
				aDao.update(a);
			} else {
				System.out.println("Can't withdraw amount. Not enough funds.\n");
				withdraw(u);
			}
		}
	}

	public static Account newAccount(User u) {
		int id = u.getUserID();
		Account a = new Account();
		System.out.println("What type of account would you like to set up?");
		System.out.println("1. Savings");
		System.out.println("2. Checking");
		Scanner read = new Scanner(System.in);
		String option = read.nextLine();
		switch (option) {
		case "1":
			a.setAccType(1);
			break;
		case "2":
			a.setAccType(2);
			break;
		default:
			System.out.println("error");// change
		}
		a.setOwnerID(id);
		System.out.println(a.getAccType());
		a.setBalance(0);
		aservice.makeAccount(a);
		return a;
	}
}

package com.revature.service;

import java.util.List;

import com.revature.dao.AccTypeDao;
import com.revature.dao.AccDao;
import com.revature.dao.GenericDao;
import com.revature.dao.UserDao;
import com.revature.model.Account;
import com.revature.model.User;

public class AccService {
	static UserDao userDao = new UserDao();
	static AccDao accDao = new AccDao();

	public static User newUserAccount(String Firstname, String Lastname, String Username, String Password) {
		User u = new User(Firstname, Lastname, Username, Password);
		userDao.save(u);
		return u;
	}

	public Account makeAccount(Account a) {
		accDao.save(a);
		return a;
	}

	public List<User> findAllUsers() {
		return userDao.findAll();
	}
}

package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BankDbConnection {
	private final String  URL =  "jdbc:postgresql://rev-canada-training.cgz20kzsu2zt.us-east-2.rds.amazonaws.com:5432/bankdb";
	private final String username = "bankuser";
	private final String password = "password";
	
	public Connection getDbConnection() throws SQLException{
		return DriverManager.getConnection(URL, username, password);
	}

}

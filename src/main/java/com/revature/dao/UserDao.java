package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.User;

public class UserDao implements GenericDao<User, Integer> {
	
	private BankDbConnection bankCon;
	
	public UserDao() {
		bankCon = new BankDbConnection();
	}


	@Override
	public List<User> findAll() {
		List<User> usr = new ArrayList<User>();

		try (Connection conn = bankCon.getDbConnection()) {
			String query = "SELECT * FROM USER_TB";
			Statement statement = conn.createStatement();   ///ta com STATEMENT MUDAR
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {

				User temp = new User();
				temp.setUserID(rs.getInt(1));
				temp.setFirstName(rs.getString(2));
				temp.setLastName(rs.getString(3));
				temp.setUsrName(rs.getString(4));
				temp.setPws(rs.getString(5));
				usr.add(temp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usr;
	}

	@Override
	public User findById(Integer id) {
		User usr = null;

		try (Connection conn = bankCon.getDbConnection()){
			String sql = "SELECT * FROM USER_TB WHERE USERID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				usr = new User();
				usr.setFirstName(rs.getString(1));
				usr.setLastName(rs.getString(2));
				usr.setUserID(rs.getInt(3));
				usr.setPws(rs.getString(4));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usr;
	}
	

	@Override
	public User save(User obj) {

		try (Connection conn = bankCon.getDbConnection()){
			conn.setAutoCommit(false);
			String sql = "INSERT INTO USER_TB (FIRSTNAME,LASTNAME,USERNAME,PASS_WORD) VALUES(?,?,?,?)";
			//String[] keyNames = { "USERID" };// need to change;
			//PreparedStatement ps = conn.prepareStatement(sql, keyNames);

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, obj.getUserID());
			ps.setString(1, obj.getFirstName());
			ps.setString(2, obj.getLastName());
			ps.setString(3, obj.getUsrName());
			ps.setString(4, obj.getPws());

			int numRows = ps.executeUpdate();
			if (numRows == 1) {
				ResultSet pk = ps.getGeneratedKeys();
				while (pk.next()) {
					obj.setUserID(pk.getInt(1));

				}
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public User update(User obj) {

		try (Connection conn = bankCon.getDbConnection()){
			String sql = "UPDATE USER_TB SET FIRSTNAME = ?, LASTNAME = ?, USERNAME = ?, PASS_WORD = ? WHERE USERID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, obj.getFirstName());
			ps.setString(2, obj.getLastName());
			ps.setString(3, obj.getUsrName());
			ps.setString(4, obj.getPws());
			ps.setInt(5, obj.getUserID());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
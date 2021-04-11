package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.revature.model.AccType;

public class AccTypeDao implements GenericDao<AccType, Integer> {
	

	private BankDbConnection bankCon;
	
	public AccTypeDao() {
		bankCon = new BankDbConnection();
	}
	

	@Override
	public List<AccType> findAll() {
		List<AccType> type = new ArrayList<AccType>();

		try (Connection conn = bankCon.getDbConnection()) {
			String query = "SELECT BALANCE FROM ACCOUNT_TB WHERE USERID = ?";
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {

				AccType temp = new AccType();
				temp.setTypeID(rs.getInt(1));
				temp.setType(rs.getString(2));
				type.add(temp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return type;
	}
	

	@Override
	public AccType findById(Integer id) {
		AccType accT = null;

		try (Connection conn = bankCon.getDbConnection()){
			String sql = "SELECT * FROM ACC_TYPE_TB WHERE TYPE = ?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				accT = new AccType();
				accT.setTypeID(rs.getInt(1));
				accT.setType(rs.getString(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accT;
	}

	@Override
	public AccType save(AccType obj) {

		try (Connection conn = bankCon.getDbConnection()){
			conn.setAutoCommit(false);

			String sql = "INSERT INTO ACCOUNT_TB (TYPE) VALUES(?)";
			String[] keyNames = { "ACC_ID" };

			PreparedStatement ps = conn.prepareStatement(sql, keyNames);
			ps.setInt(1, obj.getTypeID());
			ps.setString(2, obj.getType());

			int numRows = ps.executeUpdate();
			if (numRows > 0) {
				ResultSet pk = ps.getGeneratedKeys();
				while (pk.next()) {
					obj.setTypeID(pk.getInt(1));
					obj.setType(pk.getString(2));
				}
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;

	}

	@Override
	public AccType update(AccType obj) {

		try (Connection conn = bankCon.getDbConnection()){
			String sql = "UPDATE ACC_TYPE_TB SET TYPE = ? WHERE TYPEID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, obj.getType());

			ps.setInt(2, obj.getTypeID());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
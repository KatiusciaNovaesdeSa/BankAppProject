package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import org.postgresql.core.TypeInfo;


import com.revature.model.Account;
import com.revature.model.AccType;

import java.sql.Types;


public class AccDao implements GenericDao<Account, Integer> {
	
private BankDbConnection bankCon;
	
	public AccDao() {
		bankCon = new BankDbConnection();
	}
	

	@Override
	public List<Account> findAll() {
		List<Account> account = new ArrayList<Account>();

		try (Connection conn = bankCon.getDbConnection()) {
			//String query = "{? = call GET_ALL_ACC(?)}";
			//String query = "{CALL GET_ALL_ACC (?)}";  //era antes
			String query = "\n"
					+ "select * from user_tb u left outer join account_junction_user aju on aju.u_id = aju.u_id left outer join account_tb ac on aju.a_id = a_id\n"
					+ "		where aju.u_id= ?;\n"
					+ "				";
			
			CallableStatement st = conn.prepareCall(query);
			st.registerOutParameter(1, Types.OTHER);
			st.execute();
			ResultSet rs = (ResultSet) st.getObject(1);
		

			while (rs.next()) {
				Account temp = new Account();
				temp.setAccID(rs.getInt(1));
				temp.setAccType(rs.getInt(2));
				temp.setOwnerID(rs.getInt(3));
				temp.setBalance(rs.getDouble(4));
				account.add(temp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

	

	@Override
	public Account findById(Integer id) {
		Account a = null;
		try (Connection conn = bankCon.getDbConnection()){
			String sql = "SELECT ACC_ID FROM ACCOUNT_TB WHERE OWNER_ID = ?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				a = new Account();
				a.setAccID(rs.getInt(1));
				a.setAccType(rs.getInt(2));
				a.setBalance(rs.getDouble(3));

			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return a;
	}
	

//	@Override
//	public Account save(Account obj) {
//		try (Connection conn = bankCon.getDbConnection()){
//			String sql = "{? = call acc_insert(?,?,?)}";
//			CallableStatement callStatement = conn.prepareCall(sql);
//			
//			callStatement.registerOutParameter(1, Types.VARCHAR); // let the callable statement know what sql datatype the function is going to return
//		//	callStatement.setString(2, ACC_TYPE);
//		//	callStatement.setString(3, f_recipe);
//			callStatement.setInt(1, obj.getAccType());
//			callStatement.setInt(2, obj.getOwnerID());
//	    	callStatement.setDouble(3, obj.getBalance());
//
//			
//			callStatement.execute();
//			System.out.println(callStatement.getString(1));
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return obj;
//	}



	@Override
	public Account save(Account obj) {
		try (Connection conn = bankCon.getDbConnection()){
			conn.setAutoCommit(false);
			String sql = "{? = call acc_insert(?,?,?)}";
			//String sql = "INSERT INTO ACCOUNT_TB ( ACC_TYPE,OWNER_ID,BALANCE) VALUES(?,?,?)";
		  // String[] keyNames = { "ACC_ID" };

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, obj.getAccType());
			ps.setInt(2, obj.getOwnerID());
			ps.setDouble(3, obj.getBalance());

			int numRows = ps.executeUpdate();
			if (numRows == 1) {
				ResultSet pk = ps.getGeneratedKeys();
				while (pk.next()) {
					obj.setAccID(pk.getInt(1));
				}
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public Account update(Account obj) {
		try (Connection conn = bankCon.getDbConnection()){
			conn.setAutoCommit(false);
			String sql = "UPDATE ACCOUNT_TB SET BALANCE = ? WHERE ACC_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, obj.getBalance());
			ps.setInt(2, obj.getAccID());
			ps.executeUpdate();

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace(); // use a logger
		}
		return obj;
	}
}

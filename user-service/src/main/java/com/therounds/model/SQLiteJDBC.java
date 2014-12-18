package com.therounds.model;

import java.sql.*;
import java.util.ArrayList;

public class SQLiteJDBC {
	static Connection c = null;

	
	public static void createDB() {

		try {
			Statement stmt = c.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS USER"
					+ "(ID INTGER PRIMARY KEY NOT NULL," + " firstName TEXT , "
					+ " lastName TEXT , " + " email TEXT , "
					+ " password TEXT , " + " uri TEXT)";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		//System.out.println("Table created successfully");
	}
	
	
	private static void openDatabase() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			createDB();
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}


	public static User getUser(Integer id) {
		try {
			openDatabase();
			PreparedStatement stmt = c
					.prepareStatement("select * from user where id=?");
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			c.commit();
			User u = new User();
			u.setId(rs.getInt("id"));
			u.setFirstName(rs.getString("firstName"));
			u.setLastName(rs.getString("lastName"));
			u.setEmail(rs.getString("email"));
			u.setPassword(rs.getString("password"));
			u.setUri(rs.getString("uri"));
			c.close();
			return u;

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return null;
	}
	


	public static void deleteUser(Integer id) {
		try {
			openDatabase();
			PreparedStatement stmt = c
					.prepareStatement("delete from user where id=?");
			stmt.setLong(1, id);
			stmt.execute();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	

	
	public static Users getUsers() {

		Users users = new Users();
		users.setUsers(new ArrayList<User>());

		try {
			openDatabase();
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USER;");
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setFirstName(rs.getString("firstName"));
				u.setLastName(rs.getString("lastName"));
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("password"));
				u.setUri(rs.getString("uri"));
				users.getUsers().add(u);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return users;
	}
	
	


	public static void insertUser(User user) {
		Users users = new Users();
		users.setUsers(new ArrayList<User>());
		try {
			openDatabase();
			PreparedStatement statement = c
					.prepareStatement("insert into user(id, firstName, lastName, email, password, uri)"
							+ "VALUES(?,?,?,?,?,?)");
			statement.setLong(1, user.getId());
			statement.setString(2, user.getFirstName());
			statement.setString(3, user.getLastName());
			statement.setString(4, user.getEmail());
			statement.setString(5, user.getPassword());
			statement.setString(6, user.getUri());
			statement.executeUpdate();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	

	public static User updateUser(User user) {
		Users users = new Users();
		users.setUsers(new ArrayList<User>());
		try {
			openDatabase();
			PreparedStatement statement = c
					.prepareStatement("UPDATE user SET firstName=?, lastName=?, email=?, password=?, uri=? WHERE id=?");

			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getPassword());
			statement.setString(5, user.getUri());
			statement.setLong(6, user.getId());
			statement.executeUpdate();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return getUser(user.getId());
	}



}

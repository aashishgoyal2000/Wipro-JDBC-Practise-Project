package com.wipro.sales.util;

import java.sql.*;

public class DBUtil {
	public static Connection getDBConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/wipro", "root", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

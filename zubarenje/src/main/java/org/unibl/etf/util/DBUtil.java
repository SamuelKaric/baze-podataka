package org.unibl.etf.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DBUtil {

	private static String jdbcURL;
	private static String password;
	private static String username;

	static{
		ResourceBundle bundle = ResourceBundle.getBundle("org.unibl.etf.util.database");
		jdbcURL = bundle.getString("jdbcURL");
		username = bundle.getString("username");
		password = bundle.getString("password");
	}

	public static void close(Connection c) {

		if(c!=null) {
			try {
				c.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}

	public static void close(Connection c, Statement ps, ResultSet rs) {
		if(rs!=null)
			close(rs);
		close(ps);
		close(c);
	}

	public static void close(ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public static void close(Statement ps) {
		if(ps!=null) {
			try {
				ps.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static String EmptyToNull(String s) {
		if("".equals(s)) {
			return null;
		}else {
			return s;
		}
	}

	public static Connection getConnection() throws SQLException {
		Connection c = DriverManager.getConnection(jdbcURL,username,password);
		return c;
	}

	public static PreparedStatement prepareStatment(Connection c, String sql, Object... objects) throws SQLException{
		PreparedStatement ps = c.prepareStatement(sql);
		for(int i = 0; i< objects.length; i++) {
			ps.setObject(i+1, objects[i]);
		}
		return ps;
	}

}




package com.tutorial.drop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
	private static Statement stmt = null;
	private static Connection conn = null;
	private static String name = null;
	
	public void connect(String n) throws Exception {
		name = "jdbc:sqlite:" + n + ".db";
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection(name);
		stmt = conn.createStatement();
	}

	public void desconnect() throws Exception {
		stmt.close();
		conn.close();
	}
	
	public void commit() throws Exception {
		conn.setAutoCommit(false);
		conn.commit();
	}
	
	public void execute(String sql) throws Exception {
		stmt.executeUpdate(sql);
	}
	
	public ResultSet read(String sql) throws Exception {
		return stmt.executeQuery(sql);
	}
}

package com.markets.results.jdbcTransactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;

public class DBConn {

	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private char[] password;

	private Connection con;

	public DBConn() {
	}

	public DBConn(String url, String username, char[] password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public Connection getConnection() {
		if (this.con != null)
			return con;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, username, String.valueOf(password));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.con = con;
		return con;
	}

	public void close() {
		if (this.con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
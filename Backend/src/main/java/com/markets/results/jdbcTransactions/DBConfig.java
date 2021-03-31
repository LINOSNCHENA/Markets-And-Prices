package com.markets.results.jdbcTransactions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@PropertySource(value = "classpath:root.properties", ignoreResourceNotFound = true)

public class DBConfig {
	@Bean
	public DBConn getDBConn() {
		DBConn dbConnection = new DBConn();
		return dbConnection;
	}

}
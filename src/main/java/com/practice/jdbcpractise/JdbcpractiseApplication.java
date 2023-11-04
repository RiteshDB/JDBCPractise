package com.practice.jdbcpractise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
public class JdbcpractiseApplication {

	public static void main(String[] args) throws SQLException, IOException {
		ConfigurableApplicationContext context = SpringApplication.run(JdbcpractiseApplication.class, args);
		JDBCDemo simpleBean = context.getBean(JDBCDemo.class);
		simpleBean.simpleMethod();
	}

}

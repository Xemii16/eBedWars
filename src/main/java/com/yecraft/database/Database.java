package com.yecraft.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.Getter;

@Getter
public class Database {
	Connection connection;
	public static Database database;
	
	public void connect(){
		try {
			connection = DriverManager.getConnection(
	"jdbc:mysql://localhost:6072/test",
	"mysql", "password"
	);
		} catch (SQLException e){
			e.printStackTrace();
			System.out.println("Database has connection error");
		}
	}
	public void disconnect(){
		try {
			if (connection != null && !connection.isClosed()){
				connection.close();
			}
		} catch (SQLException e){
			e.printStackTrace();
			System.out.println("Database disconnect has error");
		}
	}
	
	public static Database getInstance(){
		return database;
	}
}
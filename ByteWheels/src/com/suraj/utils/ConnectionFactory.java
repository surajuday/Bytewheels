package com.suraj.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Factory class for the JDBC Connection
 * @author suraj.udayashankar
 *
 */
public class ConnectionFactory {
	static Logger log = Logger.getLogger(ConnectionFactory.class);
	private static Connection instance = null;

	private ConnectionFactory() {

	}

	public static Connection getConnection() {
		if (instance == null) {
			synchronized (ConnectionFactory.class) {
				if (instance == null) {
					try {
						Class.forName("com.mysql.jdbc.Driver");
						log.info("Congrats - Seems your MySQL JDBC Driver Registered!");
					} catch (ClassNotFoundException e) {
						log.error(
								"Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly",
								e);
					}

					try {
						// DriverManager: The basic service for managing a set
						// of JDBC drivers.
						instance = DriverManager.getConnection(PropertyLoader.getProperty("jdbc.connectionString"),
								PropertyLoader.getProperty("jdbc.username"),
								PropertyLoader.getProperty("jdbc.password"));
						if (instance != null) {
							log.info("Database Connection Successful!");
						} else {
							log.info("Failed to make connection!");
						}
					} catch (SQLException e) {
						log.error("MySQL Connection Failed!", e);
					}
				}
			}
		}
		return instance;
	}

}

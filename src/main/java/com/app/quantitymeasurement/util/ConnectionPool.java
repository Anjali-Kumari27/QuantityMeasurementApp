package com.app.quantitymeasurement.util;

/*
 * UC16: ConnectionPool
 *
 * This class manages database connections using a simple connection pool.
 * Instead of creating a new database connection every time, a fixed number
 * of connections are created at application startup and reused when needed.
 *
 * Responsibilities:
 * - Initialize connections using database configuration
 * - Provide available connections to the repository
 * - Return connections back to the pool after use
 *
 * Benefits:
 * - Improves performance
 * - Reduces overhead of repeatedly creating connections
 * - Helps manage database resources efficiently
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayDeque;
import java.util.Queue;

public final class ConnectionPool {

	private static final Queue<Connection> pool = new ArrayDeque<>();
	private static final int POOL_SIZE;

	static {
		try {
			String driver = DatabaseConfig.getProperty("db.driver");
			String url = DatabaseConfig.getProperty("db.url");
			String username = DatabaseConfig.getProperty("db.username");
			String password = DatabaseConfig.getProperty("db.password");
			String poolSize = DatabaseConfig.getProperty("db.pool.size");

			Class.forName(driver);

			POOL_SIZE = Integer.parseInt(poolSize);

			for (int i = 0; i < POOL_SIZE; i++) {
				Connection connection = DriverManager.getConnection(url, username, password);
				pool.add(connection);
			}

		} catch (Exception e) {
			throw new RuntimeException("Error creating connection pool", e);
		}
	}

	private ConnectionPool() {
	}

	public static synchronized Connection getConnection() {
		if (pool.isEmpty()) {
			throw new RuntimeException("No available connections in pool");
		}
		return pool.poll();
	}

	public static synchronized void releaseConnection(Connection connection) {
		if (connection != null) {
			pool.offer(connection);
		}
	}
}
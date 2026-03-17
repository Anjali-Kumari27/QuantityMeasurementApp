package com.app.quantitymeasurement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.app.quantitymeasurement.exception.DatabaseException;

/*
 * UC16: ConnectionPool
 *
 * This class manages a pool of database connections for efficient reuse.
 * It initializes a specified number of connections based on the configuration
 * and provides methods to acquire and release connections.
 *
 * The pool ensures that the number of active connections does not exceed the
 * configured pool size and supports validation and monitoring.
 */
public class ConnectionPool {

	private static final Logger logger = Logger.getLogger(ConnectionPool.class.getName());

	private static ConnectionPool instance;

	private final List<Connection> availableConnections;
	private final List<Connection> usedConnections;

	private final int poolSize;
	private final String dbUrl;
	private final String dbUsername;
	private final String dbPassword;
	private final String driverClass;
	private final String testQuery;

	/*
	 * Private constructor to initialize the connection pool.
	 */
	private ConnectionPool() throws SQLException {
		ApplicationConfig config = ApplicationConfig.getInstance();

		this.poolSize = config.getIntProperty(ApplicationConfig.ConfigKey.DB_POOL_SIZE.getKey(), 3);

		this.dbUrl = config.getProperty(ApplicationConfig.ConfigKey.DB_URL.getKey());

		this.dbUsername = config.getProperty(ApplicationConfig.ConfigKey.DB_USERNAME.getKey());

		this.dbPassword = config.getProperty(ApplicationConfig.ConfigKey.DB_PASSWORD.getKey());

		this.driverClass = config.getProperty(ApplicationConfig.ConfigKey.DB_DRIVER_CLASS.getKey());

		this.testQuery = config.getProperty(ApplicationConfig.ConfigKey.HIKARI_CONNECTION_TEST_QUERY.getKey(),
				"SELECT 1");

		this.availableConnections = new ArrayList<>();
		this.usedConnections = new ArrayList<>();

		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			throw DatabaseException.connectionFailed("JDBC Driver class not found: " + driverClass, e);
		}

		initializeConnections();
	}

	/*
	 * Returns the singleton instance of ConnectionPool.
	 */
	public static synchronized ConnectionPool getInstance() throws SQLException {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	/*
	 * Initializes the pool with the configured number of connections.
	 */
	private void initializeConnections() throws SQLException {
		for (int i = 0; i < poolSize; i++) {
			availableConnections.add(createConnection());
		}

		logger.info("Connection pool initialized with " + poolSize + " connections.");
	}

	/*
	 * Creates a new database connection.
	 */
	private Connection createConnection() throws SQLException {
		try {
			return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
		} catch (SQLException e) {
			throw DatabaseException.connectionFailed("Unable to create database connection", e);
		}
	}

	/*
	 * Acquires a connection from the pool.
	 */
	public synchronized Connection getConnection() throws SQLException {
		if (availableConnections.isEmpty()) {
			if (getTotalConnectionCount() < poolSize) {
				Connection newConnection = createConnection();
				usedConnections.add(newConnection);
				return newConnection;
			}
			throw DatabaseException.connectionFailed("No available connections in the pool", null);
		}

		Connection connection = availableConnections.remove(0);

		if (!validateConnection(connection)) {
			logger.warning("Connection validation failed. Creating a new connection.");
			connection = createConnection();
		}

		usedConnections.add(connection);
		return connection;
	}

	/*
	 * Releases a connection back to the pool.
	 */
	public synchronized void releaseConnection(Connection connection) {
		if (connection == null) {
			return;
		}

		usedConnections.remove(connection);
		availableConnections.add(connection);
	}

	/*
	 * Validates a connection using a test query.
	 */
	public boolean validateConnection(Connection connection) {
		try (Statement statement = connection.createStatement()) {
			statement.execute(testQuery);
			return true;
		} catch (SQLException e) {
			logger.warning("Connection validation failed: " + e.getMessage());
			return false;
		}
	}

	/*
	 * Closes all connections in the pool.
	 */
	public synchronized void closeAll() {
		closeConnectionList(availableConnections);
		closeConnectionList(usedConnections);

		availableConnections.clear();
		usedConnections.clear();

		logger.info("All connections in the pool have been closed.");
	}

	private void closeConnectionList(List<Connection> connections) {
		for (Connection connection : connections) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.warning("Error closing connection: " + e.getMessage());
			}
		}
	}

	public int getAvailableConnectionCount() {
		return availableConnections.size();
	}

	public int getUsedConnectionCount() {
		return usedConnections.size();
	}

	public int getTotalConnectionCount() {
		return availableConnections.size() + usedConnections.size();
	}

	@Override
	public String toString() {
		return "ConnectionPool{" + "availableConnections=" + getAvailableConnectionCount() + ", usedConnections="
				+ getUsedConnectionCount() + ", totalConnections=" + getTotalConnectionCount() + '}';
	}

	public static void main(String[] args) {
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			Connection connection = pool.getConnection();

			logger.info("Validate connection: " + (pool.validateConnection(connection) ? "Success" : "Failure"));
			logger.info("Available connections after acquiring: " + pool.getAvailableConnectionCount());
			logger.info("Used connections after acquiring: " + pool.getUsedConnectionCount());

			pool.releaseConnection(connection);

			logger.info("Available connections after releasing: " + pool.getAvailableConnectionCount());
			logger.info("Used connections after releasing: " + pool.getUsedConnectionCount());

			pool.closeAll();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
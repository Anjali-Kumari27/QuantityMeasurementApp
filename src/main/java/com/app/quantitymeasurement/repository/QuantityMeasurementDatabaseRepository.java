package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/*
 * UC16: QuantityMeasurementDatabaseRepository
 *
 * This class implements the IQuantityMeasurementRepository interface and provides
 * methods to interact with a relational database for storing and retrieving
 * quantity measurement data.
 *
 * It uses JDBC for database operations and a connection pool for efficient
 * resource management.
 */
public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

	private static final Logger logger = Logger.getLogger(QuantityMeasurementDatabaseRepository.class.getName());

	private static QuantityMeasurementDatabaseRepository instance;

	private static final String INSERT_QUERY = "INSERT INTO quantity_measurement_entity "
			+ "(this_value, this_measurement_type, that_value, that_measurement_type, "
			+ "operation, result_value, result_unit, result_measurement_type, "
			+ "result_string, is_error, error_message, created_at, updated_at) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

	private static final String SELECT_ALL_QUERY = "SELECT * FROM quantity_measurement_entity ORDER BY created_at DESC";

	private static final String SELECT_BY_OPERATION = "SELECT * FROM quantity_measurement_entity WHERE operation = ? ORDER BY created_at DESC";

	private static final String SELECT_BY_MEASUREMENT_TYPE = "SELECT * FROM quantity_measurement_entity "
			+ "WHERE this_measurement_type = ? ORDER BY created_at DESC";

	private static final String DELETE_ALL_QUERY = "DELETE FROM quantity_measurement_entity";

	private static final String COUNT_QUERY = "SELECT COUNT(*) FROM quantity_measurement_entity";

	private final ConnectionPool connectionPool;

	private QuantityMeasurementDatabaseRepository() {
		try {
			this.connectionPool = ConnectionPool.getInstance();
			initializeDatabase();
		} catch (Exception e) {
			throw DatabaseException.connectionFailed("Repository initialization", e);
		}
	}

	public static synchronized QuantityMeasurementDatabaseRepository getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementDatabaseRepository();
		}
		return instance;
	}

	/*
	 * Initializes database schema for testing/demo safety.
	 */
	private void initializeDatabase() {
		String createTableQuery = "CREATE TABLE IF NOT EXISTS quantity_measurement_entity ("
				+ "id BIGINT AUTO_INCREMENT PRIMARY KEY, " + "this_value DOUBLE, "
				+ "this_measurement_type VARCHAR(100), " + "that_value DOUBLE, "
				+ "that_measurement_type VARCHAR(100), " + "operation VARCHAR(100), " + "result_value DOUBLE, "
				+ "result_unit VARCHAR(100), " + "result_measurement_type VARCHAR(100), "
				+ "result_string VARCHAR(255), " + "is_error BOOLEAN DEFAULT FALSE, " + "error_message VARCHAR(500), "
				+ "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " + "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
				+ ")";

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = connectionPool.getConnection();
			stmt = conn.createStatement();
			stmt.execute(createTableQuery);
			logger.info("Database schema initialized successfully.");
		} catch (SQLException e) {
			throw DatabaseException.queryFailed("CREATE TABLE quantity_measurement_entity", e);
		} finally {
			closeResources(stmt, conn);
		}
	}

	@Override
	public void save(QuantityMeasurementEntity entity) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = connectionPool.getConnection();
			stmt = conn.prepareStatement(INSERT_QUERY);

			stmt.setDouble(1, entity.getThisValue());
			stmt.setString(2, entity.getThisMeasurementType());
			stmt.setDouble(3, entity.getThatValue());
			stmt.setString(4, entity.getThatMeasurementType());
			stmt.setString(5, entity.getOperation());
			stmt.setDouble(6, entity.getResultValue());
			stmt.setString(7, entity.getResultUnit());
			stmt.setString(8, entity.getResultMeasurementType());
			stmt.setString(9, entity.getResultString());
			stmt.setBoolean(10, entity.isError());
			stmt.setString(11, entity.getErrorMessage());

			stmt.executeUpdate();
			logger.info("Measurement saved successfully.");

		} catch (SQLException e) {
			throw DatabaseException.queryFailed("INSERT INTO quantity_measurement_entity", e);
		} finally {
			closeResources(stmt, conn);
		}
	}

	@Override
	public List<QuantityMeasurementEntity> getAllMeasurements() {
		List<QuantityMeasurementEntity> measurements = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = connectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SELECT_ALL_QUERY);

			while (rs.next()) {
				measurements.add(mapResultSetToEntity(rs));
			}

			return measurements;

		} catch (SQLException e) {
			throw DatabaseException.queryFailed("SELECT * FROM quantity_measurement_entity", e);
		} finally {
			closeResources(rs, stmt, conn);
		}
	}

	@Override
	public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
		List<QuantityMeasurementEntity> measurements = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = connectionPool.getConnection();
			stmt = conn.prepareStatement(SELECT_BY_OPERATION);
			stmt.setString(1, operation);
			rs = stmt.executeQuery();

			while (rs.next()) {
				measurements.add(mapResultSetToEntity(rs));
			}

			return measurements;

		} catch (SQLException e) {
			throw DatabaseException.queryFailed("SELECT BY OPERATION", e);
		} finally {
			closeResources(rs, stmt, conn);
		}
	}

	@Override
	public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
		List<QuantityMeasurementEntity> measurements = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = connectionPool.getConnection();
			stmt = conn.prepareStatement(SELECT_BY_MEASUREMENT_TYPE);
			stmt.setString(1, measurementType);
			rs = stmt.executeQuery();

			while (rs.next()) {
				measurements.add(mapResultSetToEntity(rs));
			}

			return measurements;

		} catch (SQLException e) {
			throw DatabaseException.queryFailed("SELECT BY MEASUREMENT TYPE", e);
		} finally {
			closeResources(rs, stmt, conn);
		}
	}

	@Override
	public int getTotalCount() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = connectionPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(COUNT_QUERY);

			if (rs.next()) {
				return rs.getInt(1);
			}

			return 0;

		} catch (SQLException e) {
			throw DatabaseException.queryFailed("SELECT COUNT(*)", e);
		} finally {
			closeResources(rs, stmt, conn);
		}
	}

	@Override
	public void deleteAll() {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = connectionPool.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(DELETE_ALL_QUERY);
			logger.info("All measurement data deleted successfully.");
		} catch (SQLException e) {
			throw DatabaseException.queryFailed("DELETE FROM quantity_measurement_entity", e);
		} finally {
			closeResources(stmt, conn);
		}
	}

	@Override
	public String getPoolStatistics() {
		return connectionPool.toString();
	}

	@Override
	public void releaseResources() {
		connectionPool.closeAll();
	}

	private QuantityMeasurementEntity mapResultSetToEntity(ResultSet rs) throws SQLException {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

		entity.setId(rs.getLong("id"));
		entity.setThisValue(rs.getDouble("this_value"));
		entity.setThisMeasurementType(rs.getString("this_measurement_type"));
		entity.setThatValue(rs.getDouble("that_value"));
		entity.setThatMeasurementType(rs.getString("that_measurement_type"));
		entity.setOperation(rs.getString("operation"));
		entity.setResultValue(rs.getDouble("result_value"));
		entity.setResultUnit(rs.getString("result_unit"));
		entity.setResultMeasurementType(rs.getString("result_measurement_type"));
		entity.setResultString(rs.getString("result_string"));
		entity.setError(rs.getBoolean("is_error"));
		entity.setErrorMessage(rs.getString("error_message"));

		return entity;
	}

	private void closeResources(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException ignored) {
		}

		closeResources(stmt, conn);
	}

	private void closeResources(Statement stmt, Connection conn) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException ignored) {
		}

		if (conn != null) {
			connectionPool.releaseConnection(conn);
		}
	}

	public static void main(String[] args) {
		QuantityMeasurementDatabaseRepository repository = QuantityMeasurementDatabaseRepository.getInstance();
		System.out.println(repository.getPoolStatistics());
	}
}
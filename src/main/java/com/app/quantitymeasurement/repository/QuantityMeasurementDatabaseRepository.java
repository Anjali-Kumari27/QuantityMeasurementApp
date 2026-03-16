package com.app.quantitymeasurement.repository;

/*
 * UC16: QuantityMeasurementDatabaseRepository
 *
 * This repository implementation stores and retrieves quantity measurement
 * operation history from a database using JDBC.
 *
 * Responsibilities:
 * - Save measurement operations to the database
 * - Retrieve operation history
 * - Count total stored operations
 * - Delete stored records when required
 *
 * This repository uses the ConnectionPool to obtain database connections
 * and executes SQL queries using prepared statements.
 */

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

	private static QuantityMeasurementDatabaseRepository instance;

	private static final String INSERT_SQL = "INSERT INTO quantity_measurements "
			+ "(operation_type, first_operand, second_operand, result, error, error_message, created_at) "
			+ "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

	private static final String SELECT_ALL_SQL = "SELECT operation_type, first_operand, second_operand, result, error, error_message "
			+ "FROM quantity_measurements ORDER BY id";

	private static final String DELETE_ALL_SQL = "DELETE FROM quantity_measurements";

	private static final String COUNT_SQL = "SELECT COUNT(*) FROM quantity_measurements";

	private static final String FIND_BY_OPERATION_SQL = "SELECT operation_type, first_operand, second_operand, result, error, error_message "
			+ "FROM quantity_measurements WHERE operation_type = ? ORDER BY id";

	private static final String FIND_BY_TYPE_SQL = "SELECT operation_type, first_operand, second_operand, result, error, error_message "
			+ "FROM quantity_measurements " + "WHERE first_operand LIKE ? OR second_operand LIKE ? ORDER BY id";

	private QuantityMeasurementDatabaseRepository() {
	}

	public static synchronized QuantityMeasurementDatabaseRepository getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementDatabaseRepository();
		}
		return instance;
	}

	@Override
	public void save(QuantityMeasurementEntity entity) {
		Connection connection = null;

		try {
			connection = ConnectionPool.getConnection();

			try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
				statement.setString(1, entity.getOperationType());
				statement.setString(2, entity.getFirstOperand());
				statement.setString(3, entity.getSecondOperand());
				statement.setString(4, entity.getResult());
				statement.setBoolean(5, entity.isError());
				statement.setString(6, entity.getErrorMessage());

				statement.executeUpdate();
			}

		} catch (Exception e) {
			throw new DatabaseException("Failed to save quantity measurement entity", e);
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
	}

	@Override
	public List<QuantityMeasurementEntity> findAll() {
		List<QuantityMeasurementEntity> history = new ArrayList<>();
		Connection connection = null;

		try {
			connection = ConnectionPool.getConnection();

			try (Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL)) {

				while (resultSet.next()) {
					history.add(mapRowToEntity(resultSet));
				}
			}

			return history;

		} catch (Exception e) {
			throw new DatabaseException("Failed to fetch quantity measurement history", e);
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
	}

	public List<QuantityMeasurementEntity> findByOperation(String operationType) {
		List<QuantityMeasurementEntity> history = new ArrayList<>();
		Connection connection = null;

		try {
			connection = ConnectionPool.getConnection();

			try (PreparedStatement statement = connection.prepareStatement(FIND_BY_OPERATION_SQL)) {
				statement.setString(1, operationType);

				try (ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) {
						history.add(mapRowToEntity(resultSet));
					}
				}
			}

			return history;

		} catch (Exception e) {
			throw new DatabaseException("Failed to fetch records by operation type", e);
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
	}

	public List<QuantityMeasurementEntity> findByMeasurementType(String measurementType) {
		List<QuantityMeasurementEntity> history = new ArrayList<>();
		Connection connection = null;

		try {
			connection = ConnectionPool.getConnection();

			try (PreparedStatement statement = connection.prepareStatement(FIND_BY_TYPE_SQL)) {
				String pattern = "%" + measurementType.toUpperCase() + "%";
				statement.setString(1, pattern);
				statement.setString(2, pattern);

				try (ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) {
						history.add(mapRowToEntity(resultSet));
					}
				}
			}

			return history;

		} catch (Exception e) {
			throw new DatabaseException("Failed to fetch records by measurement type", e);
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
	}

	public int getTotalCount() {
		Connection connection = null;

		try {
			connection = ConnectionPool.getConnection();

			try (Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery(COUNT_SQL)) {

				if (resultSet.next()) {
					return resultSet.getInt(1);
				}
				return 0;
			}

		} catch (Exception e) {
			throw new DatabaseException("Failed to get total count", e);
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
	}

	public void deleteAll() {
		Connection connection = null;

		try {
			connection = ConnectionPool.getConnection();

			try (Statement statement = connection.createStatement()) {
				statement.executeUpdate(DELETE_ALL_SQL);
			}

		} catch (Exception e) {
			throw new DatabaseException("Failed to delete all records", e);
		} finally {
			ConnectionPool.releaseConnection(connection);
		}
	}

	@Override
	public void clear() {
		deleteAll();
	}

	private QuantityMeasurementEntity mapRowToEntity(ResultSet resultSet) throws Exception {
		return new QuantityMeasurementEntity(resultSet.getString("operation_type"),
				resultSet.getString("first_operand"), resultSet.getString("second_operand"),
				resultSet.getString("result"), resultSet.getBoolean("error"), resultSet.getString("error_message"));
	}
}
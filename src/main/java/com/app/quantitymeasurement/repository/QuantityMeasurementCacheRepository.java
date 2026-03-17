package com.app.quantitymeasurement.repository;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;

/*
 * UC16: QuantityMeasurementCacheRepository
 *
 * This repository implementation stores quantity measurement history
 * in memory and persists it to disk using Java serialization.
 *
 * It provides a lightweight alternative to the database repository.
 */
public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

	private static final String FILE_NAME = "quantity-measurement-history.ser";
	private static QuantityMeasurementCacheRepository instance;

	private final List<QuantityMeasurementEntity> history;
	private final File storageFile;

	private QuantityMeasurementCacheRepository() {
		this.history = new ArrayList<>();
		this.storageFile = new File(FILE_NAME);
		loadFromDisk();
	}

	public static synchronized QuantityMeasurementCacheRepository getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementCacheRepository();
		}
		return instance;
	}

	@Override
	public synchronized void save(QuantityMeasurementEntity entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Entity cannot be null");
		}

		history.add(entity);
		saveToDisk(entity);
	}

	@Override
	public synchronized List<QuantityMeasurementEntity> getAllMeasurements() {
		return new ArrayList<>(history);
	}

	@Override
	public synchronized List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
		List<QuantityMeasurementEntity> result = new ArrayList<>();

		for (QuantityMeasurementEntity entity : history) {
			if (entity.getOperation() != null && entity.getOperation().equalsIgnoreCase(operation)) {
				result.add(entity);
			}
		}

		return result;
	}

	@Override
	public synchronized List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
		List<QuantityMeasurementEntity> result = new ArrayList<>();

		for (QuantityMeasurementEntity entity : history) {
			if (entity.getThisMeasurementType() != null
					&& entity.getThisMeasurementType().equalsIgnoreCase(measurementType)) {
				result.add(entity);
			}
		}

		return result;
	}

	@Override
	public synchronized int getTotalCount() {
		return history.size();
	}

	@Override
	public synchronized void deleteAll() {
		history.clear();

		if (storageFile.exists() && !storageFile.delete()) {
			throw new QuantityMeasurementException("Failed to clear repository storage file.");
		}
	}

	@Override
	public synchronized String getPoolStatistics() {
		return "Cache repository does not use a connection pool.";
	}

	@Override
	public synchronized void releaseResources() {
		// No DB resources to release for cache repository
	}

	private void saveToDisk(QuantityMeasurementEntity entity) {
		try {
			boolean append = storageFile.exists() && storageFile.length() > 0;

			try (FileOutputStream fileOutputStream = new FileOutputStream(storageFile, true);
					ObjectOutputStream objectOutputStream = append ? new AppendableObjectOutputStream(fileOutputStream)
							: new ObjectOutputStream(fileOutputStream)) {

				objectOutputStream.writeObject(entity);
			}

		} catch (IOException e) {
			throw new QuantityMeasurementException("Failed to save repository data.", e);
		}
	}

	private void loadFromDisk() {
		if (!storageFile.exists() || storageFile.length() == 0) {
			return;
		}

		try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(storageFile))) {
			history.clear();

			while (true) {
				try {
					Object object = objectInputStream.readObject();
					if (object instanceof QuantityMeasurementEntity) {
						history.add((QuantityMeasurementEntity) object);
					}
				} catch (EOFException eofException) {
					break;
				}
			}

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Old repository data is incompatible. Starting with empty history.");
			history.clear();
		}
	}

	private static class AppendableObjectOutputStream extends ObjectOutputStream {

		public AppendableObjectOutputStream(OutputStream outputStream) throws IOException {
			super(outputStream);
		}

		@Override
		protected void writeStreamHeader() throws IOException {
			reset();
		}
	}
}
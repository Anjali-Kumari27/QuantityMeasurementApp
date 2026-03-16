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

/**
 * UC15: QuantityMeasurementCacheRepository is the singleton repository
 * implementation for storing quantity measurement history.
 *
 * Responsibilities:
 * - Maintains in-memory cache of QuantityMeasurementEntity records
 * - Persists history to disk using Java serialization
 * - Loads stored history at startup
 * - Provides centralized access to measurement history
 *
 * Architectural Role:
 * This class belongs to the Repository Layer and implements
 * IQuantityMeasurementRepository.
 *
 * Design Pattern Used:
 * - Singleton Pattern
 *
 * Why Singleton:
 * - Ensures one shared repository instance across the application
 * - Provides consistent centralized storage
 * - Supports predictable state management
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
	public synchronized List<QuantityMeasurementEntity> findAll() {
		return new ArrayList<>(history);
	}

	@Override
	public synchronized void clear() {
		history.clear();

		if (storageFile.exists() && !storageFile.delete()) {
			throw new QuantityMeasurementException("Failed to clear repository storage file.");
		}
	}

	/**
	 * Saves a single entity to disk using append mode.
	 * Uses AppendableObjectOutputStream when file already contains data
	 * to avoid writing duplicate stream headers.
	 */
	private void saveToDisk(QuantityMeasurementEntity entity) {
		try {
			boolean append = storageFile.exists() && storageFile.length() > 0;

			try (FileOutputStream fileOutputStream = new FileOutputStream(storageFile, true);
				 ObjectOutputStream objectOutputStream = append
						 ? new AppendableObjectOutputStream(fileOutputStream)
						 : new ObjectOutputStream(fileOutputStream)) {

				objectOutputStream.writeObject(entity);
			}

		} catch (IOException e) {
			throw new QuantityMeasurementException("Failed to save repository data.", e);
		}
	}

	/**
	 * Loads all previously saved entities from disk into in-memory cache.
	 * Reads serialized objects one by one until EOF is reached.
	 */
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
			history.clear();
		}
	}

	/**
	 * Custom ObjectOutputStream that skips writing the header
	 * when appending to an existing serialization file.
	 */
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
package com.app.quantitymeasurement.util;

import java.io.InputStream;
import java.util.Properties;

public final class DatabaseConfig {

	private static final Properties properties = new Properties();

	static {
		try (InputStream inputStream = DatabaseConfig.class.getClassLoader()
				.getResourceAsStream("application.properties")) {

			if (inputStream == null) {
				throw new RuntimeException("application.properties file not found");
			}

			properties.load(inputStream);

		} catch (Exception e) {
			throw new RuntimeException("Failed to load database configuration", e);
		}
	}

	private DatabaseConfig() {
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}
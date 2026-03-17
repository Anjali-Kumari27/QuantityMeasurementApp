package com.app.quantitymeasurement.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/*
 * UC16: ApplicationConfig
 *
 * This singleton class is responsible for loading and managing
 * application configuration properties.
 *
 * It supports loading configurations from a properties file,
 * system properties, or environment variables.
 *
 * The configuration properties include database connection details
 * such as driver class, URL, username, password, and connection pool settings.
 */
public class ApplicationConfig {

    private static final Logger logger =
            Logger.getLogger(ApplicationConfig.class.getName());

    private static ApplicationConfig instance;

    private Properties properties;
    private Environment environment;

    /*
     * Enum for environment to manage different environments like
     * development, testing, and production.
     */
    public enum Environment {
        DEVELOPMENT, TESTING, PRODUCTION
    }

    /*
     * Enum for configuration keys to avoid hardcoding strings.
     */
    public enum ConfigKey {
        REPOSITORY_TYPE("repository.type"),
        DB_DRIVER_CLASS("db.driver"),
        DB_URL("db.url"),
        DB_USERNAME("db.username"),
        DB_PASSWORD("db.password"),
        DB_POOL_SIZE("db.pool.size"),
        HIKARI_MAX_POOL_SIZE("db.hikari.maximum-pool-size"),
        HIKARI_MIN_IDLE("db.hikari.minimum-idle"),
        HIKARI_CONNECTION_TIMEOUT("db.hikari.connection-timeout"),
        HIKARI_IDLE_TIMEOUT("db.hikari.idle-timeout"),
        HIKARI_MAX_LIFETIME("db.hikari.max-lifetime"),
        HIKARI_POOL_NAME("db.hikari.pool-name"),
        HIKARI_CONNECTION_TEST_QUERY("db.hikari.connection-test-query");

        private final String key;

        ConfigKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    private ApplicationConfig() {
        loadConfiguration();
    }

    public static synchronized ApplicationConfig getInstance() {
        if (instance == null) {
            instance = new ApplicationConfig();
        }
        return instance;
    }

    /*
     * Loads configuration properties from application.properties.
     * If the file is missing, fallback defaults are used.
     */
    private void loadConfiguration() {
        properties = new Properties();

        try {
            String env = System.getProperty("app.env");
            if (env == null || env.isEmpty()) {
                env = System.getenv("APP_ENV");
            }

            String configFile = "application.properties";

            InputStream input = ApplicationConfig.class
                    .getClassLoader()
                    .getResourceAsStream(configFile);

            if (input != null) {
                properties.load(input);
                logger.info("Configuration loaded from " + configFile);

                if (env == null || env.isEmpty()) {
                    env = properties.getProperty("app.env", "development");
                }

                this.environment = Environment.valueOf(env.toUpperCase());
            } else {
                logger.warning("Configuration file not found, using defaults");
                loadDefaults();
            }

        } catch (Exception e) {
            logger.severe("Error loading configuration: " + e.getMessage());
            loadDefaults();
        }
    }

    /*
     * Loads default configuration values.
     */
    private void loadDefaults() {
        properties.setProperty(ConfigKey.REPOSITORY_TYPE.getKey(), "database");
        properties.setProperty(ConfigKey.DB_DRIVER_CLASS.getKey(), "org.h2.Driver");
        properties.setProperty(
                ConfigKey.DB_URL.getKey(),
                "jdbc:h2:mem:quantitydb;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:db/schema.sql'"
        );
        properties.setProperty(ConfigKey.DB_USERNAME.getKey(), "sa");
        properties.setProperty(ConfigKey.DB_PASSWORD.getKey(), "");
        properties.setProperty(ConfigKey.DB_POOL_SIZE.getKey(), "3");
        properties.setProperty(ConfigKey.HIKARI_CONNECTION_TEST_QUERY.getKey(), "SELECT 1");

        this.environment = Environment.DEVELOPMENT;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public String getEnvironment() {
        return environment.name();
    }

    public boolean isConfigKey(String key) {
        for (ConfigKey configKey : ConfigKey.values()) {
            if (configKey.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public void printAllProperties() {
        logger.info("----- Loaded Application Properties -----");
        for (String key : properties.stringPropertyNames()) {
            logger.info(key + " = " + properties.getProperty(key));
        }
    }

    public static void main(String[] args) {
        ApplicationConfig config = ApplicationConfig.getInstance();
        config.printAllProperties();
    }
}
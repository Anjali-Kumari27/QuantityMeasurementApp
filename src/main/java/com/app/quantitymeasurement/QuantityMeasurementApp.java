package com.app.quantitymeasurement;

import java.util.List;
import java.util.logging.Logger;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.util.ApplicationConfig;

/*
 * UC16: QuantityMeasurementApp
 *
 * This is the main entry point of the Quantity Measurement application.
 * It initializes repository, service, and controller layers and demonstrates
 * quantity measurement operations.
 *
 * This class does not contain business logic. It coordinates the layers.
 */
public class QuantityMeasurementApp {

	private static final Logger logger = Logger.getLogger(QuantityMeasurementApp.class.getName());

	private static QuantityMeasurementApp instance;

	private final IQuantityMeasurementRepository repository;
	private final QuantityMeasurementController controller;

	/*
	 * Private constructor to initialize application components.
	 */
	private QuantityMeasurementApp() {
		this.repository = createRepository();

		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(this.repository);

		this.controller = new QuantityMeasurementController(service);

		logger.info("Quantity Measurement Application initialized with " + this.repository.getClass().getSimpleName());
	}

	/*
	 * Returns singleton instance of the application.
	 */
	public static synchronized QuantityMeasurementApp getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementApp();
		}
		return instance;
	}

	public static void main(String[] args) {
		logger.info("Starting Quantity Measurement Application...");

		QuantityMeasurementApp app = getInstance();
		QuantityMeasurementController controller = app.controller;

		try {
			// optional: clear history for a clean demo run
			app.repository.clear();

			QuantityDTO quantity1 = new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET);
			QuantityDTO quantity2 = new QuantityDTO(24.0, QuantityDTO.LengthUnit.INCHES);

			boolean comparisonResult = controller.performComparison(quantity1, quantity2);
			logger.info("Comparison result: " + comparisonResult);

			QuantityDTO conversionResult = controller.performConversion(
					new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
					new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES));
			logger.info("Conversion result: " + conversionResult);

			QuantityDTO additionResult = controller.performAddition(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
					new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES),
					new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET));
			logger.info("Addition result: " + additionResult);

			QuantityDTO subtractionResult = controller.performSubtraction(
					new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET),
					new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES),
					new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET));
			logger.info("Subtraction result: " + subtractionResult);

			double divisionResult = controller.performDivision(new QuantityDTO(24.0, QuantityDTO.LengthUnit.INCHES),
					new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET));
			logger.info("Division result: " + divisionResult);

			try {
				controller.performAddition(new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS),
						new QuantityDTO(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT),
						new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS));
			} catch (QuantityMeasurementException e) {
				logger.warning("Expected error: " + e.getMessage());
			}

			logger.info("---- Operation History ----");
			List<QuantityMeasurementEntity> history = controller.getHistory();
			for (QuantityMeasurementEntity entity : history) {
				logger.info(entity.toString());
			}

		} catch (Exception e) {
			logger.severe("Application error: " + e.getMessage());
		}

		logger.info("Application execution finished.");
	}

	/*
	 * Creates repository based on configuration.
	 */
	private IQuantityMeasurementRepository createRepository() {
		ApplicationConfig config = ApplicationConfig.getInstance();

		String repositoryType = config.getProperty(ApplicationConfig.ConfigKey.REPOSITORY_TYPE.getKey(), "database");

		if ("database".equalsIgnoreCase(repositoryType)) {
			return QuantityMeasurementDatabaseRepository.getInstance();
		}

		return QuantityMeasurementCacheRepository.getInstance();
	}
}
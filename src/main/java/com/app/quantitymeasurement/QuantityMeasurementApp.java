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
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.util.DatabaseConfig;

/*
 * UC16: QuantityMeasurementApp
 *
 * This is the main entry point of the Quantity Measurement application.
 * It initializes the application components such as repository, service,
 * and controller layers.
 *
 * Responsibilities:
 * - Start the application
 * - Configure repository type (cache or database)
 * - Demonstrate measurement operations such as comparison, conversion,
 *   addition, subtraction and division
 * - Display operation history
 *
 * This class does not contain business logic. It only coordinates
 * different layers of the application.
 */

public class QuantityMeasurementApp {

	// Logger for logging information and errors in the Application class
	private static final Logger logger = Logger.getLogger(QuantityMeasurementApp.class.getName());

	public static void main(String[] args) {

		logger.info("Starting Quantity Measurement Application...");

		IQuantityMeasurementRepository repository = createRepository();
		IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
		QuantityMeasurementController controller = new QuantityMeasurementController(service);

		try {

			QuantityDTO feet = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);

			QuantityDTO inches = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);

			boolean result = controller.performComparison(feet, inches);
			logger.info("Comparison Result: " + result);

			QuantityDTO conversion = controller.performConversion(feet,
					new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES));

			logger.info("Conversion Result: " + conversion);

			QuantityDTO addition = controller.performAddition(feet, inches,
					new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET));

			logger.info("Addition Result: " + addition);

			QuantityDTO subtraction = controller.performSubtraction(new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET),
					new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES),
					new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET));

			logger.info("Subtraction Result: " + subtraction);

			double division = controller.performDivision(new QuantityDTO(24.0, QuantityDTO.LengthUnit.INCHES),
					new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET));

			logger.info("Division Result: " + division);

			try {

				controller.performAddition(new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS),
						new QuantityDTO(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT),
						new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS));

			} catch (QuantityMeasurementException e) {

				logger.warning("Expected Error: " + e.getMessage());
			}

			logger.info("---- Operation History ----");

			List<QuantityMeasurementEntity> history = controller.getHistory();

			for (QuantityMeasurementEntity entity : history) {
				logger.info(entity.toString());
			}

		} catch (Exception e) {

			logger.severe("Application Error: " + e.getMessage());
		}

		logger.info("Application execution finished.");
	}

	private static IQuantityMeasurementRepository createRepository() {

		String repositoryType = DatabaseConfig.getProperty("repository.type");

		if ("database".equalsIgnoreCase(repositoryType)) {
			return QuantityMeasurementDatabaseRepository.getInstance();
		}

		return QuantityMeasurementCacheRepository.getInstance();
	}
}